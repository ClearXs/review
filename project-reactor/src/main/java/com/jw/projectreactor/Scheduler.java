package com.jw.projectreactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Scheduler {

    public static void main(String[] args) {
        TaskExecutors.of().submit(() -> Flux.range(0, 10)
                .publishOn(Schedulers.parallel())
                .subscribe(state -> {
                    log.info("thread name: {} consume: {}", Thread.currentThread().getName(), state);
                }));
    }
}
