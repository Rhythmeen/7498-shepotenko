package ru.cft.focusstart.shepotenko.Model;

import java.util.ArrayList;
import java.util.HashSet;

class Cell {
    private int innerValue; //0 - пустая клетка, 1-8 - цифры, 9 - бомба 10 - взорвался 11 - бомбы нет, ошибка.
    private int state;      //0 - закрыто, 1- открыто, 2 - флаг, 3 - вопрос
    private ArrayList<Integer> CellsAround;

    Cell() {
    }

    void setInnerValue(int innerValue) {
        this.innerValue = innerValue;
    }

    int getInnerValue() {
        return innerValue;
    }

    void setState(int state) {
        this.state = state;
    }

    int getState() {
        return state;
    }

    ArrayList<Integer> getCellsAround() {
        return CellsAround;
    }

    void setCellsAround(ArrayList<Integer> cellsAround) {
        CellsAround = cellsAround;
    }
}
