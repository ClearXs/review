package com.jw.opentracing;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SpanTest {

    public static void main(String[] args) {
        Tracer tracer = GlobalTracer.get();
        Tracer.SpanBuilder builder = tracer.buildSpan("test");
        Span start = builder.start();
        tracer.scopeManager().activate(start, true);


        Tracer.SpanBuilder spanBuilder = tracer.buildSpan("test").ignoreActiveSpan();

        Scope activate = tracer.scopeManager().activate(start, true);
        CompletableFuture.supplyAsync(new Supplier<Object>() {
            @Override
            public Object get() {
                return tracer.scopeManager().activate(start, true);
            }
        }).thenRun(new Runnable() {
            @Override
            public void run() {
                start.finish();
            }
        });
    }
}
