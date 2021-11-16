package com.jw.opentracing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 简单的内存存储span
 * @author jiangw
 * @date 2020/12/17 21:34
 * @since 1.0
 */
public class MemoryReporter implements Reporter {

    private List<ScrewSpan> spans;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public MemoryReporter() {
        spans = new ArrayList<>();
    }

    @Override
    public void report(ScrewSpan span) {
        lock.readLock().lock();
        if (span != null) {
            lock.readLock().unlock();
            // 锁升级为写锁
            lock.writeLock().lock();
            try {
                spans.add(span);
            } finally {
                lock.writeLock().unlock();
            }
        } else {
            lock.readLock().unlock();
        }
    }

    @Override
    public void close() {

    }

    public List<ScrewSpan> getSpans() {
        return spans;
    }
}
