package ru.cft.focusstart.shepotenko.Shapes;

import ru.cft.focusstart.shepotenko.ShapeException;

public class Rectangle extends Shape {

    private double length;
    private double width;
    private double diagonalLength;

    public Rectangle(double param1, double param2) throws ShapeException {
        this.name = "прямоугольник";
        if (param1 <= 0 || param2 <= 0) {
            throw new ShapeException("Invalid input data. Parameters must be positive.");
        }

        if (param1 < param2) {
            this.width = param1;
            this.length = param2;
        } else {
            this.length = param1;
            this.width = param2;
        }

        this.area = calcArea();
        this.perimeter = calcPerimeter();
        this.diagonalLength = calcDiagonalLength();
    }

    @Override
    protected double calcArea() {
        return length * width;
    }

    @Override
    protected double calcPerimeter() {
        return (length + width) * 2;
    }

    private double calcDiagonalLength() {
        return Math.sqrt(length * length + width * width);
    }

    @Override
    public String getInfo() {

        return String.format("Тип фигуры:      %s\n", name) +
                String.format("Длина:           %.2f\n", length) +
                String.format("Ширина:          %.2f\n", width) +
                String.format("Длина диагонали: %.2f\n", diagonalLength) +
                String.format("Периметр:        %.2f\n", perimeter) +
                String.format("Площадь:         %.2f\n", area);
    }
}

