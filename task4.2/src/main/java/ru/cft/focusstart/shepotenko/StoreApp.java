package ru.cft.focusstart.shepotenko;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class StoreApp {
    private static final int STORE_CAPACITY = 20;
    private static final int PRODUCER_THREADS_COUNT = 4;
    private static final int CONSUMER_THREADS_COUNT = 4;
    private static final int TIME_UNIT = 1000;

    public static void main(String[] args) {
        ArrayBlockingQueue<Resource> store = new ArrayBlockingQueue<>(STORE_CAPACITY);
        ExecutorService producer = Executors.newFixedThreadPool(PRODUCER_THREADS_COUNT, new NameableThreadFactory("producer"));
        ExecutorService consumer = Executors.newFixedThreadPool(CONSUMER_THREADS_COUNT, new NameableThreadFactory("consumer"));
        for (int i = 0; i < PRODUCER_THREADS_COUNT; i++) {
            consumer.submit(new ConsumerTask(TIME_UNIT, store));
        }
        for (int i = 0; i < CONSUMER_THREADS_COUNT; i++) {
            producer.submit(new ProducerTask(TIME_UNIT, store));
        }
    }
}
