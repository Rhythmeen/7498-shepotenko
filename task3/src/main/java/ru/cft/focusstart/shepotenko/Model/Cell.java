package ru.cft.focusstart.shepotenko.Model;


public class Cell {
    private cellValue Value;
    private CellState state;

    Cell() {
        this.state = CellState.CLOSED;
        }

    public void setValue(cellValue value) {
        this.Value = value;
    }

    public cellValue getValue() {
        return this.Value;
    }

    public void setClosed() {
        this.state = CellState.CLOSED;
    }

    public void setOpened() {
        this.state = CellState.OPENED;
    }

    public void setFlaged() {
        this.state = CellState.FLAGED;
    }

    public void setQuestioned() {
        this.state = CellState.QUESTIONED;
    }

    public void setExploded() {
        this.state = CellState.EXPLODED;
    }

    public void setMistake() {
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



