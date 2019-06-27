package ru.cft.focusstart.shepotenko;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CalculatingApp {
    private static final int lowerLimit = 0;
    private static final int higherLimit = 10000000;
    private static final int oneTaskArea = 1000000;


    public static void main(String[] args) {
        List<Callable<Double>> callables = new ArrayList<>();

        for (int i = lowerLimit + 1; i <= higherLimit; i += oneTaskArea) {
            Task task = new Task(i, i + oneTaskArea);
            callables.add(task);
        }
        ExecutorService executor = Executors.newFixedThreadPool(10);
            double result = 0;
            System.out.println("calculating...");
        try {
            List<Future<Double>>futures = executor.invokeAll(callables);
            for (Future<Double> future: futures) {
                result += future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();

        System.out.println(result);
    }
}
