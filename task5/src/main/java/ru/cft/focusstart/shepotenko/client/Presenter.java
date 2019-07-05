package ru.cft.focusstart.shepotenko.client;

import com.google.gson.Gson;
import ru.cft.focusstart.shepotenko.common.Message;
import ru.cft.focusstart.shepotenko.common.MessageType;
import ru.cft.focusstart.shepotenko.server.Server;

import java.io.*;

import java.net.Socket;
import java.util.Properties;


public class Presenter {
    private final Gson gson = new Gson();
    private Iview iview;
    private Model model;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Presenter(Iview iview) {
        this.iview = iview;
        this.model = new Model();

        Properties properties = new Properties();
        try (InputStream propertiesStream = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            properties.load(propertiesStream);
            this.socket = new Socket("localhost", Integer.valueOf(properties.getProperty("server.port")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    private void start() {
        messageListenerThread.start();
    }

    Thread messageListenerThread = new Thread(() -> {
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

    public void respondToReceivedMessage(Message message) {
        if (message.getType() == MessageType.SERVER_MESSAGE ||
                message.getType() == MessageType.CLIENT_TEXT_MESSAGE) {
            model.addMessage(message.toString());
            iview.update();
        } else if (message.getType() == MessageType.SERVER_USER_LIST) {
            model.updateUserList(message.getText());
            iview.update();
        }
    }


    public Model getModel() {
        return model;
    }

    public boolean joinWithNewNickName(String nickName) {
        if (!model.getUsers().contains(nickName) && !nickName.equals("")) {
            Message message = new Message(MessageType.CLIENT_SET_NAME);
             message.setName(nickName);
             model.setNickName(nickName);
             writer.println(gson.toJson(message));
             writer.flush();
             return true;
        } else return false;
    }

    public void sendMessage (String text) {
        Message message = new Message(MessageType.CLIENT_TEXT_MESSAGE);
        message.setName(model.getNickName());
        message.setText(text);

        writer.println(gson.toJson(message));
        writer.flush();
    }

    public void disconnect() {
        Message message = new Message(MessageType.CLIENT_EXIT);
        writer.println(gson.toJson(message));
        writer.flush();

        messageListenerThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

