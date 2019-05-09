package ru.cft.focusstart.shepotenko;

import java.awt.*;
import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private String shapeType;
    private ArrayList<Double> shapeParams;

    public Parser(String inputFileName) {
        try
                (FileReader fileReader = new FileReader(inputFileName)) {
            BufferedReader reader = new BufferedReader(fileReader);

            this.shapeType = reader.readLine();
            String[] tempParams = reader.readLine().split(" ");
            this.shapeParams = parseParams(tempParams);
        } catch (IOException e) {
            System.out.println("Error reading from input file");
            System.exit(1);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Invalid input parameters");
            System.exit(1);
        }
    }

    private static ArrayList<Double> parseParams(String[] tempParams) {
        ArrayList<Double> params = new ArrayList<>();
        for (String param : tempParams) {
            double value = Double.parseDouble(param);
            params.add(value);
        }
        return params;
    }

    public String getShapeType() {
        return shapeType;
    }

    public ArrayList<Double> getShapeParams() {
        return shapeParams;
    }
}
