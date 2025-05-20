package catan.game.building;

import catan.game.gameboard.node;
import catan.game.main.player;

public class settlement extends building {
    public settlement(player owner, node location) {
        super(owner, location);
    }

    @Override
    public String getType() {
        return "settlement";
    }
}
