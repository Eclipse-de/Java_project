package catan.game.gameboard;

public class tile {
    private final String resourceType;
    private final int numberToken;

    public tile(final String resourceType, final int numberToken) {
        this.resourceType = resourceType;
        this.numberToken = numberToken;
    }

    public String getResourceType() {
        return resourceType;
    }

    public int getNumberToken() {
        return numberToken;
    }
}
