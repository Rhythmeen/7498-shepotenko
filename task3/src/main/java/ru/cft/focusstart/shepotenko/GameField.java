package ru.cft.focusstart.shepotenko;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameField extends JPanel {
    private int width;
    private int length;
    private JButton[] buttons;
    private Game game;


    public GameField(Game game) {
        this.width = game.getWidth();
        this.length = game.getLength();
        this.game = game;
        buttons = generateButtons(length * width);
        setPreferredSize(new Dimension(length * 40, width * 40));
        setLayout(new GridLayout(length, width));
        updateGameField();
    }

    private JButton[] generateButtons(int amountOfButtons) {
        JButton[] buttons = new JButton[amountOfButtons];
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton();
            buttons[i] = button;
            //TODO выставить фиксированный размер ячейки
            button.setIcon(MinesweeperWindow.icons.get(12));
  //          button.setSize(20,20);
            int buttonAddress = i;
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        game.leftClick(buttonAddress);
                    }
                    if (SwingUtilities.isRightMouseButton(e) ) {
                        game.rightClick(buttonAddress);
                    }
                    if (SwingUtilities.isMiddleMouseButton(e)) {
                        game.middleClick(buttonAddress);
                    }
                    updateGameField();
                }
            });
        }
        return buttons;
    }

    public void updateGameField() {
        for (int i = 0; i < length * width; i++) {
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
            this.add(buttons[i]);
        }
    }
}
