package catan.game.main;

public class player {
    private final String name;
    private final int id;

    public player(final int id, final String name) {
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
