package catan.game.gameboard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import catan.game.main.Player;
import javafx.scene.image.ImageView;

public class node {
    private double x, y; // für Klickerkennung (GUI)
    private int index;
    private Player owner;

    private final Map<node, ImageView> buildingImages = new HashMap<>();

    private List<tile> adjacentTiles = new ArrayList<>();

    public void addTile(tile t) {
        adjacentTiles.add(t);
    }

    public List<tile> getAdjacentTiles() {
        return adjacentTiles;
    }

    public node(int i) {
        this.index = i;
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public Player getOwner() { return owner; }
    public void setOwner(Player p) { this.owner = p; }
    public boolean hasOwner() { return owner != null; }
    
    private boolean city = false;

    public boolean isCity() {
        return city;
    }

    public void setCity(boolean city) {
        this.city = city;
    }

    public int getResourceMultiplier() {
        return city ? 2 : 1;
    }
}
