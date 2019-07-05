package ru.cft.focusstart.shepotenko.client;

import java.util.ArrayList;
import java.util.Arrays;

public class Model {
    private String nickName;
    private ArrayList<String> messages;
    private ArrayList<String> userList;

    public Model() {
        this.messages = new ArrayList<>();
        this.userList = new ArrayList<>();
    }

    void setNickName(String nickName) {
        this.nickName = nickName;
    }

    String getNickName() {
        return nickName;
    }

    ArrayList<String> getMessages() {
        return this.messages;
    }

    void addMessage(String text) {
        this.messages.add(text);
    }

    ArrayList<String> getUsers() {
        return this.userList;
    }

    void updateUserList(String nickNames) {
        this.userList = new ArrayList<>(Arrays.asList(nickNames.split("\n")));
    }


}
