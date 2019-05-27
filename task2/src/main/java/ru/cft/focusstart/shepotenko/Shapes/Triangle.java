package ru.cft.focusstart.shepotenko.Shapes;

import ru.cft.focusstart.shepotenko.ShapeException;

public class Triangle extends Shape {

    private double sideA;
    private double sideB;
    private double sideC;
    private double oppositeAngleA;
    private double oppositeAngleB;
    private double oppositeAngleC;

    public Triangle(double sideA, double sideB, double sideC) throws ShapeException {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new ShapeException("Invalid input data. Parameters must be positive.");
        }
        if (sideA >= sideB + sideC || sideB >= sideA + sideC || sideC >= sideA + sideB) {
            throw new ShapeException("Invalid input data.\nEach side of the triangle must be less than the sum of the other two");
        }
        this.name = "треугольник";
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        this.perimeter = calcPerimeter();
        this.area = calcArea();
        this.oppositeAngleA = calcOppositeAngle(sideA, sideB, sideC);
        this.oppositeAngleB = calcOppositeAngle(sideB, sideA, sideC);
        this.oppositeAngleC = calcOppositeAngle(sideC, sideA, sideB);
    }
    @Override
    protected double calcPerimeter() {

        return sideA + sideB + sideC;
    }

    @Override
    protected double calcArea() {
        double p = perimeter / 2;
        return Math.sqrt(p * (p - sideA) * (p - sideB) * (p - sideC));
    }

    private double calcOppositeAngle(double oppositeSide, double side1, double side2) {

        return Math.toDegrees(Math.acos(((side1 * side1) + (side2 * side2) - (oppositeSide * oppositeSide)) / (2 * side1 * side2)));
    }

    @Override
    public String getInfo() {

        String info = String.format("Тип фигуры:                      %s\n", name) +
                String.format("Cторона а и противолежащий угол: %.2f  %.1f\n", sideA, oppositeAngleA) +
                String.format("Cторона b и противолежащий угол: %.2f  %.1f\n", sideB, oppositeAngleB) +
                String.format("Cторона c и противолежащий угол: %.2f  %.1f\n", sideC, oppositeAngleC) +
                String.format("Площадь:                         %.2f\n", area) +
                String.format("Периметр:                        %.2f\n", perimeter);
        return info;
    }


}


