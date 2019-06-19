package ru.cft.focusstart.shepotenko.Model;

public enum CellValue {
    EMPTY(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    BOMB(9); //чтобы не геттился по значению (int = 0)

    private int num;

    cellValue(int num) {
        this.num = num;
    }

    public int getNum() {
        return this.num;
    }

    public static cellValue getByInt(int valueToGet) {
        for (cellValue value : cellValue.values()) {
            if (value.num == valueToGet) {
                return value;
            }
        }
        return EMPTY;
    }
}



