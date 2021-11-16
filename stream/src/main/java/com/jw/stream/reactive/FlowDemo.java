package com.jw.stream.reactive;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class FlowDemo {

    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        StringSubscriber subscriber = new StringSubscriber();
        IntegerProcessor processor = new IntegerProcessor();
        publisher.subscribe(processor);


        processor.subscribe(subscriber);
        for (int i = 0; i < 1100; i++) {
            publisher.submit(i);
        }

        publisher.close();

        Thread.currentThread().join();

    }

    static class StringSubscriber implements Flow.Subscriber<String> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(String item) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("接收数据: " + item);
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
            subscription.cancel();
        }

        @Override
        public void onComplete() {
            System.out.println("subscribe完成");
            subscription.cancel();
        }
    }

    /**
     * processor接口提供中间处理能力,如过滤,加功数据
     * @author jw
     * @date 2021/11/7 14:29
     */
    static class IntegerProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer, String> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(Integer item) {
            String transferItem = "transfer: " + item;
            System.out.println("转换数据为: " + transferItem);
            submit(transferItem);
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();

            subscription.cancel();
        }

        @Override
        public void onComplete() {
            System.out.println("processor完成");
            close();
            subscription.cancel();
        }
    }
}
