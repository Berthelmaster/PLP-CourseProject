import scala.Draw;
import ui.App;
import javafx.application.Application;
import javafx.stage.Stage;

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
