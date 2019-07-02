package ru.cft.focusstart.shepotenko;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

public class ProducerTask implements Runnable {
    private final int produceTime;
    private final ArrayBlockingQueue<Resource> store;
    private final Logger logger = LoggerFactory.getLogger(ProducerTask.class);

    ProducerTask(int produceTime, ArrayBlockingQueue<Resource> store) {
        this.produceTime = produceTime;
        this.store = store;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(this.produceTime);
                Resource res = new Resource();
                logger.info("prodused res" + res.getId());
                store.put(res);
                logger.info("put  res" + res.getId());
            } catch (InterruptedException e) {
                logger.error("was interrupted");
            }
        }
    }
}
