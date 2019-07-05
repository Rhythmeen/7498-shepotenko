package ru.cft.focusstart.shepotenko.client;

import javax.swing.*;
import java.awt.*;

public class ClientView extends JFrame implements Iview {
    private JTextArea chatField;
    private JTextArea userList;
    private JTextField newMessageField;
    private Presenter presenter;

    ClientView() {
        this.presenter = new Presenter(this);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLocation(100, 100);
        this.setTitle("Chauchat");

        this.setLayout(new BorderLayout());
        this.setVisible(true);

        JPanel top = new JPanel();
        top.setLayout(new FlowLayout());

        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());

        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);

        this.chatField = new JTextArea();
        this.chatField.setPreferredSize(new Dimension(800, 600));
        this.chatField.setOpaque(true);
        this.chatField.setBackground(Color.gray);
        this.chatField.setEditable(false);


        this.userList = new JTextArea();
        this.userList.setPreferredSize((new Dimension(200, 600)));
        this.userList.setOpaque(true);
        this.userList.setBackground(Color.gray);
        this.userList.setEditable(false);

        this.newMessageField = new JTextField(80);

        JButton sendButton = new JButton("send");
        sendButton.setSize(new Dimension(20, 30));
        sendButton.addActionListener(e -> onSendMessage(newMessageField.getText()));

        JButton exitButton = new JButton("exit");
        exitButton.addActionListener(e -> createExitDialog());
        bottom.add(exitButton);

        top.add(chatField);
        top.add(userList);
        bottom.add(newMessageField);
        bottom.add(sendButton);

        pack();
        createConnectDialog();

    }

    private void createConnectDialog() {
        JDialog connectDialog = new JDialog(this, "new connection", true);
        connectDialog.setSize(new Dimension(300, 200));
        connectDialog.setResizable(false);
        connectDialog.setLocationRelativeTo(this);
        connectDialog.setLayout(new GridLayout(3, 1));


        JPanel hostPanel = new JPanel();
        hostPanel.setLayout(new FlowLayout());

        JPanel portPanel = new JPanel();
        portPanel.setLayout(new FlowLayout());
        connectDialog.add(hostPanel);
        connectDialog.add(portPanel);

        JTextField hostField = new JTextField("localhost", 20);
        JTextField portField = new JTextField("1111", 20);

        JLabel enterHostLabel = new JLabel("enter host");
        JLabel enterPortLabel = new JLabel("enter host");

        hostPanel.add(enterHostLabel);
        hostPanel.add(hostField);
        portPanel.add(enterPortLabel);
        portPanel.add(portField);

        JButton okButton = new JButton("connect!");
        connectDialog.add(okButton);
        okButton.addActionListener(e -> {
            onTryToConnect(hostField.getText(), portField.getText());
            connectDialog.dispose();

        });
        connectDialog.setVisible(true);
    }

    private void createSetNickNameDialog() {
        JDialog nickNameDialog = new JDialog(this, "new user", true);
        nickNameDialog.setSize(new Dimension(300, 100));
        nickNameDialog.setResizable(false);
        nickNameDialog.setLocationRelativeTo(this);
        nickNameDialog.setLayout(new FlowLayout());
        nickNameDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JTextField enterNickField = new JTextField("enter your nickname", 20);
        nickNameDialog.add(enterNickField);

        JButton startButton = new JButton("start chatting");
        startButton.addActionListener(e -> {
            onJoin(enterNickField.getText());
            nickNameDialog.dispose();
        });
        nickNameDialog.add(startButton);

        nickNameDialog.setVisible(true);
        }

    private void createExitDialog() {
        JDialog exitDialog = new JDialog(this, "are u sure?", true);
        exitDialog.setSize(new Dimension(300, 100));
        exitDialog.setResizable(false);
        exitDialog.setLocationRelativeTo(this);
        exitDialog.setLayout(new FlowLayout());

        JButton yesButton = new JButton("yes");
        yesButton.setSize(new Dimension(20, 30));
        yesButton.addActionListener(e -> {
            onExit();
            System.exit(0);
        });

        JButton noButton = new JButton("no");
        noButton.setSize(new Dimension(20, 30));
        noButton.addActionListener(e -> exitDialog.dispose());

        exitDialog.add(yesButton);
        exitDialog.add(noButton);
        exitDialog.setVisible(true);

    }

    private void onTryToConnect(String host, String port) {
        boolean isConnected = presenter.connect(host, port);
        if (isConnected) {
            createSetNickNameDialog();
        } else {
            createConnectDialog();
        }

    }

    private void onJoin(String nickName) {
        presenter.joinWithNewNickName(nickName);
    }

    private void onSendMessage(String text) {
        presenter.sendMessage(text);
        newMessageField.setText("");
    }

    private void onExit() {
        presenter.disconnect();
    }

    @Override
    public void update() {
        StringBuilder messages = new StringBuilder();
        StringBuilder users = new StringBuilder();

        for (String msg : presenter.getModel().getMessages()) {
            messages.append(msg).append("\n");
        }
        this.chatField.setText(messages.toString());

        for (String user : presenter.getModel().getUsers()) {
            users.append(user).append("\n");
        }

        this.userList.setText(users.toString());
    }
}
