package ru.cft.focusstart.shepotenko.Model;


import ru.cft.focusstart.shepotenko.GameStateObserver;


import java.util.*;

public class Model {
    private int width;
    private int length;
    private int amountOfBombs;
    private Cell[] gameGrid;
    private boolean isGameGridEmpty;
    private int unmarkedBombsCounter;
    private int openedCellsCounter;
    private int cellsWithoutBomb;

    private GameStateObserver observer;

    public Model(int width, int length, int amountOfBombs, GameStateObserver observer) {
        this.width = width;
        this.length = length;
        this.amountOfBombs = amountOfBombs;
        this.gameGrid = generateEmptyGameGrid(width, length);
        this.isGameGridEmpty = true;
        this.unmarkedBombsCounter = amountOfBombs;
        this.cellsWithoutBomb = width * length - amountOfBombs;
        this.observer = observer;
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
            int nextBombPlace = r.nextInt(width * length);
            if (nextBombPlace != firstMove)
                bombPlaces.add(nextBombPlace);
        }
        for (int i = 0; i < width * length; i++) {
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
        cellsAround.add(cellAddress - (length + 1));
        cellsAround.add(cellAddress - length);
        cellsAround.add(cellAddress - (length - 1));
        cellsAround.add(cellAddress + 1);
        cellsAround.add(cellAddress + (length + 1));
        cellsAround.add(cellAddress + length);
        cellsAround.add(cellAddress + (length - 1));
        cellsAround.add(cellAddress - 1);
        if ((cellAddress) - length < 0) {                                     //убираем сверху
            cellsAround.remove(Integer.valueOf(cellAddress - (length + 1)));
            cellsAround.remove(Integer.valueOf(cellAddress - length));
            cellsAround.remove(Integer.valueOf(cellAddress - (length - 1)));
        }
        if ((cellAddress + 1) % length == 0) {                                //убираем справа
            cellsAround.remove(Integer.valueOf(cellAddress - (length - 1)));
            cellsAround.remove(Integer.valueOf(cellAddress + 1));
            cellsAround.remove(Integer.valueOf(cellAddress + (length + 1)));
        }
        if (cellAddress + length >= width * length) {                //убираем снизу
            cellsAround.remove(Integer.valueOf(cellAddress + (length + 1)));
            cellsAround.remove(Integer.valueOf(cellAddress + length));
            cellsAround.remove(Integer.valueOf(cellAddress + (length - 1)));
        }
        if (cellAddress % length == 0) {                                      //убираем слева
            cellsAround.remove(Integer.valueOf(cellAddress + (length - 1)));
            cellsAround.remove(Integer.valueOf(cellAddress - 1));
            cellsAround.remove(Integer.valueOf(cellAddress - (length + 1)));
        }
        return cellsAround;
    }

    public void openCell(int cellAddress) {
        if (gameGrid[cellAddress].getState() == 0) {
            if (gameGrid[cellAddress].getInnerValue() == 9) {
                gameGrid[cellAddress].setState(1);
                loose(cellAddress);
                observer.loose();
            }
            if (gameGrid[cellAddress].getInnerValue() > 0  && gameGrid[cellAddress].getInnerValue() < 9) {
                gameGrid[cellAddress].setState(1);
                openedCellsCounter++;
            }
            if (gameGrid[cellAddress].getInnerValue() == 0) {
                openAllAround(cellAddress);
            }
            if (openedCellsCounter == cellsWithoutBomb) {
                win();
                observer.win();
            }
        }

    }
    private void openAllAround (int cellAddress) {
        gameGrid[cellAddress].setState(1);
        openedCellsCounter++;
        for (int i: gameGrid[cellAddress].getCellsAround()) {
            openCell(i);
        }
    }

    public void win() {
        for (Cell cell : gameGrid) {
            if (cell.getInnerValue() == 9 && cell.getState() != 2) {
                cell.setState(2);
            }
        }
    }

    public void loose(int explosionAddress) {
        gameGrid[explosionAddress].setInnerValue(10);
        for (Cell cell : gameGrid) {
            if (cell.getInnerValue() == 9 && cell.getState() != 2) {
                cell.setState(1);
            }
            if (cell.getInnerValue() != 9 && cell.getState() == 2) {
                cell.setInnerValue(11);
                cell.setState(1);
            }
        }
    }


    public void setFlag(int cellAddress) {
        gameGrid[cellAddress].setState(2);
        unmarkedBombsCounter--;
        observer.changeUnmarkedBombsCounter();

    }

    public void setQuestion(int cellAddress) {
        gameGrid[cellAddress].setState(3);
        unmarkedBombsCounter++;
        observer.changeUnmarkedBombsCounter();
    }

    public void undoQuestion(int cellAddress) {
        gameGrid[cellAddress].setState(0);
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

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public boolean getIsGameGridEmpty() {
        return isGameGridEmpty;
    }
}





