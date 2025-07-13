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
import javafx.application.Platform;
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

    private enum BuildMode { NONE, ROAD, VILLAGE, CITY, TRADE}
    private BuildMode currentBuildMode = BuildMode.NONE;

    private enum TradeMode { NONE, P1, P2, P3, BANK}
    private TradeMode currentTradeMode = TradeMode.NONE;


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
    private Pane gameWin;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label playerName;

    @FXML
    private Pane pane_tradingwindow;

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
        if (currentBuildMode == BuildMode.TRADE) {
            currentBuildMode = BuildMode.NONE;
        } else {
            currentBuildMode = BuildMode.TRADE;
        }
        highlightBuildMode(currentBuildMode);
        trade();
    }

    @FXML private Button btn_toolbar_rr;
    @FXML private Label otherPlayer;
    @FXML private Pane pane_trading_desicions;

    @FXML
    private void tradeWith(ActionEvent event) {
        Object source = event.getSource();

        if (source == btn_toolbar_ll) {
            currentTradeMode = TradeMode.P1;
            pane_trading_desicions.setVisible(true);
            otherPlayer.setText(btn_toolbar_ll.getText());

        } else if (source == btn_toolbar_ml) {
            currentTradeMode = TradeMode.P2;
            pane_trading_desicions.setVisible(true);
            otherPlayer.setText(btn_toolbar_ml.getText());

        } else if (source == btn_toolbar_mr) {
            currentTradeMode = TradeMode.P3;
            pane_trading_desicions.setVisible(true);
            otherPlayer.setText(btn_toolbar_mr.getText());

        } else if (source == btn_toolbar_rr) {
            currentTradeMode = TradeMode.BANK;
            pane_trading_desicions.setVisible(true);
            otherPlayer.setText(btn_toolbar_rr.getText());

        } else {
            currentTradeMode = TradeMode.NONE;
            System.out.println("Kein gültiger Button erkannt.");
        }

        // Optional: Update GUI (z. B. Handelspanel anzeigen)
        // updateTradeUI();
    }

    @FXML private Label lb_trade_meplayer_ll;
    @FXML private Label lb_trade_oplayer_ll;

    @FXML private Label lb_trade_meplayer_ml;
    @FXML private Label lb_trade_oplayer_ml;

    @FXML private Label lb_trade_meplayer_mid;
    @FXML private Label lb_trade_oplayer_mid;

    @FXML private Label lb_trade_meplayer_mr;
    @FXML private Label lb_trade_oplayer_mr;

    @FXML private Label lb_trade_meplayer_rr;
    @FXML private Label lb_trade_oplayer_rr;

    private Map<String,Integer> bankResources(){
        Map<String, Integer> map = new HashMap<>();
        map.put("ore", 999);
        map.put("brick", 999);
        map.put("sheep", 999);
        map.put("wood", 999);
        map.put("wheat", 999);

        return map;

    }

    @FXML
    private void increaseResource(ActionEvent event) {

        Map<String,Integer> player_resources = gameEngine.getCurrentPlayer().getResources();

        Map<String,Integer> other_resources = null;
        switch (currentTradeMode) {
            case P1:
                other_resources = gameEngine.getNextPlayer(1).getResources();
                break;
            case P2:
                other_resources = gameEngine.getNextPlayer(2).getResources();
                break;
            case P3:
                other_resources = gameEngine.getNextPlayer(3).getResources();
                break;
            case BANK:
                other_resources = bankResources();
                break;
            default:
                throw new AssertionError();
        }

        Button source = (Button) event.getSource();

        if (source.getId().contains("meplayer_ll_up")) {
            int currentValue = Integer.parseInt(lb_trade_meplayer_ll.getText());
            int available = player_resources.getOrDefault("ore", 0);

            if (currentValue < available) {
                updateResourceLabel(lb_trade_meplayer_ll, 1);
            }

        } else if (source.getId().contains("meplayer_ll_down")) {
            updateResourceLabel(lb_trade_meplayer_ll, -1);
        }

        else if (source.getId().contains("oplayer_ll_up")) {
            int currentValue = Integer.parseInt(lb_trade_oplayer_ll.getText());
            int available = other_resources.getOrDefault("ore", 0);

            if (currentValue < available) {
                updateResourceLabel(lb_trade_oplayer_ll, 1);
            }
        } else if (source.getId().contains("oplayer_ll_down")) {
            updateResourceLabel(lb_trade_oplayer_ll, -1);
        }


        if (source.getId().contains("meplayer_ml_up")) {
            int currentValue = Integer.parseInt(lb_trade_meplayer_ml.getText());
            int available = player_resources.getOrDefault("brick", 0);

            if (currentValue < available) {
                updateResourceLabel(lb_trade_meplayer_ml, 1);
            }

        } else if (source.getId().contains("meplayer_ml_down")) {
            updateResourceLabel(lb_trade_meplayer_ml, -1);
        }

        else if (source.getId().contains("oplayer_ml_up")) {
            int currentValue = Integer.parseInt(lb_trade_oplayer_ml.getText());
            int available = other_resources.getOrDefault("brick", 0);

            if (currentValue < available) {
                updateResourceLabel(lb_trade_oplayer_ml, 1);
            }
        } else if (source.getId().contains("oplayer_ml_down")) {
            updateResourceLabel(lb_trade_oplayer_ml, -1);
        }



        if (source.getId().contains("meplayer_mid_up")) {
            int currentValue = Integer.parseInt(lb_trade_meplayer_mid.getText());
            int available = player_resources.getOrDefault("sheep", 0);

            if (currentValue < available) {
                updateResourceLabel(lb_trade_meplayer_mid, 1);
            }

        } else if (source.getId().contains("meplayer_mid_down")) {
            updateResourceLabel(lb_trade_meplayer_mid, -1);
        }

        else if (source.getId().contains("oplayer_mid_up")) {
            int currentValue = Integer.parseInt(lb_trade_oplayer_mid.getText());
            int available = other_resources.getOrDefault("sheep", 0);

            if (currentValue < available) {
                updateResourceLabel(lb_trade_oplayer_mid, 1);
            }
        } else if (source.getId().contains("oplayer_mid_down")) {
            updateResourceLabel(lb_trade_oplayer_mid, -1);
        }



        if (source.getId().contains("meplayer_mr_up")) {
            int currentValue = Integer.parseInt(lb_trade_meplayer_mr.getText());
            int available = player_resources.getOrDefault("wheat", 0);

            if (currentValue < available) {
                updateResourceLabel(lb_trade_meplayer_mr, 1);
            }

        } else if (source.getId().contains("meplayer_mr_down")) {
            updateResourceLabel(lb_trade_meplayer_mr, -1);
        }

        else if (source.getId().contains("oplayer_mr_up")) {
            int currentValue = Integer.parseInt(lb_trade_oplayer_mr.getText());
            int available = other_resources.getOrDefault("wheat", 0);

            if (currentValue < available) {
                updateResourceLabel(lb_trade_oplayer_mr, 1);
            }
        } else if (source.getId().contains("oplayer_mr_down")) {
            updateResourceLabel(lb_trade_oplayer_mr, -1);
        }



        if (source.getId().contains("meplayer_rr_up")) {
            int currentValue = Integer.parseInt(lb_trade_meplayer_rr.getText());
            int available = player_resources.getOrDefault("wood", 0);

            if (currentValue < available) {
                updateResourceLabel(lb_trade_meplayer_rr, 1);
            }

        } else if (source.getId().contains("meplayer_rr_down")) {
            updateResourceLabel(lb_trade_meplayer_rr, -1);
        }

        else if (source.getId().contains("oplayer_rr_up")) {
            int currentValue = Integer.parseInt(lb_trade_oplayer_rr.getText());
            int available = other_resources.getOrDefault("ore", 0);

            if (currentValue < available) {
                updateResourceLabel(lb_trade_oplayer_rr, 1);
            }
        } else if (source.getId().contains("oplayer_rr_down")) {
            updateResourceLabel(lb_trade_oplayer_rr, -1);
        }
    }


    private void updateResourceLabel(Label label, int delta) {
        int current = Integer.parseInt(label.getText());
        int updated = Math.max(0, current + delta); // Keine negativen Werte
        label.setText(String.valueOf(updated));
    }

    @FXML
    private Pane pane_meplayer_resources;

    @FXML
    private Label lb_meplayer_resources1;

    @FXML
    private Label lb_meplayer_resources2;

    @FXML
    private Label lb_meplayer_resources3;

    @FXML
    private Label lb_meplayer_resources4;

    @FXML
    private Label lb_meplayer_resources5;

    @FXML
    private Label lb_pleft_resources1;
    @FXML
    private Label lb_pleft_resources2;
    @FXML
    private Label lb_pleft_resources3;
    @FXML
    private Label lb_pleft_resources4;
    @FXML
    private Label lb_pleft_resources5;

    @FXML
    private Label lb_pmid_resources1;
    @FXML
    private Label lb_pmid_resources2;
    @FXML
    private Label lb_pmid_resources3;
    @FXML
    private Label lb_pmid_resources4;
    @FXML
    private Label lb_pmid_resources5;

    @FXML
    private Label lb_pright_resources1;
    @FXML
    private Label lb_pright_resources2;
    @FXML
    private Label lb_pright_resources3;
    @FXML
    private Label lb_pright_resources4;
    @FXML
    private Label lb_pright_resources5;

    @FXML
    private Button btn_toolbar_ll;
    @FXML
    private Button btn_toolbar_ml;
    @FXML
    private Button btn_toolbar_mr;

    private void trade(){
        Player player = gameEngine.getCurrentPlayer();
        resourceList.getItems().clear();
        for (Map.Entry<String, Integer> entry : player.getResources().entrySet()) {

            switch (entry.getKey()) {
                case "ore" -> lb_meplayer_resources1.setText(entry.getValue().toString());
                case "brick" -> lb_meplayer_resources2.setText(entry.getValue().toString());
                case "sheep" -> lb_meplayer_resources3.setText(entry.getValue().toString());
                case "wheat" -> lb_meplayer_resources4.setText(entry.getValue().toString());
                case "wood" -> lb_meplayer_resources5.setText(entry.getValue().toString());

                default ->
                    throw new AssertionError();
            }
        }

        for (int i = 1; i <= 3; i++){
            Player other = gameEngine.getNextPlayer(i);
            String name = other.getName();

            resourceList.getItems().clear();
            switch (i) {
                case 1 -> {
                    btn_toolbar_ll.setText(name);
                    for (Map.Entry<String, Integer> entry : other.getResources().entrySet()) {

                        switch (entry.getKey()) {
                            case "ore" -> lb_pleft_resources1.setText(entry.getValue().toString());
                            case "brick" -> lb_pleft_resources2.setText(entry.getValue().toString());
                            case "sheep" -> lb_pleft_resources3.setText(entry.getValue().toString());
                            case "wheat" -> lb_pleft_resources4.setText(entry.getValue().toString());
                            case "wood" -> lb_pleft_resources5.setText(entry.getValue().toString());

                            default ->
                                throw new AssertionError();
                        }
                    }
                }
                case 2 -> {
                    btn_toolbar_ml.setText(name);
                    for (Map.Entry<String, Integer> entry : other.getResources().entrySet()) {

                        switch (entry.getKey()) {
                            case "ore" -> lb_pmid_resources1.setText(entry.getValue().toString());
                            case "brick" -> lb_pmid_resources2.setText(entry.getValue().toString());
                            case "sheep" -> lb_pmid_resources3.setText(entry.getValue().toString());
                            case "wheat" -> lb_pmid_resources4.setText(entry.getValue().toString());
                            case "wood" -> lb_pmid_resources5.setText(entry.getValue().toString());

                            default ->
                                throw new AssertionError();
                        }
                    }
                }
                case 3 -> {
                    btn_toolbar_mr.setText(name);
                    for (Map.Entry<String, Integer> entry : other.getResources().entrySet()) {

                        switch (entry.getKey()) {
                            case "ore" -> lb_pright_resources1.setText(entry.getValue().toString());
                            case "brick" -> lb_pright_resources2.setText(entry.getValue().toString());
                            case "sheep" -> lb_pright_resources3.setText(entry.getValue().toString());
                            case "wheat" -> lb_pright_resources4.setText(entry.getValue().toString());
                            case "wood" -> lb_pright_resources5.setText(entry.getValue().toString());

                            default ->
                                throw new AssertionError();
                        }
                    }
                }

                default ->
                    throw new AssertionError();
            }
        }
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
        pane_tradingwindow.setVisible(false);

        switch (mode) {
            case ROAD -> streetPane.setVisible(true);
            case VILLAGE, CITY -> buildingPane.setVisible(true);
            case TRADE -> pane_tradingwindow.setVisible(true);
            default -> {
                // NONE or any other case: both panes remain invisible
            }
        }
    }

    @FXML
    private void handleBuildBuilding(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String id = clickedButton.getId(); // z. B. "buildingNode17"
        String playerName = gameEngine.getCurrentPlayer().getName();

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

                    placeBuildingImage(x, y, "VILLAGE", playerName);
                    //clickedButton.setVisible(false);
                }
            }
            
            else{
                if (currentBuildMode == BuildMode.CITY){
                    boolean success = gameEngine.tryBuildCity(nodeIndex, gameEngine.getCurrentPlayer());
                    if (success) {
                        double x = clickedButton.getLayoutX();
                        double y = clickedButton.getLayoutY();

                        placeBuildingImage(x, y, "CITY", playerName);
                        clickedButton.setVisible(false);
                    }
                }

            }
        }
        if (gameEngine.getCurrentPlayer().getVictoryPoints() >= 10){
            showVictoryScreen(gameEngine.getCurrentPlayer().getName());
        }
    }
    private final Map<Point2D, ImageView> imagesByPosition = new HashMap<>();

