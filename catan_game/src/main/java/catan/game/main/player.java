package catan.game.main;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private final String name;
    private final HashMap<String, Integer> resources = new HashMap<>();
    private int victoryPoints;

    public Player(String name) {
        this.name = name;
        for (String r : new String[]{"wood", "brick", "sheep", "wheat", "ore"}) {
            resources.put(r, 0);
        }
        this.victoryPoints = 0;
    }

    public void addResource(String type, int amount) {
        if (!resources.containsKey(type)) {
            resources.put(type, 0);
        }
        resources.put(type, resources.get(type) + amount);
    }

    public Map<String, Integer> getResources() {
        return resources;
    }


    public boolean canBuildSettlement() {
        return resources.get("wood") >= 1 &&
               resources.get("brick") >= 1 &&
               resources.get("sheep") >= 1 &&
               resources.get("wheat") >= 1;
    }

    public void buildVillage() {
        resources.put("wood", resources.get("wood") - 1);
        resources.put("brick", resources.get("brick") - 1);
        resources.put("sheep", resources.get("sheep") - 1);
        resources.put("wheat", resources.get("wheat") - 1);

        victoryPoints += 1;
    }

    public void buildCity() {
        resources.put("wheat", resources.get("wheat") - 1);
        resources.put("ore", resources.get("ore") - 1);

        victoryPoints += 1;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public String getName() {
        return name;
    }
}
