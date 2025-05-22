package catan.game.building;

import catan.game.gameboard.node;
import catan.game.main.player;

public class road extends building {
    public road(final player owner, final node location) {
        super(owner, location);
    }

    @Override
    public String getType() {
        return "road";
    }
}
