package ru.cft.focusstart.shepotenko;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MinesweeperWindow extends JFrame {
    public static HashMap<Integer, ImageIcon> icons = fillIconsMap();

    private static HashMap<Integer, ImageIcon> fillIconsMap() {
        HashMap<Integer, ImageIcon> icons = new HashMap<>();
        icons.put(0, new ImageIcon(MainClass.class.getResource("/icons/zero.png")));
        icons.put(1, new ImageIcon(MainClass.class.getResource("/icons/one.png")));
        icons.put(2, new ImageIcon(MainClass.class.getResource("/icons/two.png")));
        icons.put(3, new ImageIcon(MainClass.class.getResource("/icons/three.png")));
        icons.put(4, new ImageIcon(MainClass.class.getResource("/icons/four.png")));
        icons.put(5, new ImageIcon(MainClass.class.getResource("/icons/five.png")));
        icons.put(6, new ImageIcon(MainClass.class.getResource("/icons/six.png")));
        icons.put(7, new ImageIcon(MainClass.class.getResource("/icons/seven.png")));
        icons.put(8, new ImageIcon(MainClass.class.getResource("/icons/eight.png")));
        icons.put(9, new ImageIcon(MainClass.class.getResource("/icons/mine.png")));
        icons.put(10, new ImageIcon(MainClass.class.getResource("/icons/mined.png")));
        icons.put(11, new ImageIcon(MainClass.class.getResource("/icons/no_mine.png")));
        icons.put(12, new ImageIcon(MainClass.class.getResource("/icons/closed.png")));
        icons.put(13, new ImageIcon(MainClass.class.getResource("/icons/flag.png")));
        icons.put(14, new ImageIcon(MainClass.class.getResource("/icons/question.png")));
        icons.put(15, new ImageIcon(MainClass.class.getResource("/icons/icon.png")));
        return icons;
    }

    MinesweeperWindow() {
        setVisible(true);
        setAlwaysOnTop(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(icons.get(15).getImage());
        setTitle("Caпер");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("Меню");
        menuBar.add(menu);

        JMenuItem newGame = new JMenuItem("Новая игра");
        JMenuItem difficulty = new JMenuItem("Уровень сложности");
        JMenuItem recordBoard = new JMenuItem("Рекорды");
        menu.add(newGame);
        menu.add(difficulty);
        menu.add(recordBoard);



        GameField gameField = new GameField(new Game(16,16,20));


        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50,50));
        panel.add(button);

        add(panel,BorderLayout.NORTH);
        add(gameField, BorderLayout.SOUTH);
        pack();
    }


}

