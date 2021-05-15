package UI;

public class Pixel {
    private int _x;
    private int _y;

    Pixel(int x, int y) {
        this._x = x;
        this._y = y;
    }

    public boolean isTextPixel() {
        return false;
    }

    public int get_x() {
        return _x;
    }

    public int get_y() {
        return _y;
    }
}
