package catan.game.gameboard;

import java.util.ArrayList;
import java.util.List;

public class board {
    private List<tile> tiles;
    private List<node> nodes;
    private List<edge> edges;
    private List<String> resources;

    public board() {
        // Platzhalter, später kann man hier die Spiellogik aufbauen
        this.tiles = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void setTiles(List<tile> tiles) {
        this.tiles = tiles;
    }

    public void setNodes(List<node> nodes) {
        this.nodes = nodes;
    }


    public board(final List<tile> tiles, final List<node> nodes, final List<edge> edges) {
        this.tiles = tiles;
        this.nodes = nodes;
        this.edges = edges;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
        System.out.println("Ressourcen gesetzt im Board: " + resources);
    }

    public List<tile> getTiles() {
        return tiles;
    }

    public List<node> getNodes() {
        return nodes;
    }

    public List<edge> getEdges() {
        return edges;
    }
}
