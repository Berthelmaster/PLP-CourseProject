package UI;

import Scala.Draw;

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
    private JList list1;
    private JTextArea logArea;
    private Draw scaleDrawingEngine = new Draw();

    public App() {
        errorArea.setForeground(Color.RED);
        canvas = new Canvas(-5,85,-5,55);

        panelMain.setBorder(new EmptyBorder(4,8,4,8));
        shapeDisplayPanel.add(canvas);
        this.setContentPane((this.panelMain));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<PixelCollection> pixels = calculateDrawing(inputField.getText());
                    pixels.forEach(drawing -> canvas.drawPixels(pixels));
                } catch (Exception exception) {
                    if (exception instanceof NoSuchFieldException || exception instanceof IllegalAccessException) {
                        logError("No such color found - " + exception.getMessage());
                    } else {
                        logError(exception.getMessage());
                    }
                }
                logInput();
            }
        });
    }

    private ArrayList<PixelCollection> calculateDrawing(String input) throws Exception{
        ArrayList<PixelCollection> drawings = new ArrayList<>();
        String[][] drawingEngineOutput = scaleDrawingEngine.DrawShape( input.isEmpty() ? "(LINE (5 5) (10 10))" : input );
        for (String[] strings : drawingEngineOutput) {
            drawings.add(new PixelCollection(strings));
        }
        return drawings;
    }

    private void logInput() {
        logArea.append(inputField.getText() + "\n");
        inputField.setText("");
    }

    private void logError(String errorMessage) {
        errorArea.append(errorMessage + "\n");
    }
}
