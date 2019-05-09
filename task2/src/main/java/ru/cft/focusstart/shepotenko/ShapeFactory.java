package ru.cft.focusstart.shepotenko;

import ru.cft.focusstart.shepotenko.Shapes.Circle;
import ru.cft.focusstart.shepotenko.Shapes.Rectangle;
import ru.cft.focusstart.shepotenko.Shapes.Shape;
import ru.cft.focusstart.shepotenko.Shapes.Triangle;

import java.util.ArrayList;

public class ShapeFactory {

    public ShapeFactory() {
    }


    public Shape createShape(String shapeType, ArrayList<Double> shapeParams) throws AppException {
        if (shapeType.equals("CIRCLE") && shapeParams.size() == 1) {
            return new Circle(shapeParams.get(0));
        } else
        if (shapeType.equals("RECTANGLE") && shapeParams.size() == 2) {
            return new Rectangle(shapeParams.get(0),shapeParams.get(1));
        } else
        if (shapeType.equals("TRIANGLE") && shapeParams.size() == 3) {
            return new Triangle(shapeParams.get(0),shapeParams.get(1),shapeParams.get(2));
        }
        else throw new AppException("invalid shape type or parameters");
    }
}
