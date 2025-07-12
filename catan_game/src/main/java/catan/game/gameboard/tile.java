package catan.game.gameboard;

public class tile {
    private final String resourceType;
    private final int numberToken;

    private node[] nodes = new node[6];
    private edge[] edges = new edge[6];

    public void setNode(int index, node n) {
        nodes[index] = n;
    }

    public node getNode(int index) {
        return nodes[index];
    }

    public node[] getNodes() {
        return nodes;
    }

    public edge[] getEdges() {
        return edges;
    }

    public void setEdge(int index, edge e) {
        edges[index] = e;
    }

    public tile(final String resourceType, final int numberToken) {
        this.resourceType = resourceType;
        this.numberToken = numberToken;
    }

    public String getResourceType() {
        return resourceType;
    }

    public int getNumberToken() {
        return numberToken;
    }

}
