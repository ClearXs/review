package com.jw.springbootdatax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobLog {

    private static JobLog log;

    private final Logger logger;

    private JobLog() {
        logger = LoggerFactory.getLogger(JobLog.class);
    }

    public synchronized void log(String param) {
        logger.info(param);
    }

    public static JobLog getInstance() {
        if (log == null) {
            synchronized (JobLog.class) {
                if (log == null) {
                    log = new JobLog();
                }
            }
        }

        return log;
    }
}