private String imagePath(String name, String type) {
    String imagePath;

    switch (type) {
        case "VILLAGE" -> {
            switch (name) {
                case "Rot" -> imagePath = "/catan/game/images/Dorf.png";
                case "Blau"    -> imagePath = "/catan/game/images/BlueSettlement.png";
                case "Gruen"    -> imagePath = "/catan/game/images/GreenSettlement.png";
                case "Gelb"    -> imagePath = "/catan/game/images/YellowSettlement.png";
                default        -> throw new IllegalArgumentException("Unbekannter BuildingType: " + type);
            }
        }
        case "CITY"    -> {
            switch (name) {
                case "Rot" -> imagePath = "/catan/game/images/Stadt.png";
                case "Blau"    -> imagePath = "/catan/game/images/BlueCity.png";
                case "Gruen"    -> imagePath = "/catan/game/images/GreenCity.png";
                case "Gelb"    -> imagePath = "/catan/game/images/YellowCity.png";
                default        -> throw new IllegalArgumentException("Unbekannter BuildingType: " + type);
            }
        }
        default        -> throw new IllegalArgumentException("Unbekannter BuildingType: " + type);
    }

    return imagePath;
}

private void placeBuildingImage(double x, double y, String type, String name) {
    String imagePath = imagePath(name, type);

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

                double rotation = clickedButton.getRotate();

                if (Math.abs(rotation) < 5.0) {
                            placeRoadImage(x, y, "horizontal");
                        } else if (rotation > 0) {
                            placeRoadImage(x, y, "upLeft");
                        } else {
                            placeRoadImage(x, y, "upRight");
                        }

                clickedButton.setVisible(false);

            }
        }
    }

    private String roadImagePath(String name, String direction) {
        String imagePath;
        switch (direction) {
            case "horizontal" -> {
                switch (name) {
                    case "Rot" -> imagePath = "/catan/game/images/Strasse_rot.png";
                    case "Blau"     -> imagePath = "/catan/game/images/Strasse_blau.png";
                    case "Gruen"    -> imagePath = "/catan/game/images/Strasse_gruen.png";
                    case "Gelb"    -> imagePath = "/catan/game/images/Strasse_gelb.png";
                    default           -> imagePath = "/catan/game/images/Strasse_rot.png";
                }
            }
            case "upLeft"     -> {
                switch (name) {
                    case "Rot" -> imagePath = "/catan/game/images/Strasse_rot_links.png";
                    case "Blau"     -> imagePath = "/catan/game/images/Strasse_blau_links.png";
                    case "Gruen"    -> imagePath = "/catan/game/images/Strasse_gruen_links.png";
                    case "Gelb"    -> imagePath = "/catan/game/images/Strasse_gelb_links.png";
                    default           -> imagePath = "/catan/game/images/Strasse_rot_links.png";
                }
            }
            case "upRight"    -> {
                switch (name) {
                    case "Rot" -> imagePath = "/catan/game/images/Strasse_rot_rechts.png";
                    case "Blau"     -> imagePath = "/catan/game/images/Strasse_blau_rechts.png";
                    case "Gruen"    -> imagePath = "/catan/game/images/Strasse_gruen_rechts.png";
                    case "Gelb"    -> imagePath = "/catan/game/images/Strasse_gelb_rechts.png";
                    default           -> imagePath = "/catan/game/images/Strasse_rot_rechts.png";
                }
            }
            default           -> imagePath = "/catan/game/images/road_horizontal.png";
        }

        

        return imagePath;
    }


    private void placeRoadImage(double x, double y, String direction) {

        String name = gameEngine.getCurrentPlayer().getName();
        String imagePath = roadImagePath(name, direction);

        ImageView roadImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));

        switch (direction) {
            case "horizontal" -> {
                roadImage.setFitWidth(40);  // Passe an deine Grafik an
                roadImage.setFitHeight(100);
                roadImage.setLayoutX(x + 10);
                roadImage.setLayoutY(y - 35);

                boardPane.getChildren().add(roadImage);
            }
            case "upLeft" -> {
                roadImage.setFitWidth(60);  // Passe an deine Grafik an
                roadImage.setFitHeight(35);
                roadImage.setLayoutX(x);
                roadImage.setLayoutY(y);

                boardPane.getChildren().add(roadImage);
            }
            case "upRight" -> {
                roadImage.setFitWidth(60);  // Passe an deine Grafik an
                roadImage.setFitHeight(35);
                roadImage.setLayoutX(x - 10);
                roadImage.setLayoutY(y - 12);

                boardPane.getChildren().add(roadImage);
            }
            default ->
                throw new AssertionError();
        }
    }

    private int turn = 0;


    @FXML
    private void handleEndTurn(ActionEvent event) {
        
        System.out.println("Zug beenden gedrückt!");
        gameEngine.nextTurn();

        updateResourceDisplay(gameEngine.getCurrentPlayer());
        Player currentPlayer = gameEngine.getCurrentPlayer();
        currentPlayerLabel.setText(currentPlayer.getName());
        currentBuildMode = BuildMode.NONE;
        highlightBuildMode(currentBuildMode);
        pane_trading_desicions.setVisible(false);
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

    public void showVictoryScreen(String winnerName) {
        gameWin.setVisible(true);
        playerName.setText(winnerName);
        System.out.println("🏆 " + winnerName + " hat gewonnen!");
    }

    @FXML
    private void handleExitGame() {
        Platform.exit(); // Beendet die JavaFX-App
    }

    @FXML
    private void commitTrade() {
        int oreFromMe = Integer.parseInt(lb_trade_meplayer_ll.getText());
        int oreFromOther = Integer.parseInt(lb_trade_oplayer_ll.getText());
        
        int brickFromMe = Integer.parseInt(lb_trade_meplayer_ml.getText());
        int brickFromOther = Integer.parseInt(lb_trade_oplayer_ml.getText());

        int sheepFromMe = Integer.parseInt(lb_trade_meplayer_mid.getText());
        int sheepFromOther = Integer.parseInt(lb_trade_oplayer_mid.getText());

        int wheatFromMe = Integer.parseInt(lb_trade_meplayer_mr.getText());
        int wheatFromOther = Integer.parseInt(lb_trade_oplayer_mr.getText());

        int woodFromMe = Integer.parseInt(lb_trade_meplayer_rr.getText());
        int woodFromOther = Integer.parseInt(lb_trade_oplayer_rr.getText());

        if (null != currentTradeMode) // Beispielhafte Logik (abhängig von deinem Player-System):
        switch (currentTradeMode) {
            case P1 -> {
                Player other = gameEngine.getNextPlayer(1);
                tradeResources(gameEngine.getCurrentPlayer(), other, oreFromMe, oreFromOther, "ore");
                tradeResources(gameEngine.getCurrentPlayer(), other, brickFromMe, brickFromOther, "brick");
                tradeResources(gameEngine.getCurrentPlayer(), other, sheepFromMe, sheepFromOther, "sheep");
                tradeResources(gameEngine.getCurrentPlayer(), other, wheatFromMe, wheatFromOther, "wheat");
                tradeResources(gameEngine.getCurrentPlayer(), other, woodFromMe, woodFromOther, "wood");
            }
            case P2 -> {
                Player other = gameEngine.getNextPlayer(2);
                tradeResources(gameEngine.getCurrentPlayer(), other, oreFromMe, oreFromOther, "ore");
                tradeResources(gameEngine.getCurrentPlayer(), other, brickFromMe, brickFromOther, "brick");
                tradeResources(gameEngine.getCurrentPlayer(), other, sheepFromMe, sheepFromOther, "sheep");
                tradeResources(gameEngine.getCurrentPlayer(), other, wheatFromMe, wheatFromOther, "wheat");
                tradeResources(gameEngine.getCurrentPlayer(), other, woodFromMe, woodFromOther, "wood");
            }
            case P3 -> {
                Player other = gameEngine.getNextPlayer(3);
                tradeResources(gameEngine.getCurrentPlayer(), other, oreFromMe, oreFromOther, "ore");
                tradeResources(gameEngine.getCurrentPlayer(), other, brickFromMe, brickFromOther, "brick");
                tradeResources(gameEngine.getCurrentPlayer(), other, sheepFromMe, sheepFromOther, "sheep");
                tradeResources(gameEngine.getCurrentPlayer(), other, wheatFromMe, wheatFromOther, "wheat");
                tradeResources(gameEngine.getCurrentPlayer(), other, woodFromMe, woodFromOther, "wood");
            }
            default -> {
                // Do nothing
            }
        }
    }

    private void tradeResources(Player me, Player other, int oreFromMe, int oreFromOther ,String resource) {
        // Beispiel: Erz vom Spieler an anderen
        for (int i = 0; i < oreFromMe; i++) me.removeResource(resource, 1);
        for (int i = 0; i < oreFromOther; i++) other.removeResource(resource, 1);

        for (int i = 0; i < oreFromMe; i++) other.addResource(resource, 1);
        for (int i = 0; i < oreFromOther; i++) me.addResource(resource, 1);

        // analog für alle weiteren Ressourcen
    }

}
