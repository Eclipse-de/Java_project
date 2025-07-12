package catan.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import catan.game.gameboard.node;
import catan.game.gameboard.tile;
import catan.game.main.GameEngine;
import catan.game.main.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GameBoardController {

    private GameEngine gameEngine;
    private List<String> resources;

    private enum BuildMode { NONE, ROAD, VILLAGE, CITY }
    private BuildMode currentBuildMode = BuildMode.NONE;

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    @FXML
    private ListView<String> resourceList;

    @FXML
    private Pane boardPane;

    @FXML
    private Pane streetPane;

    @FXML
    private Pane buildingPane;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private void street(ActionEvent event) {
        if (currentBuildMode == BuildMode.ROAD) {
            currentBuildMode = BuildMode.NONE;
        } else {
            currentBuildMode = BuildMode.ROAD;
        }
        highlightBuildMode(currentBuildMode);
    }

    @FXML
    private void handeln(ActionEvent event) {
        System.out.println("Handeln gedrückt!");
        currentBuildMode = BuildMode.NONE;
        highlightBuildMode(currentBuildMode);
    }

    @FXML
    private void dorf(ActionEvent event) {
        if (currentBuildMode == BuildMode.VILLAGE) {
            currentBuildMode = BuildMode.NONE;
        } else {
            currentBuildMode = BuildMode.VILLAGE;
        }
        highlightBuildMode(currentBuildMode);
    }
    @FXML
    private void stadt(ActionEvent event) {
        if (currentBuildMode == BuildMode.CITY) {
            currentBuildMode = BuildMode.NONE;
        } else {
            currentBuildMode = BuildMode.CITY;
        }
        highlightBuildMode(currentBuildMode);
    }

    private void highlightBuildMode(BuildMode mode) {
        // Beide unsichtbar machen
        streetPane.setVisible(false);
        buildingPane.setVisible(false);

        switch (mode) {
            case ROAD -> streetPane.setVisible(true);
            case VILLAGE, CITY -> buildingPane.setVisible(true);
            default -> {
                // NONE or any other case: both panes remain invisible
            }
        }
    }

    @FXML
    private void handleBuildBuilding(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String id = clickedButton.getId(); // z. B. "buildingNode17"

        if (id != null && id.startsWith("buildingNode")) {
            int nodeIndex = Integer.parseInt(id.replace("buildingNode", ""));
            System.out.println("Versuche Gebäude auf Node " + nodeIndex + " zu bauen");

            // Versuche Dorf zu bauen
            if (currentBuildMode == BuildMode.VILLAGE){
                boolean gebaut = gameEngine.tryBuildVillage(nodeIndex, gameEngine.getCurrentPlayer());
                updateResourceDisplay(gameEngine.getCurrentPlayer());
                if (gebaut) {
                    double x = clickedButton.getLayoutX();
                    double y = clickedButton.getLayoutY();

                    placeBuildingImage(x, y, "VILLAGE");
                    //clickedButton.setVisible(false);
                }
            }
            
            else{
                if (currentBuildMode == BuildMode.CITY){
                    boolean success = gameEngine.tryBuildCity(nodeIndex, gameEngine.getCurrentPlayer());
                    if (success) {
                        double x = clickedButton.getLayoutX();
                        double y = clickedButton.getLayoutY();

                        placeBuildingImage(x, y, "CITY");
                        clickedButton.setVisible(false);
                    }
                }

            }
        }
    }
    private final Map<Point2D, ImageView> imagesByPosition = new HashMap<>();

private void placeBuildingImage(double x, double y, String type) {
    String imagePath;

    switch (type) {
        case "VILLAGE" -> imagePath = "/catan/game/images/Dorf.png";
        case "CITY"    -> imagePath = "/catan/game/images/Stadt.png";
        default        -> throw new IllegalArgumentException("Unbekannter BuildingType: " + type);
    }

    Point2D pos = new Point2D(x, y);
    ImageView existing = imagesByPosition.get(pos);

    // Wenn ein Dorf existiert und wir eine Stadt bauen wollen, ersetze das Bild
    if (existing != null) {
        if ("CITY".equals(type)) {
            boardPane.getChildren().remove(existing); // altes Dorf entfernen
        } else {
            // Ein Dorf existiert schon – nichts tun
            return;
        }
    }

    // Neues Gebäude platzieren
    ImageView buildingImage = new ImageView(new Image(
            Objects.requireNonNull(getClass().getResourceAsStream(imagePath))
    ));

    buildingImage.setFitWidth(30);
    buildingImage.setFitHeight(30);
    buildingImage.setLayoutX(x);
    buildingImage.setLayoutY(y);

    boardPane.getChildren().add(buildingImage);
    imagesByPosition.put(pos, buildingImage);
}



    @FXML
    private void handleBuildRoad(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String id = clickedButton.getId(); // z. B. "roadEdge5"

        if (id != null && id.startsWith("roadEdge")) {
            int edgeIndex = Integer.parseInt(id.replace("roadEdge", ""));
            System.out.println("Versuche Straße auf Edge " + edgeIndex + " zu bauen");

            boolean gebaut = gameEngine.tryBuildRoad(edgeIndex, gameEngine.getCurrentPlayer());
            updateResourceDisplay(gameEngine.getCurrentPlayer());

            if (gebaut) {
                double x = clickedButton.getLayoutX();
                double y = clickedButton.getLayoutY();

                // Bestimme Richtung (später besser per Datenstruktur)
                placeRoadImage(x, y, "horizontal");  // ← z. B. statisch für jetzt
                clickedButton.setVisible(false);

            }
        }
    }


    private void placeRoadImage(double x, double y, String direction) {
        String imagePath;

        switch (direction) {
            case "horizontal" -> imagePath = "/catan/game/images/Straße.png";
            case "upLeft"     -> imagePath = "/catan/game/images/road_up_left.png";
            case "upRight"    -> imagePath = "/catan/game/images/road_up_right.png";
            default           -> imagePath = "/catan/game/images/road_horizontal.png";
        }

        ImageView roadImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        roadImage.setFitWidth(60);  // Passe an deine Grafik an
        roadImage.setFitHeight(20);
        roadImage.setLayoutX(x);
        roadImage.setLayoutY(y);

        boardPane.getChildren().add(roadImage);
    }




    @FXML
    private void handleEndTurn(ActionEvent event) {
        System.out.println("Zug beenden gedrückt!");
        gameEngine.nextTurn();

        updateResourceDisplay(gameEngine.getCurrentPlayer());
        Player currentPlayer = gameEngine.getCurrentPlayer();
        currentPlayerLabel.setText(currentPlayer.getName());
        currentBuildMode = BuildMode.NONE;
        highlightBuildMode(currentBuildMode);
    }

    private void updateResourceDisplay(Player player) {
        resourceList.getItems().clear();
        for (Map.Entry<String, Integer> entry : player.getResources().entrySet()) {
            String line = entry.getKey() + ": " + entry.getValue();
            resourceList.getItems().add(line);
        }
    }

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

    public void initTiles() {
        if (resources == null) {
            System.err.println("⚠ Ressourcenliste wurde nicht gesetzt!");
            return;
        }

        double hexWidth = 160;
        double hexHeight = 185;
        double xOffset = hexWidth - 2;
        double yOffset = hexHeight - 40;

        int[] rowLengths = {3, 4, 5, 4, 3};
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
                case "wood" -> "/catan/game/images/hexagon_wood.png";
                case "brick" -> "/catan/game/images/hexagon_clay.png";
                case "wheat" -> "/catan/game/images/hexagon_wheat.png";
                case "sheep" -> "/catan/game/images/hexagon_sheep.png";
                case "ore" -> "/catan/game/images/hexagon_ore.png";
                default -> "/catan/game/images/hexagon_desert.png";
            };

            ImageView img = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
            img.setFitWidth(hexWidth);
            img.setFitHeight(hexHeight);
            img.setLayoutX(tile.x);
            img.setLayoutY(tile.y);

            img.setOnMouseClicked(e -> handleTileClick(tile, e));

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

    public node findClosestNode(double x, double y) {
        double minDist = Double.MAX_VALUE;
        node closest = null;

        for (tile t : gameEngine.getBoard().getTiles()) {
            for (node n : t.getNodes()) {
                if (n == null) continue;
                double dist = Math.hypot(n.getX() - x, n.getY() - y);
                if (dist < 20 && dist < minDist) {
                    closest = n;
                    minDist = dist;
                }
            }
        }

        return closest;
    }


    private void handleTileClick(HexTile tile, MouseEvent event) {
        double clickX = event.getX();
        double clickY = event.getY();
        System.out.println("Hexfeld angeklickt: Ressource = " + tile.resource + ", BuildMode = " + currentBuildMode);

        switch (currentBuildMode) {
            case VILLAGE -> System.out.println("Dorf");//gameEngine.tryBuildVillage(clickX, clickY);
            case CITY -> System.out.println("Stadt");//gameEngine.tryBuildCity(clickX, clickY);
            case ROAD -> System.out.println("Strasse");//gameEngine.tryBuildRoad(clickX, clickY);
            default -> System.out.println("Kein Baumodus aktiv");
        }
    }
}
