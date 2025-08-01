package catan.game.gameboard;

import java.util.List;

public class board {
    private final List<tile> tiles;
    private final List<node> nodes;
    private final List<edge> edges;

    public board(final List<tile> tiles, final List<node> nodes, final List<edge> edges) {
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
