package com.jw.current;

import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

public class TaskSchedule {

    public static void main(String[] args) throws InterruptedException {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel();
        EventLoop eventExecutors = embeddedChannel.eventLoop();
        System.out.println(eventExecutors);
        ScheduledFuture<?> schedule = embeddedChannel.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("32");
            }
        }, 1, TimeUnit.SECONDS);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
