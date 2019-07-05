package ru.cft.focusstart.shepotenko.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("[hh:mm:ss] ");
    private MessageType type;
    private Date date;
    private String name;
    private String text;

    public Message(MessageType type) {
        this.type = type;
        this.date = new Date();
    }

    public MessageType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        if (this.type == MessageType.CLIENT_EXIT ||
                this.type == MessageType.CLIENT_SET_NAME ||
                this.type == MessageType.SERVER_MESSAGE) {

            return dateFormat.format(this.date) + this.text;
        }
        if (this.type == MessageType.CLIENT_TEXT_MESSAGE) {
            return dateFormat.format(this.date) + this.name + ": " + this.text;
        } else return super.toString();
    }
}

