package ru.cft.focusstart.shepotenko;


import java.util.*;

public class Model {
    private int gridWidth;
    private int gridLength;
    private int amountOfBombs;
    private Cell[] gameGrid;
    private boolean isGameGridEmpty;
    private int unMarkedBombCounter;


    public Model(int gridWidth, int gridLength, int amountOfBombs) {
        this.gridWidth = gridWidth;
        this.gridLength = gridLength;
        this.amountOfBombs = amountOfBombs;
        this.gameGrid = generateEmptyGameGrid(gridWidth, gridLength);
        this.isGameGridEmpty = true;
        this.unMarkedBombCounter = amountOfBombs;
    }

    private Cell[] generateEmptyGameGrid(int fieldWidth, int fieldLength) {
        Cell[] gameGrid = new Cell[fieldWidth * fieldLength];
        for (int i = 0; i < gameGrid.length; i++) {
            gameGrid[i] = new Cell();
        }
        return gameGrid;
    }

    public void gameGridInit(int firstMove) {
        HashSet<Integer> bombPlaces = new HashSet<>();
        Random r = new Random();
        while (bombPlaces.size() < amountOfBombs) {
            int nextBombPlace = r.nextInt(gridWidth * gridLength);
            if (nextBombPlace != firstMove)
                bombPlaces.add(nextBombPlace);
        }
        for (int i = 0; i < gridWidth * gridLength; i++) {
            gameGrid[i].setCellsAround(calcCellsAround(i));
            if (bombPlaces.contains(i)) {
                gameGrid[i].setInnerValue(9);
            } else {
                int numberOfBombsAround = 0;
                for (int n : gameGrid[i].getCellsAround()) {
                    if (bombPlaces.contains(n)) {
                        numberOfBombsAround++;
                    }
                }
                gameGrid[i].setInnerValue(numberOfBombsAround);
            }
        }
        isGameGridEmpty = false;
    }

    private ArrayList<Integer> calcCellsAround(int cellAddress) {
        ArrayList<Integer> cellsAround = new ArrayList<>();
        cellsAround.add(cellAddress - (gridLength + 1));
        cellsAround.add(cellAddress - gridLength);
        cellsAround.add(cellAddress - (gridLength - 1));
        cellsAround.add(cellAddress + 1);
        cellsAround.add(cellAddress + (gridLength + 1));
        cellsAround.add(cellAddress + gridLength);
        cellsAround.add(cellAddress + (gridLength - 1));
        cellsAround.add(cellAddress - 1);
        if ((cellAddress) - gridLength < 0) {                                     //убираем сверху
            cellsAround.remove(Integer.valueOf(cellAddress - (gridLength + 1)));
            cellsAround.remove(Integer.valueOf(cellAddress - gridLength));
            cellsAround.remove(Integer.valueOf(cellAddress - (gridLength - 1)));
        }
        if ((cellAddress + 1) % gridLength == 0) {                                //убираем справа
            cellsAround.remove(Integer.valueOf(cellAddress - (gridLength - 1)));
            cellsAround.remove(Integer.valueOf(cellAddress + 1));
            cellsAround.remove(Integer.valueOf(cellAddress + (gridLength + 1)));
        }
        if (cellAddress + gridLength >= gridWidth * gridLength) {                //убираем снизу
            cellsAround.remove(Integer.valueOf(cellAddress + (gridLength + 1)));
            cellsAround.remove(Integer.valueOf(cellAddress + gridLength));
            cellsAround.remove(Integer.valueOf(cellAddress + (gridLength - 1)));
        }
        if (cellAddress % gridLength == 0) {                                      //убираем слева
            cellsAround.remove(Integer.valueOf(cellAddress + (gridLength - 1)));
            cellsAround.remove(Integer.valueOf(cellAddress - 1));
            cellsAround.remove(Integer.valueOf(cellAddress - (gridLength + 1)));
        }
        return cellsAround;
    }

    public void openCell(int cellAddress) {
        if (getCellsState(cellAddress) == 0) {
            //ультракудрявый алгоритм, открывает область пустых клеток. надо попробовать порефакторить.
            if (getCellInnerValue(cellAddress) == 0) {
                HashSet<Integer> emptyArea = new HashSet<>();
                ArrayList<Integer> findingEmptyArea = new ArrayList<>();
                findingEmptyArea.add(cellAddress);
                while (findingEmptyArea.size() > 0) {
                    ArrayList<Integer> emptyClosedCellsAround = getEmptyClosedCellsAround(findingEmptyArea.get(0));
                    for (int i : emptyClosedCellsAround) {
                        findingEmptyArea.add(i);
                    }
                    emptyArea.add(findingEmptyArea.get(0));
                    setCellsState(findingEmptyArea.get(0), 1);
                    findingEmptyArea.remove(0);
                }
                HashSet<Integer> cellsToOpen = new HashSet<>();
                for (int i : emptyArea) {
                    for (int j : gameGrid[i].getCellsAround())
                        cellsToOpen.add(j);
                }
                for (int i : cellsToOpen) {
                    setCellsState(i, 1);
                }
                }
            if (getCellInnerValue(cellAddress) > 0 && getCellInnerValue(cellAddress) < 10) {
                setCellsState(cellAddress, 1);
            }
        }
    }

    private ArrayList<Integer> getEmptyClosedCellsAround(int CellAddress) {
        ArrayList<Integer> emptyClosedCellsAround = new ArrayList<>();
        for (int i : gameGrid[CellAddress].getCellsAround()) {
            if (gameGrid[i].getInnerValue() == 0 && gameGrid[i].getState() == 0) {
                emptyClosedCellsAround.add(i);
            }
        }
        return emptyClosedCellsAround;
    }

    private void setCellsState(int cellAddress, int state) {
        gameGrid[cellAddress].setState(state);
    }

    public int getCellsState(int cellAddress) {
        return gameGrid[cellAddress].getState();
    }

    public int getCellInnerValue(int cellAddress) {
        return gameGrid[cellAddress].getInnerValue();
    }

    public ArrayList<Integer> getCellsAround(int cellAddress) {
        return gameGrid[cellAddress].getCellsAround();
    }

    public void setFlag (int cellAddress) {
        gameGrid[cellAddress].setState(2);
    }

    public void setQuestion (int cellAddress) {
        gameGrid[cellAddress].setState(3);
    }


    public void undoQuestion (int cellAddress) {
        gameGrid[cellAddress].setState(0);
    }


    //TODO избавиться от этих двух методов
    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridLength() {
        return gridLength;
    }


    public boolean getIsGameGridEmpty() {
        return isGameGridEmpty;
    }
}



