package com.jw.opentracing;

public class ScrewReference {

    /**
     * @see io.opentracing.References
     */
    private String type;

    private ScrewSpanContext spanContext;

    public ScrewReference(String type, ScrewSpanContext spanContext) {
        this.type = type;
        this.spanContext = spanContext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ScrewSpanContext getSpanContext() {
        return spanContext;
    }

    public void setSpanContext(ScrewSpanContext spanContext) {
        this.spanContext = spanContext;
    }
}
