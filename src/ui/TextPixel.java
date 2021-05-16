package ui;

public class TextPixel extends Pixel{
    private String text;

    TextPixel(int x, int y, String text) {
        super(x, y);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean isTextPixel() {
        return true;
    }
}
