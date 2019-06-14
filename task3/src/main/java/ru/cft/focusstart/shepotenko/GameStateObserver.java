package ru.cft.focusstart.shepotenko;

public interface IgameStateObserver {
    void GameStarted();
    void Win();
    void Loose();
}
