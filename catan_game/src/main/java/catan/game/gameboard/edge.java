package catan.game.gameboard;

public class edge {
    private final node nodeA;
    private final node nodeB;

    public edge(final node nodeA, final node nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public node getNodeA() {
        return nodeA;
    }

    public node getNodeB() {
        return nodeB;
    }
}
