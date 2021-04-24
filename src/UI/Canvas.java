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
    private ArrayList<Pixel> pixels;

    Canvas(int x_begin, int x_end, int y_begin, int y_end) {
        this.x_size = (x_end-x_begin);
        this.x_begin = x_begin;
        this.x_end = x_end;
        this.y_size = (y_end-y_begin);
        this.y_begin = y_begin;
        this.y_end = y_end;
        this.pixels = new ArrayList<>();
        setBackground(Color.WHITE);
    }

    public void drawPixel(int x, int y){
        pixels.add(new Pixel(x,(getHeight() - y)));
        repaint();
    }

    public void drawPixels(ArrayList<Pixel> pixelList){
        pixelList.forEach(pixel -> pixels.add(new Pixel(pixel.get_x(),(getHeight() - pixel.get_y()))));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.gridSectionSize = Math.min((getHeight() / this.x_size), (getWidth() / y_size));
        if(gridSectionSize == 0) gridSectionSize = 10;
        markingSize = gridSectionSize / 5;
        y_zero = getHeight() - (abs(y_begin) * gridSectionSize);
        x_zero = abs(x_begin) * gridSectionSize;

        renderGrid(g);
        renderAxis(g);
        if (!pixels.isEmpty()) renderPixels(g);
    }

    private void renderPixels(Graphics g) {
        pixels.forEach(pixel -> g.drawLine(pixel.get_x(), pixel.get_y(), pixel.get_x(), pixel.get_y()));
    }

    private void renderGrid(Graphics g) {
        g.setColor(Color.lightGray);
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

        int nColumnCount = (getWidth() - x_zero) / gridSectionSize;
        int currentX = x_zero + gridSectionSize;
        for (int i = 0; i < nColumnCount; i++) {
            g.drawLine(currentX, y_zero + markingSize, currentX, y_zero - markingSize);
            currentX = currentX + gridSectionSize;
        }

        int nRowCount = y_zero / gridSectionSize;
        int currentY = y_zero - gridSectionSize;
        for (int i = 0; i < nRowCount; i++) {
            g.drawLine(x_zero + markingSize, currentY, x_zero - markingSize, currentY);
            currentY = currentY - gridSectionSize;
        }
    }
}
