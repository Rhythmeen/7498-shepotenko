package ru.cft.focusstart.shepotenko.Shapes;

public enum ShapeType {
    CIRCLE("круг", 1),
    RECTANGLE("прямоугольник", 2),
    TRIANGLE("треугольник", 3);

    private final String name;
    private final int requiredParametersCount;

    ShapeType(String name, int requiredParametersCount) {
        this.name = name;
        this.requiredParametersCount = requiredParametersCount;
    }

    public String getName() {
        return name;
    }

    public int getRequiredParametersCount() {
        return requiredParametersCount;
    }
}