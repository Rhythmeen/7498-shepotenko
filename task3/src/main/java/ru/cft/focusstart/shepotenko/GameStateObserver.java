package ru.cft.focusstart.shepotenko;

public interface GameStateObserver {
    void gameStarted();
    void win();
    void loose();
    void changeUnmarkedBombsCounter();
}
