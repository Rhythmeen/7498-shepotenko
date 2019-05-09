package ru.cft.focusstart.shepotenko.Shapes;

import ru.cft.focusstart.shepotenko.AppException;

public class Rectangle extends Shape {

    private String name;
    private double area;
    private double perimeter;
    private double length;
    private double width;
    private double diagonalLength;

    public Rectangle(double param1, double param2) throws AppException {
        this.name = "прямоугольник";
        if (param1 <= 0 || param2 <= 0) {
            throw new AppException("Invalid input data. Parameters must be positive.");
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
    public StringBuilder getInfo() {
        StringBuilder info = new StringBuilder();

        info.append(String.format("Тип фигуры:      %s\n", name));
        info.append(String.format("Длина:           %.2f\n", length));
        info.append(String.format("Ширина:          %.2f\n", width));
        info.append(String.format("Длина диагонали: %.2f\n", diagonalLength));
        info.append(String.format("Периметр:        %.2f\n", perimeter));
        info.append(String.format("Площадь:         %.2f\n", area));


        return info;
    }

    private double calcArea() {
        return length * width;
    }

    private double calcPerimeter() {
        return (length + width) * 2;
    }

    private double calcDiagonalLength() {
        return Math.sqrt(length * length + width * width);
    }

}

