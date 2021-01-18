package RiskGame.Logic;

import java.awt.*;
import java.util.List;

public class Continent {
    private final String name;
    private List<Land> lands;
    private final int awardingSoldiers;

    public Continent(String name, int awardingSoldiers, List<Land> lands) {
        this.name = name;
        this.awardingSoldiers = awardingSoldiers;
        this.lands = lands;
    }

    //option
    //add and remove land for select map
    public void addLand(List<Land> lands) {
        for (Land land : lands) {
            this.lands.add(land);
        }
    }
    public void addLand(Land land) {
        lands.add(land);
    }
    public void removeLand(Land land) {
        lands.remove(land);
    }

    //get the lands of the continent
    public List<Land> getLands() {
        return lands;
    }

    //return the name of continent
    public String getName() {
        return name;
    }

    public int getAwardingSoldiers() {
        return awardingSoldiers;
    }
}
