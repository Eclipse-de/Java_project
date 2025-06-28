package catan.game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameBoardController {

    @FXML
    private Button rollDiceButton;

    @FXML
    public void handleRollDice() {
        System.out.println("Würfel wurde gedrückt");
        // Würfeln-Logik
    }

    @FXML
    public void handleBuildRoad() {
        System.out.println("Straße bauen gedrückt!");
        // Baulogik hier
    }

    @FXML
    public void handleEndTurn() {
        System.out.println("Zug beendet.");
    }

    @FXML
    public void initialize() {
        // Wird beim Laden automatisch aufgerufen
    }
}
