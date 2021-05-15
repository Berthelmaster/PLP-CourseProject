package ui;

import scala.Draw;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class App extends JFrame{
    private int errorEnumerator = 0;
    private int logEnumerator = 0;
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
    private JTextArea logArea;
    private JTextArea errorArea;
    private JScrollPane logScroll;
    private JScrollPane errorScroll;
    private Draw scaleDrawingEngine = new Draw();

    public App() {
        errorArea.setForeground(Color.RED);
        errorArea.setEditable(false);
        logArea.setEditable(false);
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
                    logError(exception.getMessage());
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
        logArea.append((++logEnumerator) + ". " + inputField.getText() + "\n");
        inputField.setText("");
    }

    private void logError(String errorMessage) {
        errorArea.append((++errorEnumerator) + ". " + errorMessage + "\n");
    }
}
