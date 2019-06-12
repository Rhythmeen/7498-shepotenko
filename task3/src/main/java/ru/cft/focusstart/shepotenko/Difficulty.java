package ru.cft.focusstart.shepotenko;

public enum Difficulty {
    EASY(9,9,10),
    NORMAL(16,16,40),
    HARD(16,30,99);

    private int width;
    private int length;
    private int amountOfBombs;

    Difficulty(int  width, int length, int amountOfBombs) {
        this.width = width;
        this.length = length;
        this.amountOfBombs = amountOfBombs;
    }
}
