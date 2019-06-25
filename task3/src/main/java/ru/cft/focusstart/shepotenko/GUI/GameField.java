package ru.cft.focusstart.shepotenko.GUI;

import ru.cft.focusstart.shepotenko.Model.Cell;
import ru.cft.focusstart.shepotenko.Model.Coordinate;
import ru.cft.focusstart.shepotenko.Model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


class GameField extends JPanel {
    private Game game;

    private JButton[][] buttons;

    GameField(Game game) {
        this.game = game;
        buttons = generateButtons(game.getSizeX(), game.getSizeY());
        setLayout(new GridLayout(game.getSizeX(), game.getSizeY()));
        setPreferredSize(new Dimension(game.getSizeY() * 40, game.getSizeX() * 40));
        for (int x = 0; x < game.getSizeX(); x++) {
            for (int y = 0; y < game.getSizeY(); y++) {
                add(buttons[x][y]);
            }
        }
    }

    private JButton[][] generateButtons(int sizeX, int sizeY) {
        JButton[][] buttons = new JButton[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                JButton button = new JButton();
                button.setIcon(Icons.getClosedIcon());
                Coordinate coord = new Coordinate(x, y);
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            game.openCell(coord);
                        }
                        if (SwingUtilities.isRightMouseButton(e)) {
                            game.markCell(coord);
                        }
                        if (SwingUtilities.isMiddleMouseButton(e)) {
                            game.openCellsAround(coord);
                        }
                    }
                });
                buttons[x][y] = button;
            }
        }
        return buttons;
    }

    void update() {
        for (int x = 0; x < game.getSizeX(); x++) {
            for (int y = 0; y < game.getSizeY(); y++) {
                Cell cell = game.getCell(new Coordinate(x, y));
                if (cell.isOpened()) {
                    buttons[x][y].setIcon(Icons.getNumberedIcon(cell.getValue().getNum()));
                }
                if (cell.isClosed()) {
                    buttons[x][y].setIcon(Icons.getClosedIcon());
                }
                if (cell.isFlaged()) {
                    buttons[x][y].setIcon(Icons.getFlagIcon());
                }
                if (cell.isQuestioned()) {
                    buttons[x][y].setIcon(Icons.getQuistionIcon());
                }
                if (cell.isExploded()) {
                    buttons[x][y].setIcon(Icons.getBombedIcon());
                }
                if (cell.isMistake()) {
                    buttons[x][y].setIcon(Icons.getMistakeIcon());
                }
            }
        }
    }
}



