package catan.game.building;

import catan.game.gameboard.node;
import catan.game.main.player;

public abstract class building {
    protected final player owner;
    protected final node location;

    public building(final player owner, final node location) {
        this.owner = owner;
        this.location = location;
    }

    public player getOwner() {
        return owner;
    }

    public node getLocation() {
        return location;
    }

    public abstract String getType();
}
