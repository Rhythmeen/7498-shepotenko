package ru.cft.focusstart.shepotenko;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameField extends JPanel {
    private Model model;
    private JButton[] buttons;
    private Controller controller;


    public GameField(Model model, Controller controller) {
        this.model = model;
        this.controller = new Controller(model);
        buttons = generateButtons(model.getGridLength() * model.getGridWidth());
        setLayout(new GridLayout(model.getGridLength(), model.getGridWidth()));
        updateGameField();
    }

    private JButton[] generateButtons(int amountOfButtons) {
        JButton[] buttons = new JButton[amountOfButtons];
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton();
            buttons[i] = button;
            //TODO выставить фиксированный размер ячейки
            button.setPreferredSize(new Dimension(10, 10));
            button.setIcon(MinesweeperWindow.icons.get(12));
            int buttonIndex = i;
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        controller.leftClick(buttonIndex);
                    }
                    if (SwingUtilities.isRightMouseButton(e) ) {
                        controller.rightClick(buttonIndex);
                    }
                    //TODO клик двумя кнопками
                    updateGameField();
                }
            });
        }
        return buttons;
    }

    private void updateGameField() {
        for (int i = 0; i < model.getGridLength() * model.getGridWidth(); i++) {
            switch (model.getGameGrid()[i].getState()) {
                case 0:
                    buttons[i].setIcon(MinesweeperWindow.icons.get(12));
                    break;
                case 1:
                    buttons[i].setIcon(MinesweeperWindow.icons.get(model.getGameGrid()[i].getInnerValue()));
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

//            b.addActionListener(e -> {
//                    controller.leftClick(buttonIndex);
//                    updateGameField();
//                    });
