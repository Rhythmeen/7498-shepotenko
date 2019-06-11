package ru.cft.focusstart.shepotenko;


import java.util.*;

public class Model {
    private int gridWidth;
    private int gridLength;
    private int amountOfBombs;
    private Cell[] gameGrid;
    private boolean isGameGridEmpty;


    public Model(int gridWidth, int gridLength, int amountOfBombs) {
        this.gridWidth = gridWidth;
        this.gridLength = gridLength;
        this.amountOfBombs = amountOfBombs;
        this.gameGrid = generateEmptyGameGrid(gridWidth, gridLength);
        this.isGameGridEmpty = true;

    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridLength() {
        return gridLength;
    }

    public Cell[] getGameGrid() {
        return gameGrid;
    }

    public boolean getIsGameGridEmpty() {
        return isGameGridEmpty;
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
        gameGrid[cellAddress].setState(1);
    }

    public void flagCell (int cellAddress) {
        gameGrid[cellAddress].setState(2);
    }

    public void questionCell (int cellAddress) {
        gameGrid[cellAddress].setState(3);
    }

    public void closeCell (int cellAddress) {
        gameGrid[cellAddress].setState(0);
    }

    //TODO наверное стоит запилить геттеры полей  ячеек через модель, а то выглядит оч ебано геттинг в контроллере

}



