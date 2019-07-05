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

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public ArrayList<String> getMessages() {
        return this.messages;
    }

    public void addMessage(String text) {
        this.messages.add(text);
    }

    public ArrayList<String> getUsers() {
        return this.userList;
    }

    public void updateUserList(String nickNames) {
        this.userList = new ArrayList<String>(Arrays.asList(nickNames.split("\n")));
    }


}
