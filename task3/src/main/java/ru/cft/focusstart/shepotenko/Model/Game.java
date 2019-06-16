package ru.cft.focusstart.shepotenko.Model;


import ru.cft.focusstart.shepotenko.GUI.GameStateObserver;

import java.util.HashSet;
import java.util.Random;

public class Game {
    private Difficulty difficulty;
    private Field field;
    private int unmarkedBombsCounter;
    private int openedCellsCounter;
    private int cellsWithoutBomb;
    private GameStateObserver observer;

    public Game(Difficulty dif, GameStateObserver observer) {
        this.difficulty = dif;
        this.field = new Field(dif.getSizeX(), dif.getSizeY(), dif.getAmountOfBombs());
        this.unmarkedBombsCounter = dif.getAmountOfBombs();
        this.cellsWithoutBomb = dif.getSizeX() * dif.getSizeY() - dif.getAmountOfBombs();
        this.observer = observer;
    }

    public void pressOne(int xCoord, int yCoord) {
        if (field.isGameGridEmpty()) {
            initGameGrid(xCoord, yCoord);
            observer.gameStarted();
        }
        openCell(xCoord, yCoord);
        if (openedCellsCounter == cellsWithoutBomb) {
            win();
            observer.onWin();
        }
        observer.updateUI();
    }

    public void pressTwo(int xCoord, int yCoord) {
        Cell clickedCell = field.getCell(xCoord, yCoord);
        if (clickedCell.isOpened()) {
            int flagsAround = 0;
            int questionsAround = 0;
            for (int[] coord : field.getCellsNeighboursAddresses(xCoord, yCoord)) {
                if (field.getCell(coord[0], coord[1]).isFlaged()) {
                    flagsAround++;
                }
                if (field.getCell(coord[0], coord[1]).isQuestioned()) {
                    questionsAround++;
                }
            }
            if (flagsAround == clickedCell.getValue().getNum() && questionsAround == 0) {
                openCell(xCoord, yCoord);
                for (int[] coord : field.getCellsNeighboursAddresses(xCoord, yCoord)) {
                    if (!clickedCell.isFlaged()) {
                        openCell(coord[0], coord[1]);
                    }
                }
            }
            if (openedCellsCounter == cellsWithoutBomb) {
                win();
                observer.onWin();
            }
        }
        observer.updateUI();
    }
    public void pressThree(int xCoord, int yCoord) {
        Cell clickedCell = this.field.getCell(xCoord, yCoord);
        if (clickedCell.isClosed() && this.unmarkedBombsCounter > 0) {
            clickedCell.setFlaged();
            unmarkedBombsCounter--;
        } else if (clickedCell.isFlaged()) {
            clickedCell.setQuestioned();
            unmarkedBombsCounter++;
        } else if (clickedCell.isQuestioned()) {
            clickedCell.setClosed();
        }
        observer.updateUI();
    }

    public void initGameGrid(int xCoord, int yCoord) {
        HashSet<Integer> bombPlaces = new HashSet<>();
        Random r = new Random();
        int firstMove = convertXY2Address(xCoord, yCoord);
        while (bombPlaces.size() < field.getAmountOfBombs()) {
            int nextBombPlace = r.nextInt(field.getSizeX() * field.getSizeY());
            if (nextBombPlace != firstMove) {
                bombPlaces.add(nextBombPlace);
            }
        }
        for (int x = 0; x < field.getSizeX(); x++) {
            for (int y = 0; y < field.getSizeY(); y++) {
                if (bombPlaces.contains(convertXY2Address(x, y))) {
                    field.getCell(x, y).setValue(CellValue.BOMB);
                } else {
                    int numberOfBombsAround = 0;
                    for (int[] coord : field.getCellsNeighboursAddresses(x, y)) {
                        if (bombPlaces.contains(convertXY2Address(coord[0], coord[1]))) {
                            numberOfBombsAround++;
                        }
                    }
                    field.getCell(x, y).setValue(CellValue.getByInt(numberOfBombsAround));
                }
            }
        }
        field.setIsGameGridEmpty(false);
    }

    public void openCell(int xCoord, int yCoord) {
        Cell clickedCell = field.getCell(xCoord, yCoord);
        if (!clickedCell.isClosed()) {
            return;
        }
        if (clickedCell.getValue() == CellValue.BOMB) {
            loose(xCoord, yCoord);
        }
        if (clickedCell.getValue() != CellValue.EMPTY && clickedCell.getValue() != CellValue.BOMB) {
            clickedCell.setOpened();
            openedCellsCounter++;
        }
        if (clickedCell.getValue() == CellValue.EMPTY) {
            openAllAround(xCoord, yCoord);
        }
    }

    private void openAllAround(int xCoord, int yCoord) {
        Cell clickedCell = field.getCell(xCoord, yCoord);
        clickedCell.setOpened();
        openedCellsCounter++;
        for (int[] coord : field.getCellsNeighboursAddresses(xCoord, yCoord)) {
            openCell(coord[0], coord[1]);
        }
    }

    public void win() {
        for (int x = 0; x < field.getSizeX(); x++) {
            for (int y = 0; y < field.getSizeY(); y++) {
                Cell cell = field.getCell(x, y);
                if (cell.getValue() == CellValue.BOMB && !cell.isFlaged()) {
                    cell.setFlaged();
                }
            }
        }
    }

    public void loose(int coordX, int coordY) {
        Cell clickedCell = field.getCell(coordX, coordY);
        clickedCell.setExploded();
        for (int x = 0; x < field.getSizeX(); x++) {
            for (int y = 0; y < field.getSizeY(); y++) {
                Cell cell = field.getCell(x, y);
                if (cell.getValue() == CellValue.BOMB && cell.isClosed()) {
                    cell.setOpened();
                }
                if (cell.getValue() != CellValue.BOMB && cell.isFlaged()) {
                    cell.setMistake();
                }
            }
        }
        observer.onLoose();
        observer.updateUI();
    }

    public int[] convertAddress2XY(int coord) {
        return new int[]{coord / field.getSizeY(), coord % field.getSizeY(),};
    }

    public int convertXY2Address(int xCoord, int yCoord) {
        return xCoord * field.getSizeX() + yCoord;
    }

    public Cell getCell(int xCoord, int yCoord) {
        return field.getCell(xCoord, yCoord);
    }

    public int getSizeX() {
        return field.getSizeX();
    }

    public int getSizeY() {
        return field.getSizeY();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getUnmarkedBombsCounter() {
        return unmarkedBombsCounter;
    }
}
