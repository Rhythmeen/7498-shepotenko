package ru.cft.focusstart.shepotenko;

import ru.cft.focusstart.shepotenko.Shapes.Circle;
import ru.cft.focusstart.shepotenko.Shapes.Rectangle;
import ru.cft.focusstart.shepotenko.Shapes.Shape;
import ru.cft.focusstart.shepotenko.Shapes.Triangle;

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
        } catch (IndexOutOfBoundsException e) {
            System.out.printf("Wrong number of parameters for the specified shape.\nRequired number of parameters for   Circle: 1\n" +
                    "%45s\n%45s", "Rectangle: 2", "Triangle: 3");
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
        }
    }

    public static Shape createShape(String shapeType, ArrayList<Double> shapeParams) throws ShapeException, IndexOutOfBoundsException {
        switch (shapeType) {
            case "CIRCLE":
                return new Circle(shapeParams.get(0));
            case "RECTANGLE":
                return new Rectangle(shapeParams.get(0), shapeParams.get(1));
            case "TRIANGLE":
                return new Triangle(shapeParams.get(0), shapeParams.get(1), shapeParams.get(2));
            default:
                throw new ShapeException("invalid input data. Example for a correctly filled input file:\n TRIANGLE\n 4 2 5");
        }
    }
}





