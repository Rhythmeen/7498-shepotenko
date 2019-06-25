package ru.cft.focusstart.shepotenko.GUI;

public interface GameStateObserver {
    void onWin();
    void onLoose();
    void onFieldChanged();
    void onTimeChanged();
}
