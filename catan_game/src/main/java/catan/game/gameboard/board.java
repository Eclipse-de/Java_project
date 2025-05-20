package catan.game.gameboard;

import java.util.List;

public class board {
    private List<tile> tiles;
    private List<node> nodes;
    private List<edge> edges;

    public board(List<tile> tiles, List<node> nodes, List<edge> edges) {
        this.tiles = tiles;
        this.nodes = nodes;
        this.edges = edges;
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
