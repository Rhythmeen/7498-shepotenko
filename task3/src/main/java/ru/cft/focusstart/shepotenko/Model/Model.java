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

    private int[] convertAddress2XY(int coord) {
        return new int[]{coord % this.length, coord / this.length};
    }

    private int convertXY2Address(int x, int y) {
        return y * this.length + x;
    }

    private ArrayList<Integer> getNeighboursAddresses(int address) {
        int[] xy = convertAddress2XY(address);
        int x = xy[0];
        int y = xy[1];
        int[][] neighbours = {
                {x-1, y-1}, {x, y-1}, {x+1, y-1},
                {x-1, y}, {x+1, y},
                {x-1, y+1}, {x, y+1}, {x+1, y+1}
        };

        ArrayList<Integer> result = new ArrayList<>();

        for (int[] coords: neighbours) {
            boolean inLength = (coords[0] >= 0) && (coords[0] < this.length);
            boolean inWidth = (coords[1] >= 0) && (coords[1] < this.width);
            if (inLength && inWidth) {
                result.add(convertXY2Address(coords[0], coords[1]));
            }
        }

        return result;
    }

    private Cell[] generateEmptyGameGrid(int fieldWidth, int fieldLength) {
        Cell[] gameGrid = new Cell[fieldWidth * fieldLength];
        for (int i = 0; i < gameGrid.length; i++) {
            gameGrid[i] = new Cell();
        }
        return gameGrid;
    }

    public void gameGridInit(int firstMove) {
        Set<Integer> bombAddresses = new HashSet<>();
        Random r = new Random();
        while (bombAddresses.size() < this.amountOfBombs) {
            int nextBombPlace = r.nextInt(this.width * this.length);
            if (nextBombPlace != firstMove)
                bombAddresses.add(nextBombPlace);
        }

        for (int i = 0; i < gameGrid.length; i++) {
            this.gameGrid[i] = new Cell();

            if (bombAddresses.contains(i)) {
                this.gameGrid[i].setInnerValue(9);
                continue;
            }

            int numberOfBombsAround = 0;
            for (int neighbourCellAddress : this.getNeighboursAddresses(i)) {
                if (bombAddresses.contains(neighbourCellAddress)) {
                    numberOfBombsAround++;
                }
            }
            this.gameGrid[i].setInnerValue(numberOfBombsAround);

        }
        isGameGridEmpty = false;
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
        for (int i: getNeighboursAddresses(cellAddress)) {
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
        return getNeighboursAddresses(cellAddress);
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





