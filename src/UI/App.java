package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class App extends JFrame{
    private JPanel panelMain;
    private JButton inputButton;
    private JTextField inputField;
    private JPanel shapeDisplayPanel;
    private Canvas canvas;
    private JPanel inputPanel;
    private JPanel informationPanel;
    private JPanel logPanel;
    private JPanel errorPanel;
    private JLabel logLabel;
    private JLabel errorLabel;
    private JTextArea errorArea;
    private JTextArea logArea;

    public App() {
        errorArea.setForeground(Color.RED);
        canvas = new Canvas(-1,10,-1,10);

        panelMain.setBorder(new EmptyBorder(4,4,4,4));
        shapeDisplayPanel.add(canvas);
        this.setContentPane((this.panelMain));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logArea.append(inputField.getText() + "\n");
                inputField.setText("");
            }
        });
    }

    public void drawPixel(int x, int y){
        canvas.drawPixel(x,y);
    }

    public void drawPixel(ArrayList<Pixel> pixelList){
        canvas.drawPixels(pixelList);
    }
}
