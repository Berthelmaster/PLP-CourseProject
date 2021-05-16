package ui;

import java.util.Objects;

public class Pixel {
    private int _x;
    private int _y;

    public Pixel(int x, int y) {
        this._x = x;
        this._y = y;
    }

    public boolean isTextPixel() {
        return false;
    }

    public int get_x() {
        return _x;
    }

    public int get_y() {  return _y; }

    @Override
    public boolean equals(Object o) {
        // the same object
        if (this == o) return true;
        // not the same class
        if (o == null || getClass() != o.getClass()) return false;

        // safe type casting
        Pixel pixel = (Pixel) o;

        return _x == pixel._x && _y == pixel._y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_x, _y);
    }
}
