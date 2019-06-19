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

    CellValue(int num) {
        this.num = num;
    }

    public int getNum() {
        return this.num;
    }

    public static CellValue getByInt(int valueToGet) {
        for (CellValue value : CellValue.values()) {
            if (value.num == valueToGet) {
                return value;
            }
        }
        return EMPTY;
    }
}



