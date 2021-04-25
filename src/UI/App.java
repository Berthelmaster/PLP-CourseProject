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
    private JTextArea logArea;
    private Draw scaleDrawingEngine = new Draw();

    public App() {
        errorArea.setForeground(Color.RED);
        canvas = new Canvas(-10,50,-10,50);

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
                    ArrayList<PixelCollection> drawings = calculateDrawing(inputField.getText());
                    drawings.forEach(drawing -> drawPixels(drawing.getPixels()));
                    //TODO: Det ovenstående samt alt supporterende kode dertil bør laves om til følgende...
                    //Pt gemmes state af det tegnede hos den Jframe som holder tegningen, dette skal ændres inde i
                    //klassen Canvas hvor drawComponent bør kalde scala koden for at få alle de elementer som skal tegnes
                    //desuden skal scala jo i så fald gemme de elementer der skal tegnes.
                    //Endvidere bår scala nok også ved program start få størelsen på det det brugte canvas for at kunne udregne pixels korrekt
                    //til dette skal scala også kende størrelsen på akserne.
                    //til sidst bør det eneste GUI gør være at kalde update/redraw på canvas som så henter alt den
                    //skal gentegne fra scala koden.
                } catch (Exception exception) {
                    logError(exception.getMessage());
                }

                logInput();
            }
        });
    }

    public void drawPixel(int x, int y){
        canvas.drawPixel(x,y);
    }

    public void drawPixels(ArrayList<Pixel> pixelList){
        canvas.drawPixels(pixelList);
    }

    private ArrayList<PixelCollection> calculateDrawing(String string) {
        ArrayList<PixelCollection> drawings = new ArrayList<>();
        String[][] drawingEngineOutput = scaleDrawingEngine.DrawShape("(LINE (2 1) (3 4))"); //todo: change to use input
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
