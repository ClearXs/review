package com.jw.opentracing;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrewSpan implements Span {

    /**
     * @see io.opentracing.Tracer
     */
    private ScrewTracer tracer;

    /**
     * {@link Tags}
     */
    private Map<String, Object> tags;

    private Map<String, String> baggage;

    /**
     * @see SpanContext
     */
    private ScrewSpanContext spanContext;

    /**
     * 操作名称
     */
    private String operationName;

    /**
     * 日志
     */
    private List<ScrewLog> logs;

    /**
     * @see io.opentracing.References
     */
    private List<ScrewReference> references;

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 结束时间
     */
    private long endTime;

    public ScrewSpan(ScrewTracer tracer, List<ScrewReference> references, Map<String, Object> tags, Map<String, String> baggage, ScrewSpanContext spanContext, String operationName, long startTime) {
        this(tracer, references, tags, baggage, spanContext, operationName, startTime, new ArrayList<>());
    }

    public ScrewSpan(ScrewTracer tracer, List<ScrewReference> references, Map<String, Object> tags, Map<String, String> baggage, ScrewSpanContext spanContext, String operationName, long startTime, List<ScrewLog> logs) {
        this.tracer = tracer;
        this.references = references;
        this.tags = tags;
        this.baggage = baggage;
        this.spanContext = spanContext;
        this.operationName = operationName;
        this.startTime = startTime;
        this.logs = logs;
    }

    @Override
    public ScrewSpanContext context() {
        return spanContext;
    }

    @Override
    public synchronized Span setTag(String key, String value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public synchronized Span setTag(String key, boolean value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public synchronized Span setTag(String key, Number value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public Span log(Map<String, ?> fields) {
        return log(System.currentTimeMillis(), fields);
    }

    @Override
    public synchronized Span log(long timestampMicroseconds, Map<String, ?> fields) {
        logs.add(new ScrewLog(timestampMicroseconds, fields));
        return this;
    }

    @Override
    public Span log(String event) {
        return log(System.currentTimeMillis(), event);
    }

    @Override
    public synchronized Span log(long timestampMicroseconds, String event) {
        Map<String, String> fields = new HashMap<>();
        fields.put(Fields.EVENT, event);
        return log(timestampMicroseconds, fields);
    }

    @Override
    public synchronized Span setBaggageItem(String key, String value) {
        baggage.put(key, value);
        return this;
    }

    @Override
    public String getBaggageItem(String key) {
        return baggage.get(key);
    }

    @Override
    public synchronized Span setOperationName(String operationName) {
        this.operationName = operationName;
        return this;
    }

    public Object getTag(String tagName) {
        return tags.get(tagName);
    }

    public List<ScrewReference> getReferences() {
        return references;
    }

    @Override
    public void finish() {
        finish(System.currentTimeMillis());
    }

    @Override
    public void finish(long finishMicros) {
        endTime = finishMicros;
        tracer.reportSpan(this);
    }

    @Override
    public String toString() {
        return "ScrewSpan{" +
                "tracer=" + tracer +
                ", tags=" + tags +
                ", operationName='" + operationName + '\'' +
                ", logs=" + logs +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
