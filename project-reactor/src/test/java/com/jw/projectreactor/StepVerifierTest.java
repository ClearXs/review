package com.jw.projectreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class StepVerifierTest {

    @Test
    public void testAppendError() {
        StepVerifier.create(Flux.just("foo", "bar"))
                .expectNext("bar")
                .expectError(AssertionError.class)
                .verify();
    }
}
