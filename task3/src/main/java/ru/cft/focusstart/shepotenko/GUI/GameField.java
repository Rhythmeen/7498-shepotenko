package ru.cft.focusstart.shepotenko.GUI;

import ru.cft.focusstart.shepotenko.Model.Cell;
import ru.cft.focusstart.shepotenko.Model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GameField extends JPanel {
    private Game game;

    private JButton[] buttons;

    public GameField(Game game) {
        this.game = game;
        buttons = generateButtons(game.getSizeY() * game.getSizeX());
        setLayout(new GridLayout(game.getSizeX(), game.getSizeY()));
        setPreferredSize(new Dimension(game.getSizeY() * 40, game.getSizeX() * 40));
        update();
    }

    private JButton[] generateButtons(int amountOfButtons) {
        JButton[] buttons = new JButton[amountOfButtons];
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton();
            button.setIcon(MinesweeperWindow.icons.get(12));
            int[] buttonCoord = game.convertAddress2XY(i);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        game.pressOne(buttonCoord[0], buttonCoord[1]);
                    }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        game.pressThree(buttonCoord[0], buttonCoord[1]);
                    }
                    if (SwingUtilities.isMiddleMouseButton(e)) {
                        game.pressTwo(buttonCoord[0], buttonCoord[1]);
                    }
                }
            });
            buttons[i] = button;
        }
        return buttons;
    }

    public void update() {
        for (int i = 0; i < game.getSizeY() * game.getSizeX(); i++) {
            int xCoord = game.convertAddress2XY(i)[0];
            int yCoord = game.convertAddress2XY(i)[1];
            Cell cell = game.getCell(xCoord, yCoord);
            if (cell.isOpened()) {
                buttons[i].setIcon(MinesweeperWindow.icons.get(cell.getValue().getNum()));
            }
            if (cell.isClosed()) {
                buttons[i].setIcon(MinesweeperWindow.icons.get(12));
            }
            if (cell.isFlaged()) {
                buttons[i].setIcon(MinesweeperWindow.icons.get(13));
            }
            if (cell.isQuestioned()) {
                buttons[i].setIcon(MinesweeperWindow.icons.get(14));
            }
            if (cell.isExploded()) {
                buttons[i].setIcon(MinesweeperWindow.icons.get(10));
            }
            if (cell.isMistake()) {
                buttons[i].setIcon(MinesweeperWindow.icons.get(11));
            }
            add(buttons[i]);
        }
    }
}



