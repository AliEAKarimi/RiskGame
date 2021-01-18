package RiskGame.Logic;

import RiskGame.UI.MapGUI;

import java.util.*;

public class Board {

    private Map map;
    private boolean isAbutting;
    private List<Land> emptyLands;
    private int playerCount;
    private List<Player> players;
    private List<Player> playersInGame;
    private List<Player> gameOverPlayers;
    private Land selectedLand;
    private List<Land> memberLands;

    public Board() {
        map = new MapGUI();
        emptyLands = new ArrayList<>();
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return players.size();
    }


    //assignment and return the lands that is empty
    public List<Land> getEmptyLands() {
        emptyLands.clear();
        for (Land land : map.getLands()) {
            if (land.isEmpty())
                emptyLands.add(land);
        }
        return emptyLands;
    }


    //put soldier by player in a "his" lands.
    public void putSoldierByPlayer(Player player, Land land) {
        player.putSoldier(land);
    }

    public void addSoldiersToPlayer(Player player, int numOfSoldiers) {
        player.addSoldiers(numOfSoldiers);
    }

    //for after attack if a land game over.move soldiers that alive to game over land.
    private void addSoldierToLand(List<Soldier> soldiers, Land land) {
        land.addSoldier(soldiers);
    }

    private void removeSoldierFromLand(List<Soldier> soldiers, Land land) {
        land.removeSoldiers(soldiers);
    }

    public void moveSoldiersFromALandToAnotherLandInAttack(List<Soldier> soldiers, Land attackerLand, Land defenderLand) {
        addSoldierToLand(soldiers, defenderLand);
        removeSoldierFromLand(soldiers, attackerLand);
    }

    public void moveSoldiersFromALandToAntherLandInFortify(Land landA, Land landB, int numOfSoldiers) {
        List<Soldier> soldiers = new ArrayList<>();
        for (int i = 0; i < numOfSoldiers; i++) {
            soldiers.add(landA.getSoldiers().get(i));
        }
        addSoldierToLand(soldiers, landB);
        removeSoldierFromLand(soldiers, landA);
    }

    //return the number of Land Soldiers.
    //no exist usage of this method.
    public int getNumOfSoldier(Land land) {
        return land.getNumOfSoldier();
    }

    private int getAwardingSoldiers(Continent continent) {
        return continent.getAwardingSoldiers();
    }

    public void setOccupied(Land occupied, Player occupant) {
        occupied.setOccupied(occupant);
    }

    //no exist usage of this method.
    public void setSelectedLand(Land selectedLand) {
        this.selectedLand = selectedLand;
    }
    public Land getSelectedLand() {
        return selectedLand;
    }


    public List<Land> getAbuttingLands(Land land) {
        return land.getAbuttingLands();
    }
    //does not exist usage of this method.
    public boolean isAbutting(Land landA, Land landB) {
        if (landA.getAbuttingLands().contains(landB)) {
            return isAbutting = true;
        } else {
            return isAbutting = false;
        }
    }

    public void giveSoldiersToPlayer(Player player) { //give awarding soldiers to playerInTurn from its lands and continents.
        player.addSoldiers(receiveAwardingSoldiers(player));
    }

    public int receiveAwardingSoldiers(Player player) {
        return receiveAwardingSoldiersFromContinents(player) + receiveAwardingSoldiersFromLands(player);
    }
    private int receiveAwardingSoldiersFromLands(Player player) {
        return player.getLands().size() / 3;
    }
    private int receiveAwardingSoldiersFromContinents(Player player) {
        addContinentToPlayer(player);
        int awardingSoldiers = 0;
        for (Continent continent : player.getContinents()) {
            awardingSoldiers += continent.getAwardingSoldiers();
        }
        return awardingSoldiers;
    }
    private void addContinentToPlayer(Player player) {
        for (Continent continent : map.getContinents()) {
            if (isPlayerHasThisContinent(player, continent)) {
                player.addContinent(continent);
            } else {
                if (player.getContinents().contains(continent)) {
                    player.getContinents().remove(continent);
                }
            }
        }
    }
    private boolean isPlayerHasThisContinent(Player player, Continent continent) {
            for (Land land : continent.getLands()) {
                if (player.getLands().contains(land))continue;
                return false;
            }
            return true;
    }

    public List<Soldier> getSoldiersToAttack(Land land) {
        List<Soldier> soldiersForAttack = new ArrayList<>();
        if (land.getNumOfSoldier() > 3) {
            for (int i = 0; i < 3; i++) {
                soldiersForAttack.add(land.getSoldiers().get(i));
            }
        } else {
            for (int i = 0; i < land.getSoldiers().size() - 1; i++) {
                soldiersForAttack.add(land.getSoldiers().get(i));
            }
        }
        return soldiersForAttack;
    }

    public List<Soldier> getSoldiersToDefend(Land land) {
        List<Soldier> soldiersForDefend = new ArrayList<>();
        if (land.getNumOfSoldier() > 1) {
            soldiersForDefend.add(land.getSoldiers().get(0));
            soldiersForDefend.add(land.getSoldiers().get(1));
        } else {
            soldiersForDefend.add(land.getSoldiers().get(0));
        }
        return soldiersForDefend;
    }

    public Map getMap() {
        return map;
    }

    public boolean hasPathBFS(Land landA, Land landB) {
        LinkedList<Land> nextToVisit = new LinkedList<>();
        HashSet<Land> visited = new HashSet<>();
        nextToVisit.add(landA);
        while (!nextToVisit.isEmpty()) {
            Land land = nextToVisit.remove();
            if (land == landB) {
                return true;
            }
            if (visited.contains(land)) {
                continue;
            }
            visited.add(land);

            for (Land land1 : land.getAbuttingLands()) {
                if (land1.getCommander() == landB.getCommander())
                    nextToVisit.add(land1);
            }
        }
        return false;
    }
}
