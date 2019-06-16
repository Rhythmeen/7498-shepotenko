package ru.cft.focusstart.shepotenko;

import ru.cft.focusstart.shepotenko.Shapes.*;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            System.out.println("Wrong number of input parameters. Specify input file and output file(optionally)");
            System.exit(1);
        }
        String shapeType = null;
        ArrayList<Double> shapeParams = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileReader(args[0]))) {
            shapeType = scanner.nextLine();
            while (scanner.hasNextDouble()) {
                shapeParams.add(scanner.nextDouble());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading from file, file not found");
            System.exit(1);
        } catch (NoSuchElementException e) {
            System.out.println("invalid input data. Example for a correctly filled input file:\n TRIANGLE\n 4 2 5");
            System.exit(1);
        }

        Shape shape = null;
        try {
            shape = createShape(shapeType, shapeParams);
        } catch (ShapeException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        if (args.length == 1) {
            System.out.println(shape.getInfo());
        } else {
            printToFile(args[1], shape.getInfo());
        }
    }

public static void printToFile(String outputFileName, String text) {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.write(text);
        } catch (IOException e) {
            System.out.println("Error printing to file");
            System.exit(1);
        }
    }

    public static Shape createShape(String shapeType, ArrayList<Double> shapeParams) throws ShapeException, IndexOutOfBoundsException {
        if (shapeType.equals(ShapeType.CIRCLE.name()) && shapeParams.size() == ShapeType.CIRCLE.getRequiredParametersCount()) {
            return new Circle(shapeParams.get(0));
        } else if (shapeType.equals(ShapeType.RECTANGLE.name()) && shapeParams.size() == ShapeType.RECTANGLE.getRequiredParametersCount()) {
            return new Rectangle(shapeParams.get(0), shapeParams.get(1));
        } else if (shapeType.equals(ShapeType.TRIANGLE.name()) && shapeParams.size() == ShapeType.TRIANGLE.getRequiredParametersCount()) {
            return new Triangle(shapeParams.get(0), shapeParams.get(1), shapeParams.get(2));
        } else {
            throw new ShapeException("invalid input data. Example for a correctly filled input file:\n TRIANGLE\n 4 2 5");
        }
    }
}





