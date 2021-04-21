package UI;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.abs;

public class Canvas extends JPanel {
    private int gridSectionSize;
    private int x_size;
    private int x_begin;
    private int x_end;
    private int y_size;
    private int y_begin;
    private int y_end;

    Canvas(int x_begin, int x_end, int y_begin, int y_end) {
        this.x_size = (x_end-x_begin);
        this.x_begin = x_begin;
        this.x_end = x_end;
        this.y_size = (y_end-y_begin);
        this.y_begin = y_begin;
        this.y_end = y_end;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderGrid(g);
        renderAxisLines(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.drawRect(10, 15, 90, 60);
    }

    private void renderGrid(Graphics g) {
        this.gridSectionSize = Math.min((getHeight() / this.x_size), (getWidth() / y_size));
        if(gridSectionSize == 0) gridSectionSize = 10;

        g.setColor(Color.lightGray);
        int nRowCount = getHeight() / gridSectionSize;
        int currentX = getHeight() - gridSectionSize;
        for (int i = 0; i < nRowCount; i++) {
            g.drawLine(0, currentX, getWidth(), currentX);
            currentX = currentX - gridSectionSize;
        }

        int nColumnCount = getWidth() / gridSectionSize;
        int currentY = gridSectionSize;
        for (int i = 0; i < nColumnCount; i++) {
            g.drawLine(currentY, 0, currentY, getHeight());
            currentY = currentY + gridSectionSize;
        }
    }

    private void renderAxisLines(Graphics g){
        int yZero = getHeight() - (abs(y_begin) * gridSectionSize);
        int xZero = abs(x_begin) * gridSectionSize;

        g.setColor(Color.darkGray);
        g.drawLine(0,yZero, getWidth(), yZero);
        g.drawLine(xZero,0, xZero, getHeight());
    }
}
