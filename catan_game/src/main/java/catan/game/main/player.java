package catan.game.main;

public class player {
    private String name;
    private int id;

    public player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + " (Player " + id + ")";
    }
}
