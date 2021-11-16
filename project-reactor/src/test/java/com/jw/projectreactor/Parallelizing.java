package com.jw.projectreactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Parallelizing {

    @Test
    public void parallel() {
        Flux.range(1, 10)
                .parallel(2)
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
    }

    @Test
    public void runOn() {
        Flux.range(1, 10)
                .parallel(2)
                .runOn(Schedulers.parallel())
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
    }

}
