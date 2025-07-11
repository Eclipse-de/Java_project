package catan.game.building;

import catan.game.gameboard.node;
import catan.game.main.Player;

public abstract class building {
    protected Player owner;
    protected final node location;

    public void setOwner(Player owner) {
        this.owner = owner;
    }


    public building(final Player owner, final node location) {
        this.owner = owner;
        this.location = location;
    }

    public Player getOwner() {
        return owner;
    }

    public node getLocation() {
        return location;
    }

    public abstract String getType();
}
