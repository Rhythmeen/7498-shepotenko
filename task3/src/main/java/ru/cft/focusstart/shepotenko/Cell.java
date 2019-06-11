package ru.cft.focusstart.shepotenko;

import java.util.ArrayList;
import java.util.HashSet;

public class Cell {
    private int innerValue; //0 - пустая клетка, 1-8 - цифры, 9 - взорвался, 10 - бомба 11 - бомбы нет, ошибка.
    private int state;      //0 - закрыто, 1- открыто, 2 - флаг, 3 - вопрос
    private ArrayList<Integer> CellsAround;

    public Cell() {
    }

    public void setInnerValue(int innerValue) {
        this.innerValue = innerValue;
    }

    public int getInnerValue() {
        return innerValue;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public ArrayList<Integer> getCellsAround() {
        return CellsAround;
    }

    public void setCellsAround(ArrayList<Integer> cellsAround) {
        CellsAround = cellsAround;
    }
}
