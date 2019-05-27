package ru.cft.focusstart.shepotenko.Shapes;

public abstract class Shape {

    protected String name;
    protected double area;
    protected double perimeter;

    protected abstract double calcPerimeter();

    protected abstract double calcArea();

    public abstract String getInfo();
}
