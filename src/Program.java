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
    public void start(Stage primaryStage) throws Exception {
        test t = new test();
        t.sayHello();
        new App();
    }
}
