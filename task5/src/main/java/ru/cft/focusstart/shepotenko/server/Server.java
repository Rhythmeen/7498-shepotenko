package ru.cft.focusstart.shepotenko.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import com.google.gson.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.shepotenko.common.Message;
import ru.cft.focusstart.shepotenko.common.MessageType;

public class Server {
    private final Logger logger = LoggerFactory.getLogger(Server.class);
    private final Gson gson = new Gson();

    private ArrayList<ClientHandler> clients;
    private ServerSocket serverSocket;

    Server() {
        Properties properties = new Properties();
        try (InputStream propertiesStream = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            properties.load(propertiesStream);
            serverSocket = new ServerSocket(Integer.valueOf(properties.getProperty("server.port")));
        } catch (IOException e) {
            logger.error("error reading client properties");
        }
        this.clients = new ArrayList<>();
        start();
    }

    private void start() {
        messageListenerThread.start();
        connectionListenerThread.start();

        logger.info("server is running");

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            messageListenerThread.interrupt();
            connectionListenerThread.interrupt();
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.error("error closing server socket");
            }
            try {
                for (ClientHandler client : clients) {
                    client.getSocket().close();
                }
            } catch (IOException e) {
                logger.error("error closing client socket");
            }
        }));
    }

    private Thread connectionListenerThread = new Thread(() -> {
        while (!Thread.interrupted()) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket);
                client.getWriter().println(gson.toJson(getUpdatedUserListMessage()));
                clients.add(client);
                logger.info("new connection");
            } catch (IOException e) {
                logger.error("error adding new client");
            }
        }
    });

    private Thread messageListenerThread = new Thread(() -> {
        boolean interrupted = false;
        while (!interrupted) {
            String receivedJson = null;
            try {
                Iterator<ClientHandler> it = clients.iterator();
                while (it.hasNext()) {
                    ClientHandler client = it.next();
                    if(client.getSocket().isClosed()) {
                        it.remove();
                    }
                    if (client.getReader().ready()) {
                        receivedJson = client.getReader().readLine();
                    }
                    if (receivedJson != null) {
                        respondToReseivedMessage(client, receivedJson);
                        receivedJson = null;
                    }
                }
            } catch (IOException e) {
                logger.error("error getting new message");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                interrupted = true;
                logger.error("client operation was interrupted");
            }
        }

    });

    private void sendToAll(Message message) {
        String toSend = gson.toJson(message);
        for (ClientHandler client : clients) {
            client.getWriter().println(toSend);
            client.getWriter().flush();
        }
    }

    private Message getUpdatedUserListMessage() {
        Message userListMessage = new Message(MessageType.SERVER_USER_LIST);
        StringBuilder userList = new StringBuilder();
        for (ClientHandler client : clients) {
            if (!client.getSocket().isClosed() && client.getName() != null) {
                userList.append(client.getName()).append("\n");
            }
        }
        userListMessage.setText(userList.toString());
        return userListMessage;
    }

    private void respondToReseivedMessage(ClientHandler client, String receivedJson) {
        Message receivedMessage = gson.fromJson(receivedJson, Message.class);
        Message toSend;
        switch (receivedMessage.getType()) {
            case CLIENT_SET_NAME:
                toSend = new Message(MessageType.SERVER_MESSAGE);
                client.setName(receivedMessage.getName());
                String text = receivedMessage.getName() + " joined to chat";
                toSend.setText(text);
                sendToAll(toSend);
                sendToAll(getUpdatedUserListMessage());
                logger.info(text);
                break;
            case CLIENT_TEXT_MESSAGE:
                toSend = receivedMessage;
                sendToAll(toSend);
                logger.info(receivedMessage.getName() + " sent message");
                break;
            case CLIENT_EXIT:
                toSend = new Message(MessageType.SERVER_MESSAGE);
                String txt = client.getName() + " left chat";
                toSend.setText(txt);
                closeConnection(client);
                sendToAll(toSend);
                sendToAll(getUpdatedUserListMessage());
                logger.info(txt);
        }
    }

    private void closeConnection(ClientHandler client) {
        try {
            client.getSocket().close();
        } catch (IOException e) {
            logger.error("error closing connection");
        }
    }
}


