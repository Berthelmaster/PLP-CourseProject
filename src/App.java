import javax.swing.*;

public class App {
    private JButton button1;
    private JPanel panelMain;

    public App() {
        JFrame frame = new JFrame("App");
        frame.setContentPane((this.panelMain));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
