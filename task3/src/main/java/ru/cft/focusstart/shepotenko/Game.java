package ru.cft.focusstart.shepotenko;

import ru.cft.focusstart.shepotenko.Model.Model;
import ru.cft.focusstart.shepotenko.GameStateObserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Game {
    public Model model;
    private int unmarkedBombsCounter;
    private int openedCellsCounter;
    private int cellsWithoutBomb;
    private GameStateObserver observer;


    public Game(int gridWidth, int gridLength, int amountOfBombs, GameStateObserver observer) {
        this.model = new Model(gridWidth, gridLength, amountOfBombs);
        this.unmarkedBombsCounter = amountOfBombs;
        this.cellsWithoutBomb = model.getSizeX() * model.getSizeY() - amountOfBombs;
        this.observer = observer;
    }

    public void leftClick(int xCoord, int yCoord) {
        if (model.getIsGameGridEmpty()) {
            initGameGrid(xCoord, yCoord);
            observer.gameStarted();

        }
        openCell(xCoord, yCoord);
        if (openedCellsCounter == cellsWithoutBomb) {
            win();
            observer.win();
        }
    }

    public void rightClick(int xCoord, int yCoord) {
        switch (model.getCellState(xCoord, yCoord)) {
            case 0:
                model.setCellState(xCoord, yCoord, 2);
                unmarkedBombsCounter--;
                break;
            case 2:
                model.setCellState(xCoord, yCoord, 3);
                unmarkedBombsCounter++;
                break;
            case 3:
                model.setCellState(xCoord, yCoord, 0);
                break;
        }
    }


    public void middleClick(int xCoord, int yCoord) {
        if (model.getCellInnerValue(xCoord, yCoord) > 0 && model.getCellInnerValue(xCoord, yCoord) < 9) {
            int flagsAround = 0;
            int questionsAround = 0;
            for (int[] coord : model.getNeighboursAddresses(xCoord, yCoord)) {
                if (model.getCellState(coord[0], coord[1]) == 2) {
                    flagsAround++;
                }
                if (model.getCellState(coord[0], coord[1]) == 3) {
                    questionsAround++;
                }
            }
            if (flagsAround == model.getCellInnerValue(xCoord, yCoord) && questionsAround == 0) {
                openCell(xCoord, yCoord);
                for (int[] coord : model.getNeighboursAddresses(xCoord, yCoord)) {
                    if (model.getCellState(xCoord, yCoord) != 2) {
                        openCell(coord[0], coord[1]);
                    }
                }
            }
        }
    }

    public void initGameGrid(int xCoord, int yCoord) {
        HashSet<Integer> bombPlaces = new HashSet<>();
        Random r = new Random();
        int chlen = convertXY2Address(xCoord, yCoord);
        while (bombPlaces.size() < model.getAmountOfBombs()) {
            int nextBombPlace = r.nextInt(model.getSizeX() * model.getSizeY());
            if (nextBombPlace != chlen) {
                bombPlaces.add(nextBombPlace);
            }
        }
        for (int x = 0; x < model.getSizeX(); x++) {
            for (int y = 0; y < model.getSizeY(); y++) {
                if (bombPlaces.contains(convertXY2Address(x, y))) {
                    model.setCellInnerValue(x, y, 9);
                } else {
                    int numberOfBombsAround = 0;
                    for (int[] coord : model.getNeighboursAddresses(x, y)) {
                        if (bombPlaces.contains(convertXY2Address(coord[0], coord[1]))) {
                            numberOfBombsAround++;
                        }
                    }
                    model.setCellInnerValue(x, y, numberOfBombsAround);
                }
            }
        }
        model.setIsGameGridEmpty(false);
    }

    public void openCell(int xCoord, int yCoord) {
        if (model.getCellState(xCoord, yCoord) == 0) {
            if (model.getCellInnerValue(xCoord, yCoord) == 9) {
                model.setCellState(xCoord, yCoord, 1);
                loose(xCoord, yCoord);
                observer.loose();
            }
            if (model.getCellInnerValue(xCoord, yCoord) > 0 && model.getCellInnerValue(xCoord, yCoord) < 9) {
                model.setCellState(xCoord, yCoord, 1);
                openedCellsCounter++;
            }
            if (model.getCellInnerValue(xCoord, yCoord) == 0) {
                openAllAround(xCoord, yCoord);
            }
        }
    }

    private void openAllAround(int xCoord, int yCoord) {
        model.setCellState(xCoord, yCoord, 1);
        openedCellsCounter++;
        for (int[] coord : model.getNeighboursAddresses(xCoord, yCoord)) {
            openCell(coord[0], coord[1]);
        }
    }

    public void win() {
        for (int x = 0; x < model.getSizeX(); x++) {
            for (int y = 0; y < model.getSizeY(); y++) {

                if (model.getCellInnerValue(x, y) == 9 && model.getCellState(x, y) != 2) {
                    model.setCellState(x, y, 2);
                }
            }
        }
    }

    public void loose(int coordX, int coordY) {
        model.setCellInnerValue(coordX, coordY, 10);
        for (int x = 0; x < model.getSizeX(); x++) {
            for (int y = 0; y < model.getSizeY(); y++) {
                if (model.getCellInnerValue(x, y) == 9 && model.getCellState(x, y) != 2) {
                    model.setCellState(x, y, 1);
                }
                if (model.getCellInnerValue(x, y) != 9 && model.getCellState(x, y) == 2) {
                    model.setCellInnerValue(x, y, 11);
                    model.setCellState(x, y, 1);
                }
            }
        }
    }


    public int[] convertAddress2XY(int coord) {
        return new int[]{coord / model.getSizeY(), coord % model.getSizeY(),};
    }

    public int convertXY2Address(int xCoord, int yCoord) {
        return xCoord * model.getSizeX() + yCoord;
    }

    public int getCellsState(int cellAddress) {
        return model.getCellState(convertAddress2XY(cellAddress)[0], convertAddress2XY(cellAddress)[1]);
    }

    public int getCellsInnerValue(int cellAddress) {
        return model.getCellInnerValue(convertAddress2XY(cellAddress)[0], convertAddress2XY(cellAddress)[1]);
    }

    public int getSizeX() {
        return model.getSizeX();
    }

    public int getSizeY() {
        return model.getSizeY();
    }

}
