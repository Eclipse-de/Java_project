package catan.game.gameboard;

import javafx.geometry.Point2D;

public class tile {
    private final String resourceType;
    private final int numberToken;

    private node[] nodes = new node[6];
    private edge[] edges = new edge[6];

    public void setNode(int index, node n) {
        nodes[index] = n;
    }

    public node getNode(int index) {
        return nodes[index];
    }

    public node[] getNodes() {
        return nodes;
    }

    public edge[] getEdges() {
        return edges;
    }

    public void setEdge(int index, edge e) {
        edges[index] = e;
    }

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

    private Point2D[] hexCornerOffsets(double radius) {
        Point2D[] corners = new Point2D[6];
        for (int i = 0; i < 6; i++) {
            double angleDeg = 60 * i - 30;
            double angleRad = Math.toRadians(angleDeg);
            corners[i] = new Point2D(
                radius * Math.cos(angleRad),
                radius * Math.sin(angleRad)
            );
        }
        return corners;
    }

}
