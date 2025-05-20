package catan.game.building;

import catan.game.gameboard.node;
import catan.game.main.player;

public abstract class building {
    protected player owner;
    protected node location;

    public building(player owner, node location) {
        this.owner = owner;
        this.location = location;
    }

    public player getOwner() { return owner; }
    public node getLocation() { return location; }
    public abstract String getType();
}
