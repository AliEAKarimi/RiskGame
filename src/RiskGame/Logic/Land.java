package RiskGame.Logic;

import java.util.ArrayList;
import java.util.List;

public class Land {

    private int ID;
    private String name;
    private List<Soldier> soldiers;
    private Player commander;
    private List<Land> abuttingLands;
    private boolean isEmpty = true;

    public Land(int ID, String name) {
        this.ID = ID;
        this.name = name;
        soldiers = new ArrayList<>();
        abuttingLands = new ArrayList<>();
    }

    //I think below method can implement in player class
    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
        soldier.setStateSoldier(StateSoldier.PLACED);
    }

    public void addSoldier(List<Soldier> soldiers) { //for time war that winner soldier must add to land that game over.
        for (Soldier soldier : soldiers) {
            addSoldier(soldier);
        }
    }

    public void removeSoldiers(List<Soldier> soldiers) {
        for (Soldier soldier : soldiers) {
            this.soldiers.remove(soldier);
        }
    }


    public int getNumOfSoldier() {
        return soldiers.size();
    }

    public String getName() {
        return name;
    }

    public void setOccupied(Player occupant) {
        commander = occupant;
        occupant.addLand(this);
        isEmpty = false;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Player getCommander() {
        return commander;
    }

    public void addAbuttingLand(Land land) {
        getAbuttingLands().add(land);
    }

    public void setAbuttingLands(List<Land> abuttingLands) {
        this.abuttingLands = abuttingLands;
    }

    public List<Land> getAbuttingLands() {
        return abuttingLands;
    }

    public int getID() {
        return ID;
    }

    public List<Soldier> getSoldiers() {
        return soldiers;
    }

}
