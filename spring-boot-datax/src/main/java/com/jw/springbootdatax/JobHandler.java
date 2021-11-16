package com.jw.springbootdatax;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.db.handler.BeanHandler;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jw.springbootdatax.config.DataXConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.sql.*;
import java.util.Map;
import java.util.concurrent.*;

public class JobHandler {

    private static final Logger logger = LoggerFactory.getLogger(JobHandler.class);

    private static volatile JobHandler handler;

    private final ExecutorService jobProcessor;

    private final LinkedBlockingQueue<String> jobs;

    private final DataXConfig config;

    private JobHandler(final DataXConfig config) {
        this.config = config;
        jobProcessor = Executors.newSingleThreadExecutor();
        jobs = new LinkedBlockingQueue<>();

        jobProcessor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!jobProcessor.isShutdown()) {
                        String take = jobs.take();
                        // 1.读取文件
                        String jobJson = FileUtil.readUtf8String(take);
                        String newJson = parseJob(jobJson);
                        // 2.读取配置项
                        // 3.动态修改，创建临时文件
                        String tempPath = generateTemJsonFile(newJson, config.getDataxPath());
                        String[] cmd = new String[]{"python", config.getDataxPath() + "/datax.py", tempPath};
                        // 4.执行命令
                        final Process process = Runtime.getRuntime().exec(cmd);
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                process.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null) {
                            JobLog.getInstance().log(line);
                        }
                        in.close();
                        process.waitFor();
                        //  删除临时文件
                        if (FileUtil.exist(config.getDataxPath())) {
                            FileUtil.del(new File(tempPath));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JobLog.getInstance().log(e.getMessage());
                }
            }
        });
    }

    public static JobHandler getInstance(DataXConfig config) {
        if (handler == null) {
            synchronized (JobHandler.class) {
                if (handler == null) {
                    handler = new JobHandler(config);
                }
            }
        }
        return handler;
    }

    public synchronized void push(String obj) {
        jobs.add(obj);
    }

    private String parseJob(String oJob) {
        JSONObject newObj = JSONUtil.parseObj(oJob);
        JSONObject job = newObj.getJSONObject("job");
        if (!newObj.containsKey("info")) {
            return oJob;
        }
        JSONObject info = newObj.getJSONObject("info");
        JSONArray content = job.getJSONArray("content");
        try {
            checkWriterIsValue(content);
            for (int i = 0; i < content.size(); i++) {
                JSONObject obj = content.getJSONObject(i);
                for (Map.Entry<String, Object> entry : obj.entrySet()) {
                    JSONObject entryValue = (JSONObject) entry.getValue();
                    JSONObject parameter = entryValue.getJSONObject("parameter");
                    replaceJobPlaceholder(content, parameter, info);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JobLog.getInstance().log(throwables.getMessage());
        }
        return newObj.toString();
    }

    /**
     * 替换job中 reader或writer的占位符，如name=${name}
     * @param parameter reader或writer的parameter json串
     * @param info job info
     */
    private void replaceJobPlaceholder(JSONArray content, JSONObject parameter, JSONObject info) {
        for (Map.Entry<String, Object> entry : info.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            JobValue jobValue = JobValue.getJobValue(value);
            Strategy strategy = JobValueStrategy.getStrategy(jobValue.getType());
            if (strategy != null) {
                if (jobValue.getType().equals(Strategy.DATABASE)) {
                    JSONObject connectionObj = getConnectionByJobJson(content, jobValue.getReadBy());
                    connectionObj.set("value", jobValue.getValue());
                    jobValue.setValue(strategy.getValue(connectionObj.toString()));
                } else {
                    jobValue.setValue(strategy.getValue(jobValue.getValue()));
                }
            }
            // where替换
            if (parameter.containsKey("where")) {
                String where = parameter.getStr("where");
                if (where.contains("${" + key + "}")) {
                    where = where.replace("${" + key + "}", jobValue.getValue());
                }
                parameter.set("where", where);
            }
            // querySql替换
            JSONArray connection = parameter.getJSONArray("connection");
            for (int i = 0; i < connection.size(); i++) {
                JSONObject connectionObj = connection.getJSONObject(i);
                if (connectionObj.containsKey("querySql")) {
                    JSONArray querySqls = connectionObj.getJSONArray("querySql");
                    for (int j = 0; j < querySqls.size(); j++) {
                        String querySql = querySqls.getStr(j);
                        if (querySql.contains("${" + key + "}")) {
                            querySql = querySql.replace("${" + key + "}", jobValue.getValue());
                        }
                        querySqls.set(j, querySql);
                    }
                }
            }
        }
    }

    /**
     * 根据job json配置获取相应的连接，如果配置的是reader，那么读取的reader的连接信息，如果配置的是writer，同样读取的是writer
     * @param content job json读取的content
     * @param readBy 两种值，reader、writer
     * @return 连接属性，jdbcUrl、username、password
     */
    private JSONObject getConnectionByJobJson(JSONArray content, String readBy) {
        if (StringUtils.isEmpty(readBy)) {
            return new JSONObject();
        }
        JSONObject connectionObj = new JSONObject();
        for (int i = 0; i < content.size(); i++) {
            JSONObject contentObj = content.getJSONObject(i);
            for (Map.Entry<String, Object> entry : contentObj.entrySet()) {
                String key = entry.getKey();
                if (!readBy.equals(key)) {
                    continue;
                }
                JSONObject value = (JSONObject) entry.getValue();
                JSONObject parameter = value.getJSONObject("parameter");
                connectionObj.set("username", parameter.getStr("username"));
                connectionObj.set("password", parameter.getStr("password"));
                if (!connectionObj.containsKey("jdbcUrl")) {
                    connectionObj.set("jdbcUrl", new JSONArray());
                }
                JSONArray connection = (JSONArray) parameter.get("connection");
                for (int j = 0; j < connection.size(); j++) {
                    JSONObject connectionJSONObject = connection.getJSONObject(j);
                    JSONArray jdbcUrl = connectionObj.getJSONArray("jdbcUrl");
                    Object connectJdbcUrl = connectionJSONObject.get("jdbcUrl");
                    if (connectJdbcUrl instanceof JSONArray) {
                        jdbcUrl.addAll((JSONArray) connectJdbcUrl);
                    } else {
                        jdbcUrl.add(connectJdbcUrl);
                    }
                }
            }
        }
        return connectionObj;
    }

    /**
     * 检查writer是否有数据，如有writeMode则跟新为update，如果没有writeMode为insert，并删除reader的where过滤条件
     * @param content job content数据
     */
    private void checkWriterIsValue(JSONArray content) throws SQLException {
        boolean isFull = false;
        for (int i = 0; i < content.size(); i++) {
            JSONObject contentObj = content.getJSONObject(i);
            for (Map.Entry<String, Object> entry : contentObj.entrySet()) {
                String key = entry.getKey();
                if ("writer".equals(key)) {
                    JSONObject writerObj = (JSONObject) entry.getValue();
                    JSONObject parameter = writerObj.getJSONObject("parameter");
                    String username = parameter.getStr("username");
                    String password = parameter.getStr("password");
                    JSONArray connectArray = parameter.getJSONArray("connection");
                    for (int j = 0; j < connectArray.size(); j++) {
                        JSONObject connectObj = connectArray.getJSONObject(j);
                        String jdbcUrl = connectObj.getStr("jdbcUrl");
                        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                        PreparedStatement preparedStatement = connection.prepareStatement(config.getCheckSql().replace("${table}", connectObj.getJSONArray("table").getStr(0)));
                        ResultSet set = preparedStatement.executeQuery();
                        BeanHandler<Map> bean = new BeanHandler<>(Map.class);
                        Map handle = bean.handle(set);
                        if (handle == null) {
                            isFull = true;
                            parameter.set("writeMode", "insert");
                        }
                        set.close();
                        preparedStatement.close();
                        connection.close();
                    }
                }
            }
            for (Map.Entry<String, Object> entry : contentObj.entrySet()) {
                String key = entry.getKey();
                if ("reader".equals(key)) {
                    JSONObject readerObj = (JSONObject) entry.getValue();
                    JSONObject parameter = readerObj.getJSONObject("parameter");
                    if (isFull) {
                        parameter.remove("where");
                    }
                }
            }
        }
    }

    private String generateTemJsonFile(String jobJson, String path) {
        String tmpFilePath;
        if (!FileUtil.exist(path)) {
            FileUtil.mkdir(path);
        }
        tmpFilePath = path + "jobTmp-" + IdUtil.simpleUUID() + ".conf";
        // 根据json写入到临时本地文件
        try (PrintWriter writer = new PrintWriter(tmpFilePath, "UTF-8")) {
            writer.println(jobJson);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            logger.info("JSON 临时文件写入异常：" + e.getMessage());
        }
        return tmpFilePath;
    }
}
