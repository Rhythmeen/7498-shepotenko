package ru.cft.focusstart.shepotenko;

import java.util.Scanner;


public class Multitable {
    public static void main(String[] args) {

        int tableSize;

        System.out.println("введите размер таблицы");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();
            try {
                tableSize = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("размер таблицы должен быть целым числом от 1 до 32 включительно");
                continue;
            }
            if (tableSize < 1 || tableSize > 32) {
                System.out.println("размер таблицы должен быть целым числом от 1 до 32 включительно");
                continue;
            }
            break;
        }

        int cellSize = getCellSize(tableSize);
        printTable(tableSize, cellSize);
    }

    private static void printTable(int tableSize, int cellSize) {

        for (int i = 1; i <= tableSize; i++) {
            for (int j = 1; j <= (tableSize - 1); j++) {
                int value = i * j;
                System.out.printf("%" + cellSize + "d|", value);
            }
            System.out.printf("%" + cellSize + "d\n", (i * tableSize));
            printSeparatingLine(cellSize, tableSize);
            System.out.println();
        }
    }

    private static int getCellSize(int tableSize) {
        int longestNumber = tableSize * tableSize;
        return String.valueOf(longestNumber).length();
    }

    private static void printSeparatingLine(int cellSize, int rowSize) {
        for (int i = 0; i < (rowSize - 1); i++) {
            for (int j = 0; j < cellSize; j++) {
                System.out.print("-");
            }
            System.out.print("+");
        }
        for (int i = 0; i < cellSize; i++) {
            System.out.print("-");
        }
    }
}