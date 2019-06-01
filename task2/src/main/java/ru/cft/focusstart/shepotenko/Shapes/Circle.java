package ru.cft.focusstart.shepotenko.Shapes;

import ru.cft.focusstart.shepotenko.ShapeException;

public class Circle extends Shape {
    private double radius;
    private double diameter;

    public Circle(double radius) throws ShapeException {
        this.name = ShapeType.CIRCLE.getName();
        if (radius <= 0) {
            throw new ShapeException("Invalid input data. Parameters must be positive.");
        }
        this.radius = radius;
        this.diameter = calcDiameter();
        this.area = calcArea();
        this.perimeter = calcPerimeter();
    }

    @Override
    protected double calcArea() {
        return Math.PI * radius * radius;
    }

    @Override
    protected double calcPerimeter() {
        return 2 * Math.PI * radius;
    }

    private double calcDiameter() {
        return diameter = radius * 2;
    }

    @Override
    public String getInfo() {
        return String.format("Тип фигуры: %s\n", name) +
                String.format("Радиус:     %.2f\n", radius) +
                String.format("Диаметр:    %.2f\n", diameter) +
                String.format("Периметр:   %.2f\n", perimeter) +
                String.format("Площадь:    %.2f\n", area);
    }
}
