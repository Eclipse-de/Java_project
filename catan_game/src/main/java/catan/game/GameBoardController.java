package catan.game;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class GameBoardController {

    @FXML
    private void handleRollDice(ActionEvent event) {
        System.out.println("Würfeln gedrückt!");
    }

    @FXML
    private void handleBuildRoad(ActionEvent event) {
        System.out.println("Straße bauen gedrückt!");
    }

    @FXML
    private void handleEndTurn(ActionEvent event) {
        System.out.println("Zug beenden gedrückt!");
    }

    @FXML
    private Pane boardPane;

    private static class HexTile {
        String resource;
        double x;
        double y;

        HexTile(String resource, double x, double y) {
            this.resource = resource;
            this.x = x;
            this.y = y;
        }
    }

    public void initialize() {
        double hexWidth = 160;
        double hexHeight = 185;
        double xOffset = hexWidth - 2;
        double yOffset = hexHeight - 40;

        int[] rowLengths = {3, 4, 5, 4, 3};
        List<String> resources = new ArrayList<>(Arrays.asList(
                "Holz", "Lehm", "Weizen", "Wolle", "Erz",
                "Holz", "Lehm", "Wüste", "Wolle", "Weizen",
                "Erz", "Wolle", "Holz", "Lehm", "Weizen",
                "Erz", "Wolle", "Weizen", "Holz"
        ));

        Collections.shuffle(resources);

        List<HexTile> tiles = new ArrayList<>();
        double startY = 18;
        int resIndex = 0;
        for (int row = 0; row < rowLengths.length; row++) {
            int count = rowLengths[row];
            double startX = 400 - (count * xOffset) / 2;

            for (int i = 0; i < count; i++) {
                tiles.add(new HexTile(resources.get(resIndex++), startX + i * xOffset, startY));
            }
            startY += yOffset;
        }

        for (HexTile tile : tiles) {
            String imagePath = switch (tile.resource) {
                case "Holz" -> "/catan/game/images/hexagon_wood.png";
                case "Lehm" -> "/catan/game/images/hexagon_clay.png";
                case "Weizen" -> "/catan/game/images/hexagon_wheat.png";
                case "Wolle" -> "/catan/game/images/hexagon_sheep.png";
                case "Erz" -> "/catan/game/images/hexagon_ore.png";
                default -> "/catan/game/images/hexagon_desert.png";
            };

            ImageView img = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
            img.setFitWidth(hexWidth);
            img.setFitHeight(hexHeight);
            img.setLayoutX(tile.x);
            img.setLayoutY(tile.y);
            boardPane.getChildren().add(img);
        }

        ImageView map_standard = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/catan/game/images/map_standard.png"))));
        map_standard.setFitWidth(800);
        map_standard.setFitHeight(800);
        map_standard.setLayoutX(0);
        map_standard.setLayoutY(0);
        boardPane.getChildren().add(map_standard);

        map_standard.toFront();

    }
}
