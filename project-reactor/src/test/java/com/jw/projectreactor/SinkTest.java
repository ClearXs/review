package com.jw.projectreactor;

import org.junit.jupiter.api.Test;

public class SinkTest {

    @Test
    public void testSinkBuffer() throws InterruptedException {
        Sink.buffer();

        Thread.currentThread().join();
    }
}
