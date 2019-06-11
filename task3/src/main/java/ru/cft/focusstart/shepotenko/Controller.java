package ru.cft.focusstart.shepotenko;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Controller {
    private Model model;


    public Controller(Model model) {
        this.model = model;
    }

    public void leftClick(int cellAddress) {
        if (model.getIsGameGridEmpty()) {
            model.gameGridInit(cellAddress);
        }
        //ультракудрявый алгоритм, открывает область пустых клеток. надо попробовать порефакторить.
        if (model.getGameGrid()[cellAddress].getInnerValue() == 0 && model.getGameGrid()[cellAddress].getState() == 0) {
            HashSet<Integer> emptyArea = new HashSet<>();
            ArrayList<Integer> findingEmptyArea = new ArrayList<>();
            findingEmptyArea.add(cellAddress);
            while (findingEmptyArea.size() > 0) {
                ArrayList<Integer> emptyClosedCellsAround = getEmptyClosedCellsAround(findingEmptyArea.get(0));
                for (int i: emptyClosedCellsAround) {
                    findingEmptyArea.add(i);
                }

                emptyArea.add(findingEmptyArea.get(0));
                model.openCell(findingEmptyArea.get(0));
                findingEmptyArea.remove(0);
            }
            HashSet<Integer> cellsToOpen = new HashSet<>();
            for (int i: emptyArea) {
                for (int j: model.getGameGrid()[i].getCellsAround())
                    cellsToOpen.add(j);
            }
            for (int i: cellsToOpen) {
                model.openCell(i);
            }

        }
        if (model.getGameGrid()[cellAddress].getInnerValue() !=0) {
            model.openCell(cellAddress);
        }
    }

    public void rightClick(int cellAddress) {
        switch (model.getGameGrid()[cellAddress].getState()){
            case 0:
                model.flagCell(cellAddress);
                break;
            case 2:
                model.questionCell(cellAddress);
            case 3:
                model.closeCell(cellAddress);
        }

    }
//TODO метод должен открывать все ячейки вокруг, если количество флажков соответсвует кол-ву бомб
    public void bothClick(int cellAddress) {
        model.openCell(cellAddress);
    }

    private ArrayList<Integer> getEmptyClosedCellsAround(int CellAddress) {
        ArrayList<Integer> emptyClosedCellsAround = new ArrayList<>();
        for (int i : model.getGameGrid()[CellAddress].getCellsAround()) {
            if (model.getGameGrid()[i].getInnerValue() == 0 && model.getGameGrid()[i].getState() == 0) {
                emptyClosedCellsAround.add(i);
            }
        }
        return emptyClosedCellsAround;
    }
}

/** открывает одну клетку*/
// if (model.getGameGrid()[cellAddress].getInnerValue() == 0 && model.getGameGrid()[cellAddress].getState() == 0) {
//         ArrayList<Integer> cellsToOpen = new ArrayList<>();
//        cellsToOpen.add(cellAddress);
//        while (cellsToOpen.size() > 0) {
//        ArrayList<Integer> emptyClosedCellsAround = getEmptyClosedCellsAround(cellAddress);
//        for (int i: emptyClosedCellsAround) {
//        cellsToOpen.add(i);
//        }
//        model.openCell(cellAddress);
//        cellsToOpen.remove(0);
//        }

/**открывает только пустую область*/
//        if (model.getGameGrid()[cellAddress].getInnerValue() == 0 && model.getGameGrid()[cellAddress].getState() == 0) {
//            ArrayList<Integer> cellsToOpen = new ArrayList<>();
//            cellsToOpen.add(cellAddress);
//            while (cellsToOpen.size() > 0) {
//                ArrayList<Integer> emptyClosedCellsAround = getEmptyClosedCellsAround(cellsToOpen.get(0));
//                for (int i: emptyClosedCellsAround) {
//                    cellsToOpen.add(i);
//                }
//                model.openCell(cellsToOpen.get(0));
//                cellsToOpen.remove(0);
//            }
//        }
//    }