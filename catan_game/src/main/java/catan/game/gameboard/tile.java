package catan.game.gameboard;

public class tile {
    private String resourceType;
    private int numberToken;

    public tile(String resourceType, int numberToken) {
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
