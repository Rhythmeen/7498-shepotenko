package ru.cft.focusstart.shepotenko;

import java.util.HashSet;

public class Cell {
    private int innerValue; //0 - пустая клетка, 1-8 - цифры, 9 - бомба
    private int outerValue; //0 - пусто, 1 -флаг, 2 - вопрос
    private boolean isOpened;
    private HashSet<Coordinate> cellsAround;

    public Cell() {}

    public void setCellsAround(HashSet<Coordinate> cellsAround) {
        this.cellsAround = cellsAround;
    }

    public void setInnerValue(int innerValue) {
        this.innerValue = innerValue;
    }

    public int getInnerValue() {
        return innerValue;
    }

    @Override
    public String toString() {
        return Integer.toString(innerValue);
    }
}
