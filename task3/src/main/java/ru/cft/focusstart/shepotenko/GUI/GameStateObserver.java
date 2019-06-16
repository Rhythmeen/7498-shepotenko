package ru.cft.focusstart.shepotenko.GUI;

public interface GameStateObserver {
    void gameStarted();
    void onWin();
    void onLoose();
    void updateUI();
}
