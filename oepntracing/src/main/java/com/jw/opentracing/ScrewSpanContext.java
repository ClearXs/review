package com.jw.opentracing;

import io.opentracing.SpanContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ScrewSpanContext implements SpanContext {

    private String traceId;

    private String spanId;

    private Map<String, String> baggage;

    public ScrewSpanContext(String traceId, String spanId) {
        this(traceId, spanId, new HashMap<>());
    }

    public ScrewSpanContext(String traceId, String spanId, Map<String, String> baggage) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.baggage = baggage;
    }

    @Override
    public Iterable<Map.Entry<String, String>> baggageItems() {
        return baggage.entrySet();
    }

    public String getTraceId() {
        return traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public Map<String, String> getBaggage() {
        return baggage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScrewSpanContext that = (ScrewSpanContext) o;
        return Objects.equals(traceId, that.traceId) && Objects.equals(spanId, that.spanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(traceId, spanId);
    }

    @Override
    public String toString() {
        return "ScrewSpanContext{" +
                "traceId='" + traceId + '\'' +
                ", spanId='" + spanId + '\'' +
                ", baggage=" + baggage +
                '}';
    }
}
