package ru.cft.focusstart.shepotenko;


import java.util.*;

public class Model {
    private Cell[] gameGrid;


    public Model(int fieldSizeX, int fieldSizeY, int amountOfBombs) {
        this.gameGrid = generateGameGrid(fieldSizeX, fieldSizeY, amountOfBombs);
    }



    public Cell[] getGameGrid() {
        return gameGrid;
    }


    //TODO проверка на отутствие бомбы в первой нажатой клетке.
    private Cell[] generateGameGrid(int fieldSizeX, int fieldSizeY, int amountOfBombs) {
        LinkedHashSet<Integer> bombPlaces = new LinkedHashSet<>();
        Random r = new Random();
        while (bombPlaces.size() != amountOfBombs) {
            bombPlaces.add(r.nextInt(fieldSizeX * fieldSizeY));
        }
        Cell[] gameGrid = new Cell[fieldSizeX * fieldSizeY];
        for (int i = 0; i < gameGrid.length; i++) {
            gameGrid[i] = new Cell();
        }
        for (int i = 0; i < fieldSizeX * fieldSizeY; i++) {
            ArrayList<Integer> cellsAround = new ArrayList<>();
            cellsAround.add(i - (fieldSizeY + 1));
            cellsAround.add(i - fieldSizeY);
            cellsAround.add(i - (fieldSizeY - 1));
            cellsAround.add(i + 1);
            cellsAround.add(i + (fieldSizeY + 1));
            cellsAround.add(i + fieldSizeY);
            cellsAround.add(i + (fieldSizeY - 1));
            cellsAround.add(i - 1);
            if ((i) - fieldSizeY < 0) {                                     //убираем сверху
                cellsAround.remove(Integer.valueOf(i - (fieldSizeY + 1)));
                cellsAround.remove(Integer.valueOf(i - fieldSizeY));
                cellsAround.remove(Integer.valueOf(i - (fieldSizeY - 1)));
            }
            if ((i + 1) % fieldSizeY == 0) {                                //убираем справа
                cellsAround.remove(Integer.valueOf(i - (fieldSizeY - 1)));
                cellsAround.remove(Integer.valueOf(i + 1));
                cellsAround.remove(Integer.valueOf(i + (fieldSizeY + 1)));
            }
            if (i + fieldSizeY >= fieldSizeX * fieldSizeY) {                //убираем снизу
                cellsAround.remove(Integer.valueOf(i + (fieldSizeY + 1)));
                cellsAround.remove(Integer.valueOf(i + fieldSizeY));
                cellsAround.remove(Integer.valueOf(i + (fieldSizeY - 1)));
            }
            if (i % fieldSizeY == 0) {                                      //убираем слева
                cellsAround.remove(Integer.valueOf(i + (fieldSizeY - 1)));
                cellsAround.remove(Integer.valueOf(i - 1));
                cellsAround.remove(Integer.valueOf(i - (fieldSizeY + 1)));
            }

            if (bombPlaces.contains(i)) {
                gameGrid[i].setInnerValue(9);
            } else {
                int numberOfBombsAround = 0;
                for (int n : cellsAround) {
                    if (bombPlaces.contains(n)) {
                        numberOfBombsAround++;
                    }
                }
                gameGrid[i].setInnerValue(numberOfBombsAround);
            }
        }
        return gameGrid;
    }

    private void openCell(int cellsNumber) {

    }
}



