package catan.game.building;

import catan.game.gameboard.node;
import catan.game.main.Player;

public class city extends building {
    public city(final Player owner, final node location) {
        super(owner, location);
    }

    @Override
    public String getType() {
        return "city";
    }
}
