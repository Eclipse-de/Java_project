package catan.game.gameboard;

public class edge {
    private node nodeA;
    private node nodeB;

    public edge(node nodeA, node nodeB) {
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
