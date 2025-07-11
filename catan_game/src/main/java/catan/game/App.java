package catan.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import catan.game.main.GameEngine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard_copy_test_jakob_new.fxml"));
        Parent root = loader.load();

        GameBoardController controller = loader.getController();

        // Ressourcenliste vorbereiten
        List<String> resources = Arrays.asList(
            "Holz", "Lehm", "Weizen", "Wolle", "Erz",
            "Holz", "Lehm", "Wüste", "Wolle", "Weizen",
            "Erz", "Wolle", "Holz", "Lehm", "Weizen",
            "Erz", "Wolle", "Weizen", "Holz"
        );
        Collections.shuffle(resources);

        System.out.println(resources);

        GameEngine gameEngine = new GameEngine();
        controller.setGameEngine(gameEngine);     // GameEngine setzen
        controller.setResources(resources);       // Ressourcen setzen
        controller.initTiles();                   // Spielfeld initialisieren

        gameEngine.setResources(resources);
        gameEngine.initGameBoard();
        gameEngine.startGame();

        stage.setTitle("Siedler von Catan");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
