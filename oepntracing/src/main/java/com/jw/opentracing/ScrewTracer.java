package com.jw.opentracing;

import io.opentracing.*;
import io.opentracing.propagation.Format;
import io.opentracing.util.ThreadLocalScopeManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ScrewTracer implements Tracer {

    /**
     * @see ScopeManager
     * @see Scope Scope的作用是管理当前激活的span
     */
    private final ScopeManager scopeManager;

    /**
     * @see Reporter
     */
    private final Reporter reporter;

    public ScrewTracer() {
        this(new MemoryReporter(), new ThreadLocalScopeManager());
    }

    public ScrewTracer(Reporter reporter) {
        this(reporter, new ThreadLocalScopeManager());
    }

    public ScrewTracer(Reporter reporter, ScopeManager scopeManager) {
        this.reporter = reporter;
        this.scopeManager = scopeManager;
    }

    @Override
    public ScopeManager scopeManager() {
        return scopeManager;
    }

    @Override
    public Span activeSpan() {
        Scope activeScope = scopeManager.active();
        if (activeScope == null) {
            return null;
        }
        return activeScope.span();
    }

    @Override
    public ScrewSpanBuild buildSpan(String operationName) {
        return new ScrewSpanBuild(operationName);
    }

    @Override
    public <C> void inject(SpanContext spanContext, Format<C> format, C carrier) {

    }


    @Override
    public <C> SpanContext extract(Format<C> format, C carrier) {
        return null;
    }

    void reportSpan(ScrewSpan span) {
        reporter.report(span);
    }

    public Reporter getReporter() {
        return reporter;
    }

    class ScrewSpanBuild implements Tracer.SpanBuilder {

        /**
         * 当前span的操作名称
         */
        private String operationName;

        /**
         * span的标签
         */
        private final Map<String, Object> tags;

        /**
         * span开启时间
         */
        private long startTime = 0;

        /**
         * 是否忽略当前active的span，指定span为parent
         */
        private boolean ignoreActiveSpan = false;

        /**
         * 当前span的引用链
         */
        private List<ScrewReference> references = Collections.emptyList();

        public ScrewSpanBuild(String operationName) {
            this.operationName = operationName;
            this.tags = new ConcurrentHashMap<>();
        }

        @Override
        public ScrewSpanBuild asChildOf(SpanContext parent) {
            return addReference(References.CHILD_OF, parent);
        }

        @Override
        public ScrewSpanBuild asChildOf(Span parent) {
            return asChildOf(parent.context());
        }

        @Override
        public ScrewSpanBuild addReference(String referenceType, SpanContext referencedContext) {
            if (referenceType == null || referenceType.toCharArray().length == 0) {
                return this;
            }
            if (referencedContext == null) {
                return this;
            }
            if (!(referencedContext instanceof ScrewSpanContext)) {
                return this;
            }
            ScrewSpanContext spanContext = (ScrewSpanContext) referencedContext;
            if (references.isEmpty()) {
                // 当前span只有一个父span（或）
                references = Collections.singletonList(new ScrewReference(referenceType, spanContext));
            } else {
                if (references.size() == 1) {
                    references = new ArrayList<>(references);
                } else {
                    references.add(new ScrewReference(referenceType, spanContext));
                }
            }
            return this;
        }

        @Override
        public ScrewSpanBuild ignoreActiveSpan() {
            ignoreActiveSpan = true;
            return this;
        }

        @Override
        public ScrewSpanBuild withTag(String key, String value) {
            this.tags.put(key, value);
            return this;
        }

        @Override
        public ScrewSpanBuild withTag(String key, boolean value) {
            this.tags.put(key, value);
            return this;
        }

        @Override
        public ScrewSpanBuild withTag(String key, Number value) {
            this.tags.put(key, value);
            return this;
        }

        @Override
        public ScrewSpanBuild withStartTimestamp(long microseconds) {
            this.startTime = microseconds;
            return this;
        }

        @Override
        public Scope startActive(boolean finishSpanOnClose) {
            return null;
        }

        @Override
        public Span startManual() {
            return null;
        }

        @Override
        public ScrewSpan start() {
            // 1.创建引用关系（如果是子的话）
            if (!ignoreActiveSpan && scopeManager.active() != null) {
                asChildOf(scopeManager.active().span());
            }
            // 2.创建上下文（TraceId、SpanId、baggage）
            ScrewSpanContext spanContext;
            if (references.isEmpty()) {
                spanContext = createParentContext();
            } else {
                spanContext = createChildContext();
            }
            // 3.创建开始时间
            if (startTime == 0L) {
                startTime = System.currentTimeMillis();
            }
            return new ScrewSpan(
                    ScrewTracer.this,
                    references,
                    tags,
                    null,
                    spanContext,
                    operationName,
                    startTime
            );
        }

        /**
         * 创建当前traceId
         * @return {@link ScrewSpanContext}
         */
        private ScrewSpanContext createParentContext() {
            String traceId = UUID.randomUUID().toString();
            return new ScrewSpanContext(traceId, "0");
        }

        private ScrewSpanContext createChildContext() {
            // 寻找parentSpanContext
            ScrewReference exceptedReference = references.get(0);
            for (ScrewReference reference : references) {
                // !References.CHILD_OF.equals(exceptedReference.getType())可能存在FOLLOWS_FROM
                if (References.CHILD_OF.equals(reference.getType())
                        && !References.CHILD_OF.equals(exceptedReference.getType())) {
                    exceptedReference = reference;
                    break;
                }
            }
            ScrewSpanContext spanContext = exceptedReference.getSpanContext();
            // 子span构建父span的baggage，类似于继承的关系
            Map<String, String> baggage = null;
            for (ScrewReference reference : references) {
                Map<String, String> parentBaggage = reference.getSpanContext().getBaggage();
                if (parentBaggage != null) {
                    if (baggage == null) {
                        baggage = new HashMap<>();
                    }
                    baggage.putAll(parentBaggage);
                }
            }
            return new ScrewSpanContext(
                    spanContext.getTraceId(),
                    UUID.randomUUID().toString(),
                    baggage
            );
        }
    }
}
