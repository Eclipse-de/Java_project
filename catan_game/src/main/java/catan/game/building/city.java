package catan.game.building;

import catan.game.gameboard.node;
import catan.game.main.player;

public class city extends building {
    public city(player owner, node location) {
        super(owner, location);
    }

    @Override
    public String getType() {
        return "city";
    }
}
