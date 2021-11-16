package com.jw.opentracing;

import java.util.Map;

public class ScrewLog {

    /**
     * record log time
     */
    private long logTime;

    /**
     * @see io.opentracing.log.Fields
     */
    private Map<String, ?> filed;

    public ScrewLog(long logTime, Map<String, ?> filed) {
        this.logTime = logTime;
        this.filed = filed;
    }
}
