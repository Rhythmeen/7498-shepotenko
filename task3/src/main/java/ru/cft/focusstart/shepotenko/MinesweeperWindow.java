package ru.cft.focusstart.shepotenko;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MinesweeperWindow extends JFrame implements GameStateObserver {
    public static HashMap<Integer, ImageIcon> icons = fillIconsMap();
    private GameField gameField;

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

        JMenuItem newGameItem = new JMenuItem("Новая игра");
        JMenuItem difficultyItem = new JMenuItem("Уровень сложности");
        JMenu recordBoard = new JMenu("Рекорды");
        JMenuItem recordEasy = new JMenuItem("новичек");
        JMenuItem recordNormal = new JMenuItem("любитель");
        JMenuItem recordHard = new JMenuItem("профессионал");
        recordBoard.add(recordEasy);
        recordBoard.add(recordNormal);
        recordBoard.add(recordHard);
        newGameItem.addActionListener(e -> newGame(gameField.getDifficculty()));
        difficultyItem.addActionListener(e -> {
            JDialog difficultyDialog = generateDifficultyDialogFrame();
            difficultyDialog.setVisible(true);
        });
        recordEasy.addActionListener(e -> {
            JDialog recordDialog = generateRecordBoard("EASY");
            recordDialog.setVisible(true);
        });
        recordNormal.addActionListener(e -> {
            JDialog recordDialog = generateRecordBoard("NORMAL");
            recordDialog.setVisible(true);
        });
        recordHard.addActionListener(e -> {
            JDialog recordDialog = generateRecordBoard("HARD");
            recordDialog.setVisible(true);
        });


        menu.add(newGameItem);
        menu.add(difficultyItem);
        menu.add(recordBoard);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton smileButton = new JButton();
        smileButton.setPreferredSize(new Dimension(50, 50));
        panel.add(smileButton);
        smileButton.addActionListener(e -> newGame(gameField.getDifficculty()));

        add(panel, BorderLayout.NORTH);

        newGame(Difficulty.EASY);
        pack();


    }


    private void newGame(Difficulty dif) {
        if (gameField != null) {
            remove(gameField);
        }
        gameField = new GameField(dif, this);
        add(gameField, BorderLayout.SOUTH);
        revalidate();
        pack();
    }

    private JDialog generateDifficultyDialogFrame() {
        JDialog dialog = new JDialog(this, "Уровень сложности", true);
        dialog.setSize(new Dimension(200, 200));
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(HIDE_ON_CLOSE);
        dialog.setLocationRelativeTo(gameField);

        JRadioButton easy = new JRadioButton("новичек (9x9)");
        JRadioButton normal = new JRadioButton("любитель 16х16");
        JRadioButton hard = new JRadioButton("профессионал 16х30");
        JButton button = new JButton("Новая игра");
        ButtonGroup group = new ButtonGroup();
        group.add(easy);
        group.add(normal);
        group.add(hard);

        dialog.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setPreferredSize(new Dimension(200, 200));
        panel.add(easy);
        panel.add(normal);
        panel.add(hard);
        panel.add(button);
        dialog.add(panel, BorderLayout.WEST);

        easy.addActionListener(a -> {
            button.addActionListener(e -> {
                newGame(Difficulty.EASY);
                dialog.dispose();
            });
        });
        normal.addActionListener(b -> {
            button.addActionListener(e -> {
                newGame(Difficulty.NORMAL);
                dialog.dispose();
            });
        });
        hard.addActionListener(c -> {
            button.addActionListener(e -> {
                newGame(Difficulty.HARD);
                dialog.dispose();
            });
        });
        return dialog;
    }

    private JDialog generateRecordBoard(String difficulty) {
        JDialog recordTable = new JDialog(this, "Рекорды", true);
        recordTable.setSize(new Dimension(300, 200));
        recordTable.setResizable(false);
        recordTable.setDefaultCloseOperation(HIDE_ON_CLOSE);
        recordTable.setLocationRelativeTo(gameField);
        recordTable.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1));
        recordTable.add(panel, BorderLayout.WEST);
        try (Scanner scanner = new Scanner(new FileReader(this.getClass().getResource("/records/" + difficulty + ".txt").getFile()))) {
            while (scanner.hasNext()) {
                String record = scanner.nextLine();
                JLabel label = new JLabel(record);
                panel.add(label);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return recordTable;
    }

    private JDialog generateWriteRecordDialog(String difficulty, int newRecordTime) {
        JDialog writeToRecordBoard = new JDialog(this, true);
        writeToRecordBoard.setSize(new Dimension(300, 200));
        writeToRecordBoard.setResizable(false);
        writeToRecordBoard.setDefaultCloseOperation(HIDE_ON_CLOSE);
        writeToRecordBoard.setLocationRelativeTo(gameField);
        writeToRecordBoard.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        writeToRecordBoard.add(panel, BorderLayout.WEST);
        JLabel congrats = new JLabel("Поздравляем! вы поставили новый рекорд!");
        JLabel yorTime = new JLabel("Ваше время: " + newRecordTime);
        JTextField newRecordTextField = new JTextField("введите имя", 30);
        JButton okButton = new JButton("ok");
        writeToRecordBoard.add(okButton, BorderLayout.SOUTH);
        okButton.addActionListener(e -> {
            updateRecordBoard(newRecordTime, newRecordTextField.getText(), difficulty);
            generateRecordBoard(difficulty);
        });

        return writeToRecordBoard;
    }

    private void updateRecordBoard(int newRecordTime, String name, String difficulty) {
        ArrayList<String> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileReader(this.getClass().getResource("/records/" + difficulty + ".txt").getFile()))) {
            while (scanner.hasNext()) {
                String recordLine = scanner.nextLine();
                int recordTime = Integer.parseInt(recordLine.replaceAll("\\D+", ""));
                if (newRecordTime >= recordTime) {
                    records.add(recordLine);
                } else {
                    records.add(newRecordTime + " " + name);
                    while (scanner.hasNext()) {
                        records.add(scanner.nextLine());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (records.size() > 10) {
            records.remove(10);
        }
        try (FileWriter writer = new FileWriter(this.getClass().getResource("/records/" + difficulty + ".txt").getFile())) {
            for (String recordLine : records) {
                writer.write(recordLine + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error printing to file");
            System.exit(1);
        }
    }


    @Override
    public void gameStarted() {
        System.out.println("gameStarted");
    }

    @Override
    public void win() {
        System.out.println("win");
    }

    @Override
    public void loose() {

        System.out.println("loose");
    }

    @Override
    public void bombMarked() {

    }

    @Override
    public void bombUnmarked() {

    }

}

