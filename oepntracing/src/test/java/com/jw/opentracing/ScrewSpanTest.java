package com.jw.opentracing;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScrewSpanTest {

    private ScrewTracer screwTracer;

    @BeforeEach
    public void init() {
        screwTracer = new ScrewTracer();
    }

    @Test
    public void createSpan() {
        Span test = screwTracer.buildSpan("test").start();
        SpanContext context = test.context();
        System.out.println(context);
    }

    @Test
    public void createChildSpan() {
        ScrewSpan parent = screwTracer.buildSpan("parent").start();
        screwTracer.scopeManager().activate(parent, true);
        ScrewSpan child = screwTracer.buildSpan("child").start();

        ScrewSpanContext parentContext = parent.context();
        ScrewSpanContext childContext = child.context();
        Assertions.assertEquals(parentContext.getTraceId(), childContext.getTraceId());
    }

    @Test
    public void withTags() {
        ScrewSpan span = screwTracer.buildSpan("tags").withTag("test1", "test1").start();
        Assertions.assertEquals(span.getTag("test1"), "test1");
    }

    @Test
    public void withStartTime() {
        ScrewSpan span = screwTracer.buildSpan("startTime").withStartTimestamp(100).start();
        System.out.println(span);
    }

    /**
     * 当前结构
     *              activeSpan
     *             /          \
     *        childSpan1    childSpan2
     */
    @Test
    public void testRelationship() {
        ScrewSpan parent = screwTracer.buildSpan("parent").withTag("test1", "test1").start();
        screwTracer.scopeManager().activate(parent, true);
        ScrewSpan child1 = screwTracer.buildSpan("child1").withTag("test1", "test1").start();
        ScrewSpan child2 = screwTracer.buildSpan("child2").withTag("test1", "test1").start();
    }

    /**
     *      parent1
     *      /     \
     *   child1    parent1
     *                  \
     *                   child2
     */
    @Test
    public void testForefather() {
        ScrewSpan parent1 = screwTracer.buildSpan("parent").withTag("test1", "test1").start();
        screwTracer.scopeManager().activate(parent1, true);
        ScrewSpan parent2 = screwTracer.buildSpan("parent2").withTag("test1", "test1").start();
        ScrewSpan child1 = screwTracer.buildSpan("child1").withTag("test1", "test1").start();
        screwTracer.scopeManager().activate(parent2, true);
        ScrewSpan child2 = screwTracer.buildSpan("child2").withTag("test1", "test1").start();

        // 测试parent1 -> parent2 - child1
        ScrewSpanContext context = parent1.context();
        List<ScrewReference> references = parent2.getReferences();
        Assertions.assertEquals(context, references.get(0).getSpanContext());
        references = child1.getReferences();
        Assertions.assertEquals(context, references.get(0).getSpanContext());

        // 测试parent2 -> child2
        context = parent2.context();
        references = child2.getReferences();
        Assertions.assertEquals(context, references.get(0).getSpanContext());
    }

    @Test
    public void testFinish() {
        ScrewSpan parent = screwTracer.buildSpan("parent").withTag("test1", "test1").start();
        try {
            screwTracer.scopeManager().activate(parent, true);
            ScrewSpan child = screwTracer.buildSpan("child").withTag("test1", "test1").start();
            child.finish();
        } finally {
            parent.finish();
        }
        Reporter reporter = screwTracer.getReporter();
        if (reporter instanceof MemoryReporter) {
            MemoryReporter memoryReporter = (MemoryReporter) reporter;
            List<ScrewSpan> spans = memoryReporter.getSpans();
            for (ScrewSpan span : spans) {
                System.out.println(span);
            }
        }
    }

}
