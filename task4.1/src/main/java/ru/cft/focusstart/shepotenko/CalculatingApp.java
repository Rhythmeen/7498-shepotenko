package ru.cft.focusstart.shepotenko;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CalculatingApp {
    private static final int TASKS_COUNT = 10;
    private static final int TOTAL_NUMBER = 10000000;
    private static final int ONE_TASK_AREA = TOTAL_NUMBER / TASKS_COUNT;
    private static final int REMAINDER = TOTAL_NUMBER % TASKS_COUNT;

    public static void main(String[] args) {
        List<Callable<Double>> callables = new ArrayList<>();
        for (int i = 0; i < TASKS_COUNT; i++) {
            int from = i * ONE_TASK_AREA + 1;
            int to;
            if (i == TASKS_COUNT - 1) {
                to = (i + 1) * ONE_TASK_AREA + REMAINDER;
            } else {
                to = (i + 1) * ONE_TASK_AREA;
            }
            Task task = new Task(from, to);
            callables.add(task);
        }
        ExecutorService executor = Executors.newFixedThreadPool(TASKS_COUNT);
        double result = 0;
        System.out.println("calculating...");
        try {
            List<Future<Double>> futures = executor.invokeAll(callables);
            for (Future<Double> future : futures) {
                result += future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        System.out.println(result);
    }
}
