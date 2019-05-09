package ru.cft.focusstart.shepotenko.Shapes;

import ru.cft.focusstart.shepotenko.AppException;

public class Circle extends Shape {

    private String name;
    private double radius;
    private double diameter;
    private double area;
    private double perimeter;


    public Circle(double radius) throws AppException {
        this.name = "круг";
        if (radius <= 0) {
            throw new AppException( "Invalid input data. Parameters must be positive.");
        }
        this.radius = radius;
        this.diameter = calcDiameter();
        this.area  = calcArea();
        this.perimeter = calcPerimeter();
    }

    @Override
    public StringBuilder getInfo () {
        StringBuilder info = new StringBuilder();

        info.append(String.format("Тип фигуры: %s\n", name));
        info.append(String.format("Радиус:     %.2f\n", radius));
        info.append(String.format("Диаметр:    %.2f\n", diameter));
        info.append(String.format("Периметр:   %.2f\n", perimeter));
        info.append(String.format("Площадь:    %.2f\n", area));

        return info;
    }

    private double calcDiameter() {
        return diameter = radius * 2;
    }

    private double calcArea() {
        return Math.PI * radius * radius;
    }

    private double calcPerimeter() {
        return 2 * Math.PI * radius;
    }

}
