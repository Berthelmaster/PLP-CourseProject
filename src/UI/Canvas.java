package UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Canvas extends JPanel {
    private int x_size;
    private int x_begin;
    private int x_end;
    private int y_size;
    private int y_begin;
    private int y_end;
    private int gridSectionSize;
    private int markingSize;
    private int y_zero;
    private int x_zero;
    private ArrayList<PixelCollection> pixels;

    Canvas(int x_begin, int x_end, int y_begin, int y_end) {
        this.gridSectionSize = 0;
        this.x_size = (x_end-x_begin);
        this.x_begin = x_begin;
        this.x_end = x_end;
        this.y_size = (y_end-y_begin);
        this.y_begin = y_begin;
        this.y_end = y_end;
        this.pixels = new ArrayList<>();
        setBackground(Color.WHITE);
    }

    public void drawPixels(ArrayList<PixelCollection> pixelCollectionList){
        pixels.addAll(pixelCollectionList);
        repaint();
    }

    public void drawText(ArrayList<PixelCollection> pixelCollectionList){
        pixels.addAll(pixelCollectionList);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gridSectionSize == 0) {
            this.gridSectionSize = Math.min((getWidth() / this.x_size), (getHeight() / y_size));
        } else {
            x_size = getWidth()/gridSectionSize;
            y_size = getHeight()/gridSectionSize;
            x_end = x_begin + x_size;
            y_end = y_begin + y_size;
        }
        markingSize = gridSectionSize / 5;
        y_zero = getHeight() - (abs(y_begin) * gridSectionSize);
        x_zero = abs(x_begin) * gridSectionSize;

        renderGrid(g);
        renderAxis(g);
        if (!pixels.isEmpty()) renderPixels(g);
    }

    private void renderPixels(Graphics g) {
        pixels.forEach(pixelCollection -> {
            g.setColor(Color.getColor(pixelCollection.getColor()));
            pixelCollection.getPixels().forEach(pixel -> {
                if (pixel.getClass() == TextPixel.class) {
                    g.drawString(((TextPixel) pixel).getText(), pixel.get_x(), getHeight()-pixel.get_y());
                } else {
                    g.drawLine(pixel.get_x(), getHeight()-pixel.get_y(), pixel.get_x(), getHeight()-pixel.get_y());
                }
            });
        });
        g.setColor(Color.BLACK);
    }

    private void renderGrid(Graphics g) {
        g.setColor(new Color(224,224,224));
        int nRowCount = getHeight() / gridSectionSize;
        int currentY = getHeight() - gridSectionSize;
        for (int i = 0; i < nRowCount; i++) {
            g.drawLine(0, currentY, getWidth(), currentY);
            currentY = currentY - gridSectionSize;
        }

        int nColumnCount = getWidth() / gridSectionSize;
        int currentX = gridSectionSize;
        for (int i = 0; i < nColumnCount; i++) {
            g.drawLine(currentX, 0, currentX, getHeight());
            currentX = currentX + gridSectionSize;
        }
    }

    private void renderAxis(Graphics g){
        g.setColor(Color.darkGray);
        g.drawLine(0,y_zero, getWidth(), y_zero);
        g.drawLine(x_zero,0, x_zero, getHeight());

        //x-Axis
        int nColumnCount = getWidth() / gridSectionSize;
        int currentX = gridSectionSize;
        int x_count = x_begin + 1;
        for (int i = 0; i < nColumnCount; i++) {
            g.drawLine(currentX, y_zero + markingSize, currentX, y_zero - markingSize);
            String number = String.valueOf(x_count++);
            if (!number.equals("0")) g.drawString(number, currentX - gridSectionSize/2, y_zero + gridSectionSize);
            currentX = currentX + gridSectionSize;
        }

        //y-Axis
        int nRowCount = getHeight() / gridSectionSize;
        int currentY = getHeight() - gridSectionSize;
        int y_count = y_begin + 1;
        for (int i = 0; i < nRowCount; i++) {
            g.drawLine(x_zero + markingSize, currentY, x_zero - markingSize, currentY);
            String number = String.valueOf(y_count++);
            if (!number.equals("0")) g.drawString(number, x_zero - (gridSectionSize + 2), currentY + gridSectionSize/3);
            currentY = currentY - gridSectionSize;
        }
    }

    void renderText(Graphics g) {
        g.drawString("test", x_zero+1, y_zero-1);
    }
}
