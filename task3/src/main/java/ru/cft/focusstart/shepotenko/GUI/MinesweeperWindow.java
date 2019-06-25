package ru.cft.focusstart.shepotenko.GUI;

import ru.cft.focusstart.shepotenko.Model.Difficulty;
import ru.cft.focusstart.shepotenko.Model.Game;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class MinesweeperWindow extends JFrame implements GameStateObserver {
    private Game game;
    private GameField gameField;
    private JLabel bombsUnmarkedJLabel;
    private JLabel timerJLabel;
    private JButton restartButton;

    public MinesweeperWindow() {
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setLocation(100, 100);
        this.setIconImage(Icons.getGameIcon().getImage());
        this.setTitle("Caпер");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        //генерация главного меню
        {
            JMenuBar menuBar = new JMenuBar();
            this.setJMenuBar(menuBar);
            JMenu menu = new JMenu("Меню");
            menuBar.add(menu);

            JMenuItem newGameItem = new JMenuItem("Новая игра");
            newGameItem.addActionListener(e -> newGame(game.getDifficulty()));
            JMenuItem difficultyItem = new JMenuItem("Уровень сложности");
            JMenu leaderBoard = new JMenu("Рекорды");
            JMenuItem recordEasy = new JMenuItem("новичек");
            JMenuItem recordNormal = new JMenuItem("любитель");
            JMenuItem recordHard = new JMenuItem("профессионал");
            leaderBoard.add(recordEasy);
            leaderBoard.add(recordNormal);
            leaderBoard.add(recordHard);
            difficultyItem.addActionListener(e -> generateDifficultyJDialog());
            recordEasy.addActionListener(e -> generateLeaderBoardJDialog());
            recordNormal.addActionListener(e -> generateLeaderBoardJDialog());
            recordHard.addActionListener(e -> generateLeaderBoardJDialog());

            menu.add(newGameItem);
            menu.add(difficultyItem);
            menu.add(leaderBoard);
        }
        //генерация панели информации
        {
            this.bombsUnmarkedJLabel = new JLabel();
            this.bombsUnmarkedJLabel.setPreferredSize(new Dimension(100, 45));
            this.bombsUnmarkedJLabel.setFont(new Font("Verdana", Font.BOLD, 20));
            this.restartButton = new JButton("Заново");
            this.restartButton.setSize(60, 30);
            this.timerJLabel = new JLabel("0");
            this.timerJLabel.setFont(new Font("Verdana", Font.BOLD, 20));
            this.timerJLabel.setPreferredSize(new Dimension(100, 45));
            this.timerJLabel.setHorizontalAlignment(JLabel.RIGHT);

            JPanel topPanel = new JPanel();
            topPanel.setLayout(new GridLayout(1, 3));
            topPanel.setSize(new Dimension(360, 45));

            topPanel.add(this.bombsUnmarkedJLabel, BorderLayout.WEST);
            topPanel.add(this.restartButton);
            topPanel.add(this.timerJLabel, BorderLayout.EAST);

            this.add(topPanel, BorderLayout.NORTH);
        }
        newGame(Difficulty.EASY);
    }

    private void newGame(Difficulty difficulty) {
        if (this.gameField != null) {
            remove(this.gameField);
        }
        this.game = new Game(difficulty, this);
        this.gameField = new GameField(game);
        this.add(this.gameField, BorderLayout.SOUTH);
        this.restartButton.addActionListener(e -> newGame(this.game.getDifficulty()));
        this.onFieldChanged();

        this.timerJLabel.setText("0");
        this.pack();
    }

    private void generateDifficultyJDialog() {
        JDialog dialog = new JDialog(this, "Уровень сложности", true);
        dialog.setSize(new Dimension(200, 200));
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(HIDE_ON_CLOSE);
        dialog.setLocationRelativeTo(gameField);
        dialog.setLayout(new BorderLayout());

        JRadioButton easy = new JRadioButton("новичек (9x9)");
        JRadioButton normal = new JRadioButton("любитель 16х16");
        JRadioButton hard = new JRadioButton("профессионал 16х30");
        JButton button = new JButton("Новая игра");
        ButtonGroup group = new ButtonGroup();
        group.add(easy);
        group.add(normal);
        group.add(hard);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        dialog.add(panel, BorderLayout.WEST);
        panel.setPreferredSize(new Dimension(200, 200));
        panel.add(easy);
        panel.add(normal);
        panel.add(hard);
        panel.add(button);

        easy.addActionListener(a -> button.addActionListener(e -> {
            newGame(Difficulty.EASY);
            dialog.dispose();
        }));
        normal.addActionListener(b -> button.addActionListener(e -> {
            newGame(Difficulty.NORMAL);
            dialog.dispose();
        }));
        hard.addActionListener(c -> button.addActionListener(e -> {
            newGame(Difficulty.HARD);
            dialog.dispose();
        }));
        dialog.setVisible(true);
    }

    private void generateLeaderBoardJDialog() {
        JDialog recordTable = new JDialog(this, "Рекорды", true);
        recordTable.setSize(new Dimension(300, 200));
        recordTable.setResizable(false);
        recordTable.setDefaultCloseOperation(HIDE_ON_CLOSE);
        recordTable.setLocationRelativeTo(gameField);
        recordTable.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        recordTable.add(panel, BorderLayout.WEST);
        panel.setLayout(new GridLayout(10, 1));

        try (Scanner scanner = new Scanner(new FileReader(game.getRecordManager().getLeaderBoardPath()))) {
            while (scanner.hasNext()) {
                JLabel recordLine = new JLabel(scanner.nextLine());
                panel.add(recordLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        recordTable.setVisible(true);
    }

    private void generateLooseJDialog() {
        JDialog looseDialog = new JDialog(this, "Вы проиграли:(", true);
        looseDialog.setSize(new Dimension(100, 100));
        looseDialog.setResizable(false);
        looseDialog.setDefaultCloseOperation(HIDE_ON_CLOSE);
        looseDialog.setLocationRelativeTo(gameField);
        looseDialog.setLayout(new BorderLayout());

        JButton button = new JButton("Начать заново");
        looseDialog.add(button);
        button.addActionListener(e -> {
            newGame(game.getDifficulty());
            looseDialog.dispose();
        });
        looseDialog.setVisible(true);
    }

    private void generateWinJDialog() {
        JDialog winDialog = new JDialog(this, "Победа!", true);
        winDialog.setSize(new Dimension(100, 100));
        winDialog.setResizable(false);
        winDialog.setDefaultCloseOperation(HIDE_ON_CLOSE);
        winDialog.setLocationRelativeTo(gameField);
        winDialog.setLayout(new BorderLayout());

        JButton button = new JButton("Начать заново");
        winDialog.add(button);
        button.addActionListener(e -> {
            newGame(game.getDifficulty());
            winDialog.dispose();
        });

        winDialog.setVisible(true);
    }



    private void generateNewRecordJDialog() {
        JDialog writeToLeaderBoard = new JDialog(this, "Поздравляем! вы поставили новый рекорд!", true);
        writeToLeaderBoard.setSize(new Dimension(400, 200));
        writeToLeaderBoard.setResizable(false);
        writeToLeaderBoard.setDefaultCloseOperation(HIDE_ON_CLOSE);
        writeToLeaderBoard.setLocationRelativeTo(gameField);

        JPanel panel = new JPanel();
        writeToLeaderBoard.add(panel);

        JLabel yourTime = new JLabel("Ваше время: " + game.getTime());
        JTextField newRecordTextField = new JTextField("Ваше имя", 30);
        JButton okButton = new JButton("ok");
        panel.add(yourTime);
        panel.add(newRecordTextField);
        panel.add(okButton);

        okButton.addActionListener(e -> {
            writeToLeaderBoard.dispose();
            game.getRecordManager().updateLeaderBoard(game.getTime(), newRecordTextField.getText());
            generateLeaderBoardJDialog();
            newGame(game.getDifficulty());
        });

        writeToLeaderBoard.setVisible(true);
    }



    //имплементация интерфейса GameStateObserver

    @Override
    public void onWin() {
        if (this.game.getRecordManager().isNewRecord(game.getTime())) {
            generateNewRecordJDialog();
        } else {
            generateWinJDialog();
        }
    }

    @Override
    public void onLoose() {
        gameField.update();
        generateLooseJDialog();
    }

    @Override
    public void onFieldChanged() {
        gameField.update();
        bombsUnmarkedJLabel.setText(String.valueOf(game.getUnmarkedBombsCounter()));
    }

    @Override
    public void onTimeChanged() {
        timerJLabel.setText(String.valueOf(game.getTime()));
    }
}
