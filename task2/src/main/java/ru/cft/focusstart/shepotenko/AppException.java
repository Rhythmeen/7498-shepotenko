package ru.cft.focusstart.shepotenko;

public class AppException extends Exception {


    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Exception e) {
        super(message, e);
    }
}

