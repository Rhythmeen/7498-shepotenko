package ru.cft.focusstart.shepotenko.Model;


import ru.cft.focusstart.shepotenko.Game;

import java.util.*;

public class Model {
    private int sizeX;
    private int sizeY;
    private int amountOfBombs;
    private Cell[][] gameGrid;
    private boolean isGameGridEmpty;


    public Model(int sizeX, int sizeY, int amountOfBombs) {
        //TODO проверка на размеры и кол-во бомб
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
                gameGrid[x][y] = new Cell();
            }
        }
        return gameGrid;
    }

    public ArrayList<int[]> getNeighboursAddresses(int xCoord, int yCoord) {
        int[][] neighbours = {
                {xCoord - 1, yCoord - 1}, {xCoord, yCoord - 1}, {xCoord + 1, yCoord - 1},
                {xCoord - 1, yCoord}, {xCoord + 1, yCoord},
                {xCoord - 1, yCoord + 1}, {xCoord, yCoord + 1}, {xCoord + 1, yCoord + 1}
        };
        ArrayList<int[]> result = new ArrayList<>();
        for (int[] coords : neighbours) {
            boolean inSizeY = (coords[0] >= 0) && (coords[0] < sizeY);
            boolean inSizeX = (coords[1] >= 0) && (coords[1] < sizeX);
            if (inSizeY && inSizeX) {
                result.add(coords);
            }
        }
        return result;
    }

    public void setCellState(int x, int y, int state) {
        gameGrid[x][y].setState(state);
    }

    public int getCellState(int x, int y) {
        return gameGrid[x][y].getState();
    }

    public void setCellInnerValue(int x, int y, int innerValue) {
        gameGrid[x][y].setInnerValue(innerValue);
    }

    public int getCellInnerValue(int x, int y) {
        return gameGrid[x][y].getInnerValue();
    }

    public int getAmountOfBombs() {
        return amountOfBombs;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public boolean getIsGameGridEmpty() {
        return isGameGridEmpty;
    }

    public void setIsGameGridEmpty(boolean gameGridEmpty) {
        isGameGridEmpty = gameGridEmpty;
    }
}
//    private ArrayList<Integer> calcCellsAround(int cellAddress) {
//        ArrayList<Integer> cellsAround = new ArrayList<>();
//        cellsAround.add(cellAddress - (sizeY + 1));
//        cellsAround.add(cellAddress - sizeY);
//        cellsAround.add(cellAddress - (sizeY - 1));
//        cellsAround.add(cellAddress + 1);
//        cellsAround.add(cellAddress + (sizeY + 1));
//        cellsAround.add(cellAddress + sizeY);
//        cellsAround.add(cellAddress + (sizeY - 1));
//        cellsAround.add(cellAddress - 1);
//        if ((cellAddress) - sizeY < 0) {                                     //убираем сверху
//            cellsAround.remove(Integer.valueOf(cellAddress - (sizeY + 1)));
//            cellsAround.remove(Integer.valueOf(cellAddress - sizeY));
//            cellsAround.remove(Integer.valueOf(cellAddress - (sizeY - 1)));
//        }
//        if ((cellAddress + 1) % sizeY == 0) {                                //убираем справа
//            cellsAround.remove(Integer.valueOf(cellAddress - (sizeY - 1)));
//            cellsAround.remove(Integer.valueOf(cellAddress + 1));
//            cellsAround.remove(Integer.valueOf(cellAddress + (sizeY + 1)));
//        }
//        if (cellAddress + sizeY >= sizeX * sizeY) {                //убираем снизу
//            cellsAround.remove(Integer.valueOf(cellAddress + (sizeY + 1)));
//            cellsAround.remove(Integer.valueOf(cellAddress + sizeY));
//            cellsAround.remove(Integer.valueOf(cellAddress + (sizeY - 1)));
//        }
//        if (cellAddress % sizeY == 0) {                                      //убираем слева
//            cellsAround.remove(Integer.valueOf(cellAddress + (sizeY - 1)));
//            cellsAround.remove(Integer.valueOf(cellAddress - 1));
//            cellsAround.remove(Integer.valueOf(cellAddress - (sizeY + 1)));
//        }
//        return cellsAround;
//    }





