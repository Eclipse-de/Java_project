package catan.game.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import catan.game.gameboard.board;
import catan.game.gameboard.node;
import catan.game.gameboard.tile;

public class GameEngine {

    private final List<Player> players;
    private int currentPlayerIndex = 0;
    private board board;
    private List<String> resources;

    public GameEngine() {
        this.players = new ArrayList<>();
        players.add(new Player("Rot"));
        players.add(new Player("Blau"));
        players.add(new Player("Gruen"));
        players.add(new Player("Gelb"));
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }


    public void startGame() {
        if (resources == null) {
            System.err.println("⚠ Ressourcenliste fehlt in GameEngine!");
            return;
        }

        this.board = new board();
        board.setNodes(nodes);

        List<tile> tiles = new ArrayList<>();

        // Zahlentoken vorbereiten
        List<Integer> numberTokens = new ArrayList<>(List.of(
            5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11
        ));
        int tokenIndex = 0;

        for (String res : resources) {
            if (res.equals("Wüste")) {
                tiles.add(new tile("Wüste", 0));
            } else {
                int token = numberTokens.get(tokenIndex++);
                tiles.add(new tile(res, token));
            }
        }
        board.setTiles(tiles);
        board.setResources(resources);

        // Jetzt Tile-zu-Node-Zuordnung
            int[][] tileToNodeMapping = {
                {0, 1, 2, 18, 17, 16},      // Tile 0
                {2, 3, 4, 16, 14, 13},    // Tile 1
                {4, 5, 6, 13, 12, 7},   // Tile 2
                {19, 18, 17, 20, 21, 22},   // Tile 3
                {17, 16, 14, 22, 23, 15}, // Tile 4
                {14, 13, 12, 15, 24, 11},
                {12, 7, 8, 11, 10, 9},
                {53, 20, 21, 52, 50, 51},
                {21, 22, 23, 51, 41, 40},
                {23, 15, 24, 40, 39, 33},
                {24, 11, 10, 33, 34, 35},
                {10, 9, 25, 35, 27, 26},
                {50, 51, 41, 49, 48, 43},
                {41, 40, 39, 43, 42, 38},
                {39, 33, 34, 38, 32, 36},
                {34, 35, 27, 26, 38, 27},
                {48, 42, 43, 47, 46, 44},
                {42, 38, 32, 44, 45, 31},
                {32, 26, 38, 31, 30, 29}  // Tile 18
            };

        for (int tileIndex = 0; tileIndex < tileToNodeMapping.length; tileIndex++) {
            tile t = tiles.get(tileIndex);
            int[] nodeIndices = tileToNodeMapping[tileIndex];

            for (int nodeIndex : nodeIndices) {
                node n = nodes.get(nodeIndex);
                n.addTile(t);
            }
        }

    }

    public board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getNextPlayer(int next) {
        int size = players.size();
        int nextIndex = (currentPlayerIndex + next) % size;
        return players.get(nextIndex);
    }


    public int rollDice() {
        int die1 = (int)(Math.random() * 6) + 1;
        int die2 = (int)(Math.random() * 6) + 1;
        return die1 + die2;
    }

    public void distributeResources(int diceRoll) {
        for (node n : board.getNodes()) {
            if (n.getOwner() != null) {
                for (tile t : n.getAdjacentTiles()) {
                    if (t.getNumberToken() == diceRoll) {
                        String res = t.getResourceType();
                        int amount = n.isCity() ? 2 : 1;
                        n.getOwner().addResource(res, amount);
                    }
                }
            }
        }
    }

    private int turn = 0;

    public void nextTurn() {

        if (turn < 7){
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            turn += 1;
        }
        else {
            startPhase = false;
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            Player spieler = getCurrentPlayer();

            int diceRoll = rollDice();
            System.out.println("Gewürfelt: " + diceRoll);

            distributeResources(diceRoll);
        }
    }

    public boolean tryBuildRoad(int edgeIndex, Player player) {

        if (startPhase) {
            return true;
        }
        else {
            Map<String, Integer> resourcen = player.getResources();

            // Prüfen, ob genug Ressourcen vorhanden sind
            if (resourcen.getOrDefault("brick", 0) >= 1 && resourcen.getOrDefault("wood", 0) >= 1) {
                System.out.println("genug");

                // Ressourcen abziehen
                resourcen.put("brick", resourcen.get("brick") - 1);
                resourcen.put("wood", resourcen.get("wood") - 1);

                return true;
            } else {
                System.out.println("nicht Genug");
                return false;
            }
        }
    }

