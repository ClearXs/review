package com.jw.basics.collection;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleVector {

    private Vector<String> vector;

    public SimpleVector() {
        vector = new Vector<>();
        vector.add("32");
    }

    public Object getLast(Vector<String> vector) {
        int lastIndex = vector.size() - 1;
        return vector.get(lastIndex);
    }

    public void deleteLast(Vector<String> vector) {
        int lastIndex = vector.size() - 1;
        vector.remove(lastIndex);
    }

    public synchronized Object syncGetLast(Vector<String> vector) {
        int lastIndex = vector.size() - 1;
        return vector.get(lastIndex);
    }

    public synchronized void syncDeleteLast(Vector<String> vector) {
        int lastIndex = vector.size() - 1;
        vector.remove(lastIndex);
    }

    public static void main(String[] args) {
        SimpleVector simpleVector = new SimpleVector();

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Object last = simpleVector.getLast(simpleVector.vector);
                        simpleVector.deleteLast(simpleVector.vector);
                        System.out.println(last);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
