package UI;

import java.util.ArrayList;
import java.util.List;

public class PixelCollection {
    private String color;
    private ArrayList<Pixel> pixels;

    PixelCollection() {
        color = "";
        pixels = new ArrayList<>();
    }

    PixelCollection(String[] stringArray) {
        color = stringArray[0];
        pixels = new ArrayList<>();
        for (int i = 1; i < stringArray.length; i +=2) {
            pixels.add(new Pixel(Integer.parseInt(stringArray[i]), Integer.parseInt(stringArray[i+1])));
        }
    }

    public String getColor() {
        return color;
    }

    public ArrayList<Pixel> getPixels() {
        return pixels;
    }
}