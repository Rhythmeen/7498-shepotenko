package ru.cft.focusstart.shepotenko;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GameField extends JPanel {
    private Game game;
    private JButton[] buttons;
    private Difficulty dif;


    public GameField(Difficulty dif, GameStateObserver observer) {
        this.game = new Game(dif.getWidth(),dif.getLength(),dif.getAmountOfBombs(),observer);
        buttons = generateButtons(game.getSizeY() * game.getSizeX());
        this.dif = dif;
        setLayout(new GridLayout(game.getSizeY(), game.getSizeX()));
        setPreferredSize(new Dimension(game.getSizeY() * 40, game.getSizeX() * 40));
        updateGameField();

    }

    private JButton[] generateButtons(int amountOfButtons) {
        JButton[] buttons = new JButton[amountOfButtons];
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton();
            buttons[i] = button;
            //TODO выставить фиксированный размер ячейки
   //         button.setSize(new Dimension(40, 40));
            button.setIcon(MinesweeperWindow.icons.get(12));
            int[] buttonCoord = game.convertAddress2XY(i);

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        game.leftClick(buttonCoord[0],buttonCoord[1]);
                    }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        game.rightClick(buttonCoord[0],buttonCoord[1]);
                    }
                    if (SwingUtilities.isMiddleMouseButton(e)) {
                        game.middleClick(buttonCoord[0],buttonCoord[1]);
                    }
                    updateGameField();
                }
            });

        }
        return buttons;
    }

    public void updateGameField() {
        for (int i = 0; i < game.getSizeY() * game.getSizeX(); i++) {
            switch (game.getCellsState(i)) {
                case 0:
                    buttons[i].setIcon(MinesweeperWindow.icons.get(12));
                    break;
                case 1:
                    buttons[i].setIcon(MinesweeperWindow.icons.get(game.getCellsInnerValue(i)));
                    break;
                case 2:
                    buttons[i].setIcon(MinesweeperWindow.icons.get(13));
                    break;
                case 3:
                    buttons[i].setIcon(MinesweeperWindow.icons.get(14));
                    break;
            }
            add(buttons[i]);

        }
    }

    public Difficulty getDifficculty() {
        return dif;
    }


    public void blockButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);

        }
    }
}
//    public void newGame(Game game) {
//        this.game = game;
//        buttons = generateButtons(game.getSizeY() * game.getSizeX());
//        setLayout(new GridLayout(game.getSizeY(), game.getSizeX()));
//        updateGameField();
//
//    }

//    public void updateGameField() {
//        for (int i = 0; i < game.getSizeY() * game.getSizeX(); i++) {
//            switch (game.getCellsState(i)) {
//                case 0:
//                    buttons[i].setIcon(MinesweeperWindow.icons.get(12));
//                    break;
//                case 1:
//                    buttons[i].setIcon(MinesweeperWindow.icons.get(game.getCellsInnerValue(i)));
//                    break;
//                case 2:
//                    buttons[i].setIcon(MinesweeperWindow.icons.get(13));
//                    break;
//                case 3:
//                    buttons[i].setIcon(MinesweeperWindow.icons.get(14));
//                    break;
//            }
//            add(buttons[i]);
//
//        }
//    }

