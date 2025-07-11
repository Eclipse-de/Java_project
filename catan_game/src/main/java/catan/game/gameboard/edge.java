package catan.game.gameboard;

import  catan.game.main.Player ;

public class edge {
    private node nodeA;
    private node nodeB;
    private Player owner;

    public edge(node a, node b) {
        this.nodeA = a;
        this.nodeB = b;
    }

    public boolean isConnectedTo(node n) {
        return nodeA == n || nodeB == n;
    }

    public Player getOwner() { return owner; }
    public void setOwner(Player p) { this.owner = p; }

    public node getNodeA() { return nodeA; }
    public node getNodeB() { return nodeB; }
}
