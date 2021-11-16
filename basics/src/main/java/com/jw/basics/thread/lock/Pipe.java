package com.jw.basics.thread.lock;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Pipe {

    static class ReaderThread implements Runnable{

        private PipedReader pipedReader;

        public ReaderThread(PipedReader pipedReader) {
            this.pipedReader = pipedReader;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = pipedReader.read()) != -1) {
                    System.out.println((char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class WriterThread implements Runnable {

        private PipedWriter pipedWriter;

        public WriterThread(PipedWriter pipedWriter) {
            this.pipedWriter = pipedWriter;
        }

        @Override
        public void run() {
            try {
                pipedWriter.write("test");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    pipedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);
        new Thread(new ReaderThread(pipedReader)).start();
        Thread.sleep(100);
        new Thread(new WriterThread(pipedWriter)).start();
    }
}
