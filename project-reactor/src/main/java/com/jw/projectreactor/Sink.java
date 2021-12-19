package com.jw.projectreactor;

import cn.hutool.core.collection.CollectionUtil;
import org.reactivestreams.Subscription;
import org.springframework.util.Assert;
import reactor.core.publisher.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Sink {


    public static void main(String[] args) throws InterruptedException {
//        generate();
//        create();
        push();
    }


    public static void generate() {

        // 动态参数类型
        Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long num = state.getAndIncrement();
                    sink.next("3 x " + num + " = " + 3 * num);
                    if (num == 10) {
                        sink.complete();
                    }
                    return state;
                },
                state -> System.out.println(state)
        ).subscribe(System.out::println);
    }

    public static void create() {
        MessageProcessor<String> processor = new MessageProcessor<>();
        Flux.create(sink -> {
            processor.register(new MessageEvent<String>() {
                @Override
                public void onDataChunk(List<String> chunk) {
                    chunk.forEach(sink::next);
                }

                @Override
                public void processComplete() {
                    sink.complete();
                }

                @Override
                public void processError(Throwable e) {
                    sink.error(e);
                }
            });
            sink.onRequest(n -> {
                processor.request((int) n)
                        .forEach(sink::next);

            });
        }).subscribe(new BaseSubscriber<Object>() {
            private AtomicInteger counter = new AtomicInteger(1);
            @Override
            protected void hookOnNext(Object value) {
                System.out.println(value);
                int count = counter.getAndIncrement();
                if (count < 4) {
                    request(count);
                }
            }
        });
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                List<String> list = Arrays.asList("foo", "boo", "bar");
                processor.chunk(list);

                // 延迟1s后完成
                MessageEvent<String> event = processor.getEvent();
                try {
                    Thread.currentThread().join(1000);
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                    event.processError(e);
                }
                event.processComplete();
            }
        });
    }

    public static void push() throws InterruptedException {

        MessageProcessor<String> processor = new MessageProcessor<>();

        custom observable = new custom();

        observable.addObserver(new Business("jw"));

        MessageEvent<String> messageEvent;
        // 异步获取商家名字
        Flux.push(sink -> {
            processor.register(new MessageEvent<String>() {
                @Override
                public void onDataChunk(List<String> chunk) {

                }

                @Override
                public void processComplete() {
                    observable.setChanged();
                    observable.notifyObservers(sink);
                }

                @Override
                public void processError(Throwable e) {

                }
            });
        }).subscribe(System.out::println);


        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                processor.getEvent().processComplete();
            }
        });
    }

    public static void buffer() {
        EmitterProcessor<Integer> processor = EmitterProcessor.create(false);
        FluxSink<Integer> sink = processor.sink(FluxSink.OverflowStrategy.BUFFER);

        processor.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected Subscription upstream() {
                return super.upstream();
            }

            @Override
            public boolean isDisposed() {
                return super.isDisposed();
            }

            @Override
            public void dispose() {
                super.dispose();
            }

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                super.hookOnSubscribe(subscription);
            }

            @Override
            protected void hookOnNext(Integer value) {
                super.hookOnNext(value);
            }

            @Override
            protected void hookOnComplete() {
                super.hookOnComplete();
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                super.hookOnCancel();
            }

            @Override
            protected void hookFinally(SignalType type) {
                super.hookFinally(type);
            }

            @Override
            public String toString() {
                return super.toString();
            }
        });

        sink.next(1);

    }

    static class custom extends Observable {

        @Override
        public synchronized void setChanged() {
            super.setChanged();
        }
    }

    static class Business implements Observer {

        private String name;

        public Business(String name) {
            this.name = name;
        }

        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof FluxSink) {
                ((FluxSink) arg).next(this.name);
            }
        }
    }


    static class MessageProcessor<T> {

        private List<?> chunk;

        private MessageEvent<T> event;

        public void register(MessageEvent<T> event) {
            this.event = event;
        }

        public List<T> request(int index) {
            if (index < 0) {
                return Collections.emptyList();
            }
            if (CollectionUtil.isEmpty(chunk)) {
                return Collections.emptyList();
            }
            if (index > chunk.size()) {
                return Collections.emptyList();
            }
            return (List<T>) chunk.subList(0, index);
        }

        public void chunk(List<T> chunk) {
            Assert.notNull(event, "event is null");
            this.chunk = chunk;
            event.onDataChunk(chunk);
        }

        public MessageEvent<T> getEvent() {
            return this.event;
        }
    }

    interface MessageEvent<T> {

        void onDataChunk(List<T> chunk);

        void processComplete();

        void processError(Throwable e);
    }

}
