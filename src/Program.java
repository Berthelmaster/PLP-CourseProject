import Scala.Draw;
import Scala.alex_playground;
import Scala.test;
import UI.App;
import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Program extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Draw t = new Draw();
        t.DrawShape("");
        new App();
    }
}
