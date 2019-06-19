package ru.cft.focusstart.shepotenko.Model;

public enum Difficulty {
    EASY(9,9,10),
    NORMAL(16,16,40),
    HARD(16,30,99);

    private int sizeX;
    private int sizeY;
    private int amountOfBombs;

    Difficulty(int sizeX, int ySize, int amountOfBombs) {
        this.sizeX = sizeX;
        this.sizeY = ySize;
        this.amountOfBombs = amountOfBombs;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getAmountOfBombs() {
        return amountOfBombs;
    }
}
