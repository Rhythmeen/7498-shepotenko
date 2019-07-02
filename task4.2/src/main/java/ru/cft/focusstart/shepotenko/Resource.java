package ru.cft.focusstart.shepotenko;

import java.util.concurrent.locks.Lock;

class Resource {
    private static final Object LOCK = new Object();
    private static int counter = 0;
    private int id;

    Resource() {
        synchronized (LOCK) {
            this.id = counter++;
        }
    }

    int getId() {
        return id;
    }

}
