package catan.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

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
    @FXML
    private Group zoomGroup;

    private Scale scale = new Scale(1, 1, 0, 0);
    private Translate translate = new Translate();

    private double mouseAnchorX, mouseAnchorY;
    private double translateAnchorX, translateAnchorY;

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

        // Zoom and Movement
        zoomGroup.getTransforms().addAll(translate, scale);

        //Zoom
        boardPane.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.05;
            if (event.getDeltaY() < 0) {
                zoomFactor = 1 / zoomFactor;
            }

            double newScale = scale.getX() * zoomFactor;
            if (newScale < 0.2 || newScale > 5) return;

            scale.setX(newScale);
            scale.setY(newScale);
        });

        //Panning
        boardPane.setOnMousePressed((MouseEvent event) -> {
            mouseAnchorX = event.getSceneX();
            mouseAnchorY = event.getSceneY();
            translateAnchorX = translate.getX();
            translateAnchorY = translate.getY();
        });

        boardPane.setOnMouseDragged((MouseEvent event) -> {
            translate.setX(translateAnchorX + event.getSceneX() - mouseAnchorX);
            translate.setY(translateAnchorY + event.getSceneY() - mouseAnchorY);
        });

    }
}
