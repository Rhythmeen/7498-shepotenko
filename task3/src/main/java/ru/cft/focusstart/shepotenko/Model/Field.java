package ru.cft.focusstart.shepotenko.Model;


import java.util.*;

class Field {
    private int sizeX;
    private int sizeY;
    private int amountOfBombs;
    private Cell[][] gameGrid;
    private boolean isGameGridEmpty;

    Field(int sizeX, int sizeY, int amountOfBombs) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.amountOfBombs = amountOfBombs;
        this.gameGrid = generateEmptyGameGrid();
        this.isGameGridEmpty = true;
    }

    private Cell[][] generateEmptyGameGrid() {
        Cell[][] gameGrid = new Cell[this.sizeX][this.sizeY];
        for (int x = 0; x < this.sizeX; x++) {
            for (int y = 0; y < this.sizeY; y++) {
                Cell cell = new Cell();
                gameGrid[x][y] = cell;
            }
        }
        return gameGrid;
    }

    ArrayList<int[]> getCellsNeighboursAddresses(int xCoord, int yCoord) {
        int[][] neighbours = {
                {xCoord - 1, yCoord - 1}, {xCoord - 1, yCoord}, {xCoord - 1, yCoord + 1},
                {xCoord, yCoord - 1}, {xCoord, yCoord + 1},
                {xCoord + 1, yCoord - 1}, {xCoord + 1, yCoord}, {xCoord + 1, yCoord + 1}
        };
        ArrayList<int[]> result = new ArrayList<>();
        for (int[] coords : neighbours) {
            boolean inSizeX = (coords[0] >= 0) && (coords[0] < sizeX);
            boolean inSizeY = (coords[1] >= 0) && (coords[1] < sizeY);
            if (inSizeY && inSizeX) {
                result.add(coords);
            }
        }
        return result;
    }

    Cell getCell(int xCoord, int yCoord) {
        return this.gameGrid[xCoord][yCoord];
    }

    int getAmountOfBombs() {
        return this.amountOfBombs;
    }

    int getSizeX() {
        return this.sizeX;
    }

    int getSizeY() {
        return this.sizeY;
    }

    boolean isGameGridEmpty() {
        return this.isGameGridEmpty;
    }

    void setIsGameGridEmpty(boolean isGameGridEmpty) {
        this.isGameGridEmpty = isGameGridEmpty;
    }
}






