package RiskGame.Logic;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private List<Land> lands;
    private List<Soldier> soldiers;
    private List<Soldier> unplacedSoldiers;
    private List<Continent> continents;
    private int numOfUnplacedSoldiers; //the number of unplaced soldiers.
    private int totalNumOfSoldiers;
    private boolean isHandout;
    private int numOfLands;
    private Color color; //method e set color.and get Color.repeated color must not choose.

    public Player(String name, Color color) {
        this.name = name;
        unplacedSoldiers = new ArrayList<>();
        lands = new ArrayList<>();
        soldiers = new ArrayList<>();
        continents = new ArrayList<>();
        isHandout = false;
        this.color = color;
    }
    //constructor
    public Player(String name, Color color, int numOfSoldiers) {
        this(name, color);
        addSoldiers(numOfSoldiers);
    }

    //add and remove land
    public void addLand(Land land) {
        lands.add(land);
    }
    public void removeLand(Land land) {
        lands.remove(land);
    }

    //get the list of player lands.
    public List<Land> getLands() {
        return lands;
    }

    //get the name of soldier
    public String getName() {
        return name;
    }

    //add and remove soldier
    //set and get num of soldiers
    public void addSoldiers(){
        soldiers.add(new Soldier(this));
    }
    public void addSoldiers(int numOfSoldiers) {
        for(int i = 0 ; i < numOfSoldiers ; i++)
            soldiers.add(new Soldier(this));
    }

    //player can add an unplaced soldier to a land.
    public void putSoldier(Land land) {
        if(land.isEmpty())
            land.setOccupied(this);

        //if(land.getCommander() == this)
        land.addSoldier(this.getUnplacedSoldiers().get(0));
        //this.unplacedSoldiers.remove(0); //this soldier else wont exist in unplaced soldiers.
        //else System.out.println("");
    }

    //get the number of unplaced soldiers
    public int getNumOfUnplacedSoldiers() {
        return numOfUnplacedSoldiers = getUnplacedSoldiers().size();
    }
    //get the total number of soldiers
    public int getTotalNumOfSoldiers() {
        return totalNumOfSoldiers = soldiers.size();
    }

    //give handout to player
    public void setHandout(boolean handout) {
        isHandout = handout;
    }
    public boolean isHandout() {
        return isHandout;
    }

    //return the number of player lands
    public int getNumOfLands() {
        return numOfLands = lands.size();
    }

    public List<Soldier> getUnplacedSoldiers() {
        unplacedSoldiers.clear();
        for (Soldier soldier : soldiers) {
            if(soldier.getStateSoldier() == StateSoldier.UNPLACED)
                unplacedSoldiers.add(soldier);
        }
        return unplacedSoldiers;
    }
    public List<Soldier> getSoldiers() {
        return soldiers;
    }

    //i need to method that in time a player occupied all of lands of a continent, this continent add to player continents.
    public void addContinent(Continent continent) {
        continents.add(continent);
    }
    public void removeContinent(Continent continent) {
        continents.remove(continent);
    }
    public List<Continent> getContinents() {
        return continents;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
}
