import Scala.alex_playground;
import Scala.test;
import UI.App;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        test t = new test();
        t.sayHello();
        new App();

        alex_playground play = new alex_playground();
        play.Draw("");
    }
}
