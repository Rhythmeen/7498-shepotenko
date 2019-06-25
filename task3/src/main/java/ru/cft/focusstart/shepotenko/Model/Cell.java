package ru.cft.focusstart.shepotenko.Model;


public class Cell {
    private CellValue value;
    private CellState state;

    Cell() {
        this.state = CellState.CLOSED;
        }

    public CellValue getValue() {
        return this.value;
    }

    void setValue(CellValue value) {
        this.value = value;
    }

    void setClosed() {
        this.state = CellState.CLOSED;
    }

    void setOpened() {
        this.state = CellState.OPENED;
    }

    void setFlaged() {
        this.state = CellState.FLAGED;
    }

    void setQuestioned() {
        this.state = CellState.QUESTIONED;
    }

    void setExploded() {
        this.state = CellState.EXPLODED;
    }

    void setMistake() {
        this.state = CellState.MISTAKE;
    }

    public boolean isClosed() {
        return this.state == CellState.CLOSED;
    }

    public boolean isOpened() {
        return this.state == CellState.OPENED;
    }

    public boolean isFlaged() {
            return this.state == CellState.FLAGED;
    }

    public boolean isQuestioned() {
        return this.state == CellState.QUESTIONED;
    }

    public boolean isExploded() {
        return this.state == CellState.EXPLODED;
    }

    public boolean isMistake() {
        return this.state == CellState.MISTAKE;
    }

}



