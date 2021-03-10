package UI;

import javax.swing.*;
import java.awt.*;

public class App {
    private JButton button1;
    private JPanel panelMain;

    public App() {
        JFrame frame = new JFrame("App");
        frame.setContentPane((this.panelMain));
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(600, 450));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }
}
