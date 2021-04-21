package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
        panelMain.setBorder(new EmptyBorder(4,4,4,4));
        shapeDisplayPanel.add(new Canvas(-1,10,-1,10));
        this.setContentPane((this.panelMain));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public void drawPixel(){
        //do some shit
    }
}
