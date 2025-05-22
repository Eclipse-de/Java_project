package catan.game.gameboard;

public class node {
    private final int x;
    private final int y;

    public node(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
