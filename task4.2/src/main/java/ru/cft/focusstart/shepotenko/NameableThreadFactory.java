package ru.cft.focusstart.shepotenko;

import java.util.concurrent.ThreadFactory;

 class NameableThreadFactory implements ThreadFactory{
    private int threadsCounter;
    private final String namePattern;

    NameableThreadFactory(String baseName){
        namePattern = baseName + "-%d";
    }

    @Override
    public Thread newThread(Runnable runnable){
        threadsCounter++;
        return new Thread(runnable, String.format(namePattern, threadsCounter));
    }
}
