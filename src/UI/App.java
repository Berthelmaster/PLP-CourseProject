package UI;

import javax.swing.*;
import java.awt.*;

public class App {
    private JPanel panelMain;
    private JButton inputButton;
    private JTextField inputField;
    private JPanel canvasPanel;
    private JPanel inputPanel;
    private JPanel informationPanel;
    private JPanel logPanel;
    private JPanel errorPanel;
    private JLabel logLabel;
    private JLabel errorLabel;
    private JTextArea errorArea;
    private JTextArea logArea;
    private Canvas canvas;

    public App() {
        JFrame frame = new JFrame("App");
        frame.setContentPane((this.panelMain));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        canvas = new Canvas();
        canvasPanel.add(canvas);
    }
}
