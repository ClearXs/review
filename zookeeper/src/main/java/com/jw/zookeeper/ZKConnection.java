package com.jw.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZKConnection {

    public static void main(String[] args) throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 30000, event -> {
            Watcher.Event.KeeperState state = event.getState();
            if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                latch.countDown();
            }
        });
        latch.await();
        ZooKeeper.States state = zooKeeper.getState();
        System.out.println(state);
    }
}
