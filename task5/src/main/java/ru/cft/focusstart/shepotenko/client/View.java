package ru.cft.focusstart.shepotenko.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements Iview {
    private JTextArea chatField;
    private JTextArea userList;
    private Presenter presenter;

    public View() {
        this.presenter = new Presenter(this);

        this.setResizable(false);
        this.setLocation(100, 100);
        this.setTitle("Chauchat");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

        JTextField newMessageField = new JTextField(80);

        JButton sendButton = new JButton("send");
        sendButton.setSize(new Dimension(20, 30));
        sendButton.addActionListener(e -> onSendMessage(newMessageField.getText()));

        top.add(chatField);
        top.add(userList);
        bottom.add(newMessageField);
        bottom.add(sendButton);

        pack();

        createSetNickNameDialog();
    }

    private JDialog createSetNickNameDialog() {
        JDialog nickNameDialog = new JDialog(this, "new user", true);
        nickNameDialog.setSize(new Dimension(300, 100));
        nickNameDialog.setResizable(false);
        nickNameDialog.setLocationRelativeTo(this);
        nickNameDialog.setLayout(new FlowLayout());

        JTextField enterNickField = new JTextField("enter your nickname", 20);
        nickNameDialog.add(enterNickField);

        JButton startButton = new JButton("start chatting");
        startButton.addActionListener(e -> {
            onJoin(enterNickField.getText());
            nickNameDialog.dispose();
        });
        nickNameDialog.add(startButton);

        nickNameDialog.setVisible(true);

        return nickNameDialog;
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

        JButton noButton = new JButton("yes");
        noButton.setSize(new Dimension(20, 30));
        noButton.addActionListener(e -> exitDialog.dispose());

    }

    private void onJoin(String nickName) {
        boolean isJoined = false;
        while (!isJoined)
            isJoined = presenter.joinWithNewNickName(nickName);
        if (!isJoined) {
            presenter.getModel().addMessage("this nickname is unavailable. set another nickname.");
            createSetNickNameDialog();
        }
    }

    private void onSendMessage(String text) {
        presenter.sendMessage(text);
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
