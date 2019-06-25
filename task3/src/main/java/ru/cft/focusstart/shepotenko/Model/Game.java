package ru.cft.focusstart.shepotenko.Model;


import ru.cft.focusstart.shepotenko.GUI.GameStateObserver;

import javax.swing.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Game {
    private Difficulty difficulty;
    private Field field;
    private int unmarkedBombsCounter;
    private int openedCellsCounter;
    private int cellsWithoutBomb;
    private GameStateObserver observer;
    private RecordManager recordManager;
    private int time;
    private Timer timer;

    public Game(Difficulty dif, GameStateObserver observer) {
        this.difficulty = dif;
        this.field = new Field(dif.getSizeX(), dif.getSizeY(), dif.getAmountOfBombs());
        this.unmarkedBombsCounter = dif.getAmountOfBombs();
        this.cellsWithoutBomb = dif.getSizeX() * dif.getSizeY() - dif.getAmountOfBombs();
        this.observer = observer;
        this.recordManager = new RecordManager(dif);
        this.time = 0;
        this.timer = new Timer(1000, e -> {
            this.time++;
            this.observer.onTimeChanged();
        });
    }

    public void openCell(Coordinate coord) {
        if (this.field.isGameGridEmpty()) {
            initGameGrid(coord);
            this.timer.start();
        }
        doOpenCell(coord);
        this.observer.onFieldChanged();

        if (this.openedCellsCounter == this.cellsWithoutBomb) {
            onWin();
        }
    }

    public void openCellsAround(Coordinate coord) {
        Cell clickedCell = this.field.getCell(coord);
        if (clickedCell.isOpened()) {
            int flagsAround = getFlagsAroundCount(coord);
            int questionsAround = getQuestionsAroundCount(coord);

            if (flagsAround == clickedCell.getValue().getNum() && questionsAround == 0) {
                doOpenCellsAround(coord);
            }
            this.observer.onFieldChanged();

            if (this.openedCellsCounter == this.cellsWithoutBomb) {
                onWin();
            }
        }
    }

    public void markCell(Coordinate coord) {
        Cell clickedCell = this.field.getCell(coord);
        if (clickedCell.isClosed() && this.unmarkedBombsCounter > 0) {
            clickedCell.setFlaged();
            this.unmarkedBombsCounter--;
        } else if (clickedCell.isFlaged()) {
            clickedCell.setQuestioned();
            this.unmarkedBombsCounter++;
        } else if (clickedCell.isQuestioned()) {
            clickedCell.setClosed();
        }
        this.observer.onFieldChanged();
    }

    private void initGameGrid(Coordinate firstMove) {
        Set<Coordinate> bombPlaces = getBombPlaces(firstMove);

        for (int x = 0; x < this.difficulty.getSizeX(); x++) {
            for (int y = 0; y < this.difficulty.getSizeY(); y++) {
                Coordinate coord = new Coordinate(x, y);
                if (bombPlaces.contains(coord)) {
                    this.field.getCell(coord).setValue(CellValue.BOMB);
                } else {
                    int numberOfBombsAround = 0;
                    for (Coordinate c : field.getCellsNeighboursAddresses(coord)) {
                        if (bombPlaces.contains(c)) {
                            numberOfBombsAround++;
                        }
                    }
                    this.field.getCell(coord).setValue(CellValue.getByInt(numberOfBombsAround));
                }
            }
        }
        this.field.setIsGameGridEmpty(false);
    }

    private void doOpenCell(Coordinate coord) {
        Cell clickedCell = this.field.getCell(coord);
        if (!clickedCell.isClosed()) {
            return;
        }
        if (clickedCell.getValue() == CellValue.BOMB) {
            onLoose(coord);
        } else {
            clickedCell.setOpened();
            this.openedCellsCounter++;
            if (clickedCell.getValue() == CellValue.EMPTY) {
                doOpenCellsAround(coord);
            }
        }
    }

    private void doOpenCellsAround(Coordinate coord) {
        for (Coordinate neighbour : this.field.getCellsNeighboursAddresses(coord)) {
            if (this.field.getCell(neighbour).isClosed()) {
                doOpenCell(neighbour);
            }
        }
    }

    private int getFlagsAroundCount(Coordinate coord) {
        int flagsAround = 0;
        for (Coordinate neighbour : this.field.getCellsNeighboursAddresses(coord)) {
            if (this.field.getCell(neighbour).isFlaged()) {
                flagsAround++;
            }
        }
        return flagsAround;
    }

    private int getQuestionsAroundCount(Coordinate coord) {
        int questionsAround = 0;
        for (Coordinate neighbour : this.field.getCellsNeighboursAddresses(coord)) {
            if (this.field.getCell(neighbour).isQuestioned()) {
                questionsAround++;
            }
        }
        return questionsAround;
    }

    private Set<Coordinate> getBombPlaces(Coordinate firstMove) {
        Set<Coordinate> bombPlaces = new HashSet<>();
        Random r = new Random();

        while (bombPlaces.size() < this.field.getAmountOfBombs()) {
            int x = r.nextInt(this.difficulty.getSizeX());
            int y = r.nextInt(this.difficulty.getSizeY());
            Coordinate nextBombPlace = new Coordinate(x, y);
            if (!nextBombPlace.equals(firstMove)) {
                bombPlaces.add(nextBombPlace);
            }
        }
        return bombPlaces;
    }

    private void onWin() {
        for (int x = 0; x < this.difficulty.getSizeX(); x++) {
            for (int y = 0; y < this.difficulty.getSizeY(); y++) {
                Cell cell = this.field.getCell(new Coordinate(x, y));
                if (cell.getValue() == CellValue.BOMB && !cell.isFlaged()) {
                    cell.setFlaged();
                }
            }
        }
        this.timer.stop();
        System.out.println("win");
        this.observer.onWin();
    }

    private void onLoose(Coordinate coord) {
        Cell clickedCell = this.field.getCell(coord);
        clickedCell.setExploded();
        for (int x = 0; x < this.difficulty.getSizeX(); x++) {
            for (int y = 0; y < this.difficulty.getSizeY(); y++) {
                Cell cell = this.field.getCell(new Coordinate(x, y));
                if (cell.getValue() == CellValue.BOMB && cell.isClosed()) {
                    cell.setOpened();
                }
                if (cell.getValue() != CellValue.BOMB && cell.isFlaged()) {
                    cell.setMistake();
                }
            }
        }
        this.timer.stop();
        this.observer.onLoose();
    }


    public Cell getCell(Coordinate coord) {
        return this.field.getCell(coord);
    }

    public int getSizeX() {
        return this.difficulty.getSizeX();
    }

    public int getSizeY() {
        return this.difficulty.getSizeY();
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public int getUnmarkedBombsCounter() {
        return this.unmarkedBombsCounter;
    }

    public RecordManager getRecordManager() {
        return this.recordManager;
    }

    public int getTime() {
        return this.time;
    }

}