    private List<node> nodes = new ArrayList<>();

    public void initGameBoard() {
        for (int i = 0; i < 54; i++) {
            nodes.add(new node(i)); // oder dein eigener Knoten-Typ
        }
    }

    public boolean  tryBuildVillage(int nodeIndex, Player player) {

        if (startPhase) {

            node n = board.getNodes().get(nodeIndex);

            if (n.getOwner() == null) {
                n.setOwner(player);
                buildSettlement(n);
                return true;
            }
            return true;
        }
        else {
            Player spieler = player;
            node n = board.getNodes().get(nodeIndex);

            Map<String,Integer> resourcen =spieler.getResources();
            if (n.getOwner() == null) {
                if (resourcen.getOrDefault("brick", 0) >= 1 && resourcen.getOrDefault("wood", 0) >= 1&& resourcen.getOrDefault("sheep", 0) >= 1&& resourcen.getOrDefault("wheat", 0) >= 1) {
                    System.out.println("genug");
                    n.setOwner(player);
                    buildSettlement(n);
                    return true;
                }
                else{
                    System.out.println("nicht Genug");
                    return false;
                }
            } else {
                System.out.println("Hier steht schon ein Dorf!");
                return false;
            }
        }
    }

    public boolean tryBuildCity(int nodeIndex, Player player) {
        node n = board.getNodes().get(nodeIndex);

        if (n.getOwner() != null && n.getOwner().equals(player)) {
            Map<String, Integer> resourcen = player.getResources();

            if (resourcen.getOrDefault("wheat", 0) >= 2 && resourcen.getOrDefault("ore", 0) >= 3) {

                buildCity(n, player);
                return true;
            } else {
                System.out.println("Nicht genug Ressourcen für eine Stadt!");
                return false;
            }
        } else {
            System.out.println("Du kannst hier keine Stadt bauen (kein eigenes Dorf)!");
            return false;
        }
    }

    public void buildCity(node location, Player player) {
        player.buildCity(); // z.B. Anzahl Städte +1 setzen
        location.setCity(true); // falls du so ein Flag hast
        checkVictory(player);
        System.out.println(player.getName() + " hat eine Stadt gebaut bei " + location);
    }


    public void buildSettlement(node location) {
        Player current = getCurrentPlayer();
        if (startPhase){
            location.setOwner(current);
            current.addResource("wood", 1);
            current.addResource("brick", 1);
            current.addResource("sheep", 1);
            current.addResource("wheat", 1);
            current.buildVillage();
        }
        else {
            if (current.canBuildSettlement()) {
                location.setOwner(current);
                current.buildVillage();
                checkVictory(current);
                System.out.println(current.getName() + " hat ein Dorf gebaut bei " + location);
            } else {
                System.out.println(current.getName() + " hat nicht genug Ressourcen für ein Dorf.");
            }
        }
    }

    // public void buildCity(node location) {
    //     Player current = getCurrentPlayer();
    //     if (location.getOwner() == current && !location.isCity()) {
    //         if (current.hasEnoughResourcesForCity()) {
    //             location.setCity(true);
    //             current.useResourcesForCity();
    //             System.out.println(current.getName() + " hat eine Stadt gebaut bei " + location);
    //         } else {
    //             System.out.println(current.getName() + " hat nicht genug Ressourcen für eine Stadt.");
    //         }
    //     } else {
    //         System.out.println("Ungültiger Stadtbauversuch bei " + location);
    //     }
    // }
//-------------------------------------------------------------------------------------------------------------------------------

    private boolean startPhase = true;

    private boolean gameOver = false;

    public boolean isGameOver() {
        return gameOver;
    }

    public void checkVictory(Player player) {
        if (player.getVictoryPoints() >= 10) {
            System.out.println("🏆 " + player.getName() + " hat das Spiel gewonnen!");
            gameOver = true;
            // Optional: GUI benachrichtigen
        }
    }


}
