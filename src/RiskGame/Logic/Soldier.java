package RiskGame.Logic;

import java.awt.*;

public class Soldier {

    private static int totalNumOfSoldiers = 0;
    private int id;
    private Player commander;
    private StateSoldier stateSoldier;
    private Color color;
    private boolean isKilled;

    public Soldier(Player commander) {
        id = totalNumOfSoldiers++;
        this.commander = commander;
        this.color = commander.getColor();
        stateSoldier = StateSoldier.UNPLACED;
    }

    //return the commander of this soldier
    public Player getCommander() {
        return commander;
    }

    public int getId() {
        return id;
    }

    public void setStateSoldier(StateSoldier stateSoldier) {
        this.stateSoldier = stateSoldier;
    }
    public StateSoldier getStateSoldier() {
        return stateSoldier;
    }


    public boolean isKilled() {
        return isKilled;
    }

    public static int getTotalNumOfSoldiers() {
        return totalNumOfSoldiers+1;
    }
}

//the state property could be boolean data type.but now is a enum class.
enum StateSoldier{
    UNPLACED, PLACED
}