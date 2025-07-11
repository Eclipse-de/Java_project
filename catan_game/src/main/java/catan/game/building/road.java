package catan.game.building;

import catan.game.gameboard.node;
import catan.game.main.Player;

public class road extends building {
    public road(final Player owner, final node location) {
        super(owner, location);
    }

    @Override
    public String getType() {
        return "road";
    }
}
