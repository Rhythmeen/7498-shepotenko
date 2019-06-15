package ru.cft.focusstart.shepotenko;

import ru.cft.focusstart.shepotenko.Model.Model;

public class Game {
    public Model model;


    public Game(int gridWidth, int gridLength, int amountOfBombs, GameStateObserver observer) {
        this.model = new Model(gridWidth, gridLength, amountOfBombs, observer);
    }

    public void leftClick(int cellAddress) {
        if (model.getIsGameGridEmpty()) {
            model.gameGridInit(cellAddress);
        }
        model.openCell(cellAddress);
    }

    public void rightClick(int cellAddress) {
        switch (model.getCellsState(cellAddress)) {
            case 0:
                model.setFlag(cellAddress);
                break;
            case 2:
                model.setQuestion(cellAddress);
                break;
            case 3:
                model.undoQuestion(cellAddress);
                break;
        }
    }


    public void middleClick(int cellAddress) {
        if (model.getCellInnerValue(cellAddress) > 0 && model.getCellInnerValue(cellAddress) < 9) {
            int flagsAround = 0;
            for (int i : model.getCellsAround(cellAddress)) {
                if (model.getCellsState(i) == 2) {
                    flagsAround++;
                }
            }
            if (flagsAround == model.getCellInnerValue(cellAddress)) {
                model.openCell(cellAddress);
                for (int i : model.getCellsAround(cellAddress)) {
                    if (model.getCellsState(i) != 2) {
                        model.openCell(i);
                    }
                }
            }
        }
    }





    public int getCellsState (int CellAddress) {
        return model.getCellsState(CellAddress);
    }

    public int getCellsInnerValue(int CellAddress) {
        return model.getCellInnerValue(CellAddress);
    }

    public int getWidth() {
        return model.getWidth();
    }

    public int getLength() {
        return model.getLength();
    }

}
