package com.jw.opentracing;

import io.opentracing.Span;

/**
 * 当span {@link Span#finish()}时进行调用，或者在内存储存、ES、结束的span。或者Log for span，或者remote
 *
 * @author jiangw
 * @date 2020/12/17 21:30
 * @since 1.0
 */
public interface Reporter {

    /**
     * @param span {@link ScrewSpan}
     */
    void report(ScrewSpan span);

    void close();
}
