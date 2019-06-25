package ru.cft.focusstart.shepotenko.Model;

import java.util.Objects;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate coord = (Coordinate) o;
        return x == coord.x &&
                y == coord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
