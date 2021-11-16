package com.jw.springbootdatax;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jw.springbootdatax.config.DataXConfig;
import com.jw.springbootdatax.job.QuartzJob;
import com.mysql.jdbc.JDBC4Connection;
import com.mysql.jdbc.JDBC4MySQLConnection;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringBootDataxApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataXConfig config;

    @Test
    void contextLoads() {
    }

    @Test
    public void testQuartz() throws SchedulerException {
        // 1.创建scheduler工厂
        StdSchedulerFactory factory = new StdSchedulerFactory();
        // 2.获取调度器
        Scheduler scheduler = factory.getScheduler();
        // 3.创建JobDetails
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                // job 描述
                .withDescription("first quartz job")
                // job name 与 group
                .withIdentity("quartz job", "quartz")
                .build();
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withDescription("")
                .withIdentity("quartz trigger", "trigger")
                .startAt(new Date())
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    @Test
    public void testJDBC() {
        try {
            Connection connection = dataSource.getConnection();
            connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet set = metaData.getTables(catalog(), "test", tableNamePattern(), types());
            while (set.next()) {
                ResultSetMetaData metaData1 = set.getMetaData();
                System.out.println(metaData1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(dataSource);
    }


    /**
     * a catalog name; must match the catalog name as it is stored in the database; "" retrieves those without a catalog; null means that the catalog name should not be used to narrow the search
     */
    protected String catalog(){
        return null;
    }

    /**
     * a table name pattern; must match the table name as it is stored in the database
     */
    protected String tableNamePattern(){
        return "%";
    }

    /**
     * a list of table types, which must be from the list of table types returned from {@link DatabaseMetaData.getTableTypes},to include; null returns all types
     */
    protected String[] types() {

        return new String[]{"TABLE", "VIEW"};
    }

    @Test
    public void modifyJson() {
        String s = FileUtil.readUtf8String("D:\\tools\\datax\\datax\\bin\\job\\mysql\\mysql-mysql-read-write.json");
        JSONObject jsonObject = JSONUtil.parseObj(s);
        JSONObject newObj = JSONUtil.parseObj(s);
        System.out.println(jsonObject);
        boolean isInfo = jsonObject.containsKey("info");
        if (isInfo) {
            newObj.remove("info");
            JSONObject info = jsonObject.getJSONObject("info");
            JSONObject job = newObj.getJSONObject("job");
            JSONArray content = job.getJSONArray("content");
            JSONObject reader = null;
            for (int i = 0; i < content.size(); i++) {
                JSONObject obj = content.getJSONObject(i);
                for (Map.Entry<String, Object> entry : obj.entrySet()) {
                    String key = entry.getKey();
                    if ("reader".equals(key)) {
                        reader = (JSONObject) entry.getValue();
                        break;
                    }
                }
            }
            if (reader != null) {
                JSONObject parameter = reader.getJSONObject("parameter");
                for (Map.Entry<String, Object> entry : info.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    // 查找替换
                    String where = parameter.getStr("where");
                    if (where.contains("${" + key + "}")) {
                        where = where.replace("${" + key + "}", value.toString());
                    }
                    parameter.set("where", where);
                }
            }
        }
    }

    @Test
    public void testDatax() throws InterruptedException {
        JobHandler handler = JobHandler.getInstance(config);
        handler.push("D:\\tools\\datax\\datax\\bin\\job\\mysql\\mysql-mysql-read-write.json");

        TimeUnit.SECONDS.sleep(1000);
    }
}
