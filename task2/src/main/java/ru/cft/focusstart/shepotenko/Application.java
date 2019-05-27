package ru.cft.focusstart.shepotenko;


import ru.cft.focusstart.shepotenko.Shapes.Shape;

import java.util.ArrayList;

public class Application {
    public static void main(String[] args) {

        if (args.length < 1 || args.length > 2) {
            System.out.println("Wrong number of input parameters. Specify input file and output file(optionally)");
            System.exit(1);
        }

        Parser parser = new Parser(args[0]);
        String shapeType = parser.getShapeType();
        ArrayList<Double> shapeParams = parser.getShapeParams();

        ShapeFactory shapeFactory = new ShapeFactory();


        try {
            Shape shape = shapeFactory.createShape(shapeType, shapeParams);
            Printer printer = new Printer();
            if (args.length == 1) {
                printer.printToConsole(shape.getInfo());
            } else {
                printer.printToFile(args[1],shape.getInfo());
            }
        } catch (AppException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}





