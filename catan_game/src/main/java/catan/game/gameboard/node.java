package catan.game.gameboard;
import catan.game.main.Player;

public class node {
    private double x, y; // für Klickerkennung (GUI)
    private int index;
    private Player owner;

    public node(int i) {
        this.index = i;
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public Player getOwner() { return owner; }
    public void setOwner(Player p) { this.owner = p; }
    
}
