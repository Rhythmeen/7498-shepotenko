package ru.cft.focusstart.shepotenko;

import java.io.FileWriter;
import java.io.IOException;

public class Printer {

    public Printer() {
    }

    public void printToConsole(StringBuilder stringBuilder) {
        System.out.println(stringBuilder);
    }

    public void printToFile(String outputFileName, StringBuilder stringBuilder) {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.write(stringBuilder.toString());
        } catch (IOException e) {
            System.out.println("Error writing to file");
            System.exit(1);
        }
    }
}
