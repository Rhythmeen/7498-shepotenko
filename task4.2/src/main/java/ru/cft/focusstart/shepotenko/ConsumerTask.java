package ru.cft.focusstart.shepotenko;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

public class ConsumerTask implements Runnable {
    private final int consumeTime;
    private final ArrayBlockingQueue<Resource> store;
    private final Logger logger = LoggerFactory.getLogger(ConsumerTask.class);

    ConsumerTask(int consumeTime, ArrayBlockingQueue<Resource> store) {
        this.consumeTime = consumeTime;
        this.store = store;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Resource res = store.take();
                logger.info("took res" + res.getId());
                Thread.sleep(consumeTime);
                logger.info("consumed res" + res.getId());
            } catch (InterruptedException e) {
                logger.error("was interrupted");
            }
        }
    }
}
