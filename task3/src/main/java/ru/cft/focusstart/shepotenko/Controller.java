package ru.cft.focusstart.shepotenko;

import java.util.ArrayList;
import java.util.HashSet;

public class Controller {
    private Model model;


    public Controller(Model model) {
        this.model = model;
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

    //TODO метод открывает все ячейки вокруг, если количество флажков соответсвует кол-ву бомб
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
}
