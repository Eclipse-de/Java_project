// JavaFX Ui Klass

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello, JavaFX with Maven!");
        Scene scene = new Scene(new StackPane(label), 400, 300);
        stage.setScene(scene);
        stage.setTitle("JavaFX App");
        stage.show(); 
    }
    
}
