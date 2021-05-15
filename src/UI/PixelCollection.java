package UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PixelCollection {
    private Color color;
    private ArrayList<Pixel> pixels;

    PixelCollection() {
        color = null;
        pixels = new ArrayList<>();
    }

    public PixelCollection(String[] stringArray) throws NoSuchFieldException, IllegalAccessException {
        color = (Color) Color.class.getField(stringArray[0]).get(null);
        pixels = new ArrayList<>();
        if (stringArray.length == 4) {
            pixels.add(new TextPixel(Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2]), stringArray[3]));
        } else {
            for (int i = 1; i < stringArray.length; i += 2) {
                pixels.add(new Pixel(Integer.parseInt(stringArray[i]), Integer.parseInt(stringArray[i + 1])));
            }
        }
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Pixel> getPixels() {
        return pixels;
    }
}
