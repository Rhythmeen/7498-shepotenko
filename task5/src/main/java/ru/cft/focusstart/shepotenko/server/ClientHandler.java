package ru.cft.focusstart.shepotenko.server;

import java.io.*;
import java.net.Socket;

class ClientHandler {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String name;

    ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream());
    }

    Socket getSocket() {
        return socket;
    }

    BufferedReader getReader() {
        return reader;
    }

    PrintWriter getWriter() {
        return writer;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
