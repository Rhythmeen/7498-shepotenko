package ru.cft.focusstart.shepotenko;

import java.util.ArrayList;
import java.util.HashSet;

public class Controller {
    private Model model;


    public Controller(Model model) {
        this.model = model;
    }

    public void leftClick(int cellAddress) {
        if (model.getCellsState(cellAddress) != 0) {
            return;
        } else {
            if (model.getIsGameGridEmpty()) {
                model.gameGridInit(cellAddress);
            }
            //ультракудрявый алгоритм, открывает область пустых клеток. надо попробовать порефакторить.
            if (model.getCellInnerValue(cellAddress) == 0 && model.getCellsState(cellAddress) == 0) {
                HashSet<Integer> emptyArea = new HashSet<>();
                ArrayList<Integer> findingEmptyArea = new ArrayList<>();
                findingEmptyArea.add(cellAddress);
                while (findingEmptyArea.size() > 0) {
                    ArrayList<Integer> emptyClosedCellsAround = getEmptyClosedCellsAround(findingEmptyArea.get(0));
                    for (int i : emptyClosedCellsAround) {
                        findingEmptyArea.add(i);
                    }
                    emptyArea.add(findingEmptyArea.get(0));
                    model.setCellsState(findingEmptyArea.get(0), 1);
                    findingEmptyArea.remove(0);
                }
                HashSet<Integer> cellsToOpen = new HashSet<>();
                for (int i : emptyArea) {
                    for (int j : model.getGameGrid()[i].getCellsAround())
                        cellsToOpen.add(j);
                }
                for (int i : cellsToOpen) {
                    model.setCellsState(i, 1);
                }

            }
            if (model.getCellInnerValue(cellAddress) != 0) {
                model.setCellsState(cellAddress, 1);
            }
        }
    }

    public void rightClick(int cellAddress) {
        switch (model.getCellsState(cellAddress)) {
            case 0:
                model.setCellsState(cellAddress, 2);
                break;
            case 2:
                model.setCellsState(cellAddress, 3);
                break;
            case 3:
                model.setCellsState(cellAddress, 0);
                break;
        }

    }



    //TODO метод должен открывать все ячейки вокруг, если количество флажков соответсвует кол-ву бомб
    public void bothClick(int cellAddress) {

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

/**
 * открывает одну клеткуоткрывает только пустую область
 */
// if (model.getGameGrid()[cellAddress].getCellInnerValue() == 0 && model.getGameGrid()[cellAddress].getCellsState() == 0) {
//         ArrayList<Integer> cellsToOpen = new ArrayList<>();
//        cellsToOpen.add(cellAddress);
//        while (cellsToOpen.size() > 0) {
//        ArrayList<Integer> emptyClosedCellsAround = getEmptyClosedCellsAround(cellAddress);
//        for (int i: emptyClosedCellsAround) {
//        cellsToOpen.add(i);
//        }
//        model.setCellsState(cellAddress);
//        cellsToOpen.remove(0);
//        }

/**открывает только пустую область*/
//        if (model.getGameGrid()[cellAddress].getCellInnerValue() == 0 && model.getGameGrid()[cellAddress].getCellsState() == 0) {
//            ArrayList<Integer> cellsToOpen = new ArrayList<>();
//            cellsToOpen.add(cellAddress);
//            while (cellsToOpen.size() > 0) {
//                ArrayList<Integer> emptyClosedCellsAround = getEmptyClosedCellsAround(cellsToOpen.get(0));
//                for (int i: emptyClosedCellsAround) {
//                    cellsToOpen.add(i);
//                }
//                model.setCellsState(cellsToOpen.get(0));
//                cellsToOpen.remove(0);
//            }
//        }
//    }