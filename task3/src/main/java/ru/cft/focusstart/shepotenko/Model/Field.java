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

    ArrayList<Coordinate> getCellsNeighboursAddresses(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        Coordinate[] neighbours = {
                new Coordinate(x - 1,y - 1),
                new Coordinate(x - 1,y),
                new Coordinate(x-1,y + 1),
                new Coordinate(x,y - 1),
                new Coordinate(x,y + 1),
                new Coordinate(x + 1,y - 1),
                new Coordinate(x + 1,y),
                new Coordinate(x + 1,y + 1)
        };
        ArrayList<Coordinate> result = new ArrayList<>();
        for (Coordinate coord : neighbours) {
            boolean inSizeX = (coord.getX() >= 0) && (coord.getX() < this.sizeX);
            boolean inSizeY = (coord.getY() >= 0) && (coord.getY() < this.sizeY);
            if (inSizeY && inSizeX) {
                result.add(coord);
            }
        }
        return result;
    }

    Cell getCell(Coordinate coord) {
        return this.gameGrid[coord.getX()][coord.getY()];
    }

    int getAmountOfBombs() {
        return this.amountOfBombs;
    }

    boolean isGameGridEmpty() {
        return this.isGameGridEmpty;
    }

    void setIsGameGridEmpty(boolean isGameGridEmpty) {
        this.isGameGridEmpty = isGameGridEmpty;
    }
}






