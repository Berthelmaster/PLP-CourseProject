package UI;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame{
    private JPanel panelMain;
    private JButton inputButton;
    private JTextField inputField;
    private JPanel shapeDisplayPanel;
    private JPanel inputPanel;
    private JPanel informationPanel;
    private JPanel logPanel;
    private JPanel errorPanel;
    private JLabel logLabel;
    private JLabel errorLabel;
    private JTextArea errorArea;
    private JTextArea logArea;

    public App() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setColor(Color.BLACK);
                g2d.drawRect(10, 15, 90, 60);
            }
        };

        panel.setBackground(Color.WHITE);
        shapeDisplayPanel.add(panel);
        this.setContentPane((this.panelMain));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public void drawShape() {
    }
}
