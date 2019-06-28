package ru.cft.focusstart.shepotenko;

import java.util.concurrent.Callable;

public class Task implements Callable<Double> {
    private int from;
    private int to;
    private double result;

    Task(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Double call() {
        int justBecause = 0;
        for (int x = from; x <= to; x++) {
            for (int i = x; i < x + 1000; i++) {
                justBecause += i;
            }
            result += x * x / justBecause;
        }
        return result;
    }
}
