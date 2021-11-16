package com.jw.opentracing;

import io.opentracing.*;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;

import java.util.HashMap;
import java.util.Map;

public class ScopeManagerTest {

    public static void main(String[] args) {
        Tracer tracer = GlobalTracer.get();
        // 创建并启动这个span
        Span span = tracer.buildSpan("test").start();
        ScopeManager scopeManager = tracer.scopeManager();
        try {
            // 在当前线程激活这个span
            Scope activate = scopeManager.activate(span, true);
            if (span == activate.span()) {
                System.out.println(true);
            }

            int i = 1 / 0;
            // do something... 或调用子span
        } catch (Exception e) {
            // 表示当前span是以错误状态结束
            Tags.ERROR.set(span, true);
            Map<String, Object> map = new HashMap<>();
            map.put(Fields.EVENT, "error");
            map.put(Fields.ERROR_OBJECT, e);
            map.put(Fields.MESSAGE, e.getMessage());
            span.log(map);
        } finally {
            span.finish();
        }
    }
}
