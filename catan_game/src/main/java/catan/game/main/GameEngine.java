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
        players.add(new Player("Spieler 1"));
        players.add(new Player("Spieler 2"));
        players.add(new Player("Spieler 3"));
        players.add(new Player("Spieler 4"));
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

        List<tile> tiles = new ArrayList<>();
        board.setNodes(nodes);

        // Zahlentokens für 19 Felder (ohne Wüste). Verteilung ist wie beim echten Catan.
        List<Integer> numberTokens = new ArrayList<>(List.of(
            5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11
        ));

        int tokenIndex = 0;

        for (String res : resources) {
            if (res.equals("Wüste")) {
                tiles.add(new tile("Wüste", 0));  // Kein Zahlentoken
            } else {
                int token = numberTokens.get(tokenIndex++);
                tiles.add(new tile(res, token));
            }
        }

        board.setTiles(tiles);
        board.setResources(resources);

        System.out.println("Tiles im Board:");
        for (tile t : tiles) {
            System.out.println(t.getResourceType() + " mit Token " + t.getNumberToken());
        }

    }

    public board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int rollDice() {
        int die1 = (int)(Math.random() * 6) + 1;
        int die2 = (int)(Math.random() * 6) + 1;
        return die1 + die2;
    }

    public void distributeResources(int diceNumber) {
        for (tile t : board.getTiles()) {
            if (t.getNumberToken() == diceNumber) {
                String res = t.getResourceType();

                System.out.println(res);

                for (node n : t.getNodes()) {
                    Player owner = n.getOwner();
                    if (owner != null) {
                        owner.addResource(res, 1);
                        System.out.println(owner.getName() + " bekommt 1x " + res);
                    }
                }
            }
        }
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        Player spieler = getCurrentPlayer();

        int diceRoll = rollDice();
        System.out.println("Gewürfelt: " + diceRoll);

        //distributeResources(diceRoll);
        spieler.addResource("wood", 1);
        spieler.addResource("brick", 1);
        spieler.addResource("sheep", 1);
        spieler.addResource("wheat", 1);
    }

    public boolean tryBuildRoad(int edgeIndex, Player player) {
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


    public void buildStreet(node from, node to) {
        // TODO: Implementieren, z.B. Edge zwischen zwei Nodes prüfen
        System.out.println("Straße gebaut von " + from + " nach " + to);
    }

    private List<node> nodes = new ArrayList<>();

    public void initGameBoard() {
        for (int i = 0; i < 53; i++) {
            nodes.add(new node(i)); // oder dein eigener Knoten-Typ
        }
    }

    public boolean  tryBuildVillage(int nodeIndex, Player player) {
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


    public void buildSettlement(node location) {
        Player current = getCurrentPlayer();
        if (current.canBuildSettlement()) {
            location.setOwner(current);
            current.buildVillage();
            System.out.println(current.getName() + " hat ein Dorf gebaut bei " + location);
        } else {
            System.out.println(current.getName() + " hat nicht genug Ressourcen für ein Dorf.");
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
}
