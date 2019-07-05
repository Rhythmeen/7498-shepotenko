package ru.cft.focusstart.shepotenko.client;

import com.google.gson.Gson;
import ru.cft.focusstart.shepotenko.common.Message;
import ru.cft.focusstart.shepotenko.common.MessageType;
import ru.cft.focusstart.shepotenko.server.Server;

import java.io.*;

import java.net.ConnectException;
import java.net.Socket;
import java.util.Properties;


public class Presenter {
    private final Gson gson = new Gson();
    private Iview iview;
    private Model model;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    Presenter(Iview iview) {
        this.iview = iview;
        this.model = new Model();
    }

    boolean connect(String host, String port) {
        try {
            this.socket = new Socket(host, Integer.valueOf(port));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.socket != null) {
            model.addMessage("successfully conected to " + host + " " + port);
            messageListenerThread.start();
            return true;
        } else {
            model.addMessage("Connection failed");
            iview.update();
            return false;
        }
    }

    void disconnect() {
        if(this.socket != null) {
            Message message = new Message(MessageType.CLIENT_EXIT);
            writer.println(gson.toJson(message));
            writer.flush();
        messageListenerThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        } else {
            System.exit(0);
        }
    }

    private Thread messageListenerThread = new Thread(() -> {
        boolean interrupted = false;
        while (!interrupted) {
            try {
                if (this.reader.ready()) {
                    Message receivedMessage = gson.fromJson(reader.readLine(), Message.class);
                    respondToReceivedMessage(receivedMessage);
                    this.iview.update();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
    });

    private void respondToReceivedMessage(Message message) {
        if (message.getType() == MessageType.SERVER_MESSAGE ||
                message.getType() == MessageType.CLIENT_TEXT_MESSAGE) {
            model.addMessage(message.toString());
            iview.update();
        } else if (message.getType() == MessageType.SERVER_USER_LIST) {
            model.updateUserList(message.getText());
            iview.update();
        }
    }

    void joinWithNewNickName(String nickName) {
        Message message = new Message(MessageType.CLIENT_SET_NAME);
        message.setName(nickName);
        model.setNickName(nickName);
        writer.println(gson.toJson(message));
        writer.flush();
    }

    void sendMessage(String text) {
        Message message = new Message(MessageType.CLIENT_TEXT_MESSAGE);
        message.setName(model.getNickName());
        message.setText(text);

        writer.println(gson.toJson(message));
        writer.flush();
    }


    public Model getModel() {
        return model;
    }
}

