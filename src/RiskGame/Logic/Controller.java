package RiskGame.Logic;

import RiskGame.UI.GamePage;
import RiskGame.UI.Utils;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Controller {

    //for attack method.
    private Integer[] diceValueForAttackers;
    private Integer[] diceValueForDefenders;
    private List<Soldier> soldiersForAttack;
    private List<Soldier> soldiersForDefend;

    private Board board;
    private GameState gameState;
    private int numOfSoldiersAnyPlayer;
    private Land attackerLand;
    private Land defenderLand;
    private Player playerInTurn;
    private Timer updateInformation;
    private Timer isEndOfGame;
    private String message;
    private GamePage gamePage;
    private int numOfRounds = 0;

    public Controller(List<Player> players, GamePage gamePage) {
        this.gamePage = gamePage;

        initializationBoard(players);

        updateInformation = new Timer(33, e -> updateInformation());
        updateInformation.start();
    }

    private void initializationBoard(List<Player> players) {
        System.out.println("initialization Board...");
        board = new Board();
        board.setPlayers(players);
    }

    public void startGame() {
        System.out.println("The game started...");
        runFirstState();
    }

    //this method consist 3 methods.
    private void runFirstState() {
        System.out.println("set RandomDeal to Game State...");
        gameState = GameState.RANDOM_DEAL;
        randomDealLandsToPlayer();
        primitiveAddSoldierToPlayers();
        putOneSoldierInAnyLand();

    }

    //this method give random lands to any player.
    private void randomDealLandsToPlayer() {
        System.out.println("Random deal lands to players...");
        final int[] randomIndexLand = new int[1];
        Land land;
        while (board.getEmptyLands().size() > board.getPlayerCount()) {
            switch (board.getPlayerCount()) {
                case 4:
                    setPlayerInTurn(board.getPlayers().get(3));
                    randomIndexLand[0] = Utils.randomInteger(0, board.getEmptyLands().size() - 1);
                    land = board.getEmptyLands().get(randomIndexLand[0]);
                    board.setOccupied(land, board.getPlayers().get(3));
                    gamePage.addPlayerFlagInLand(land);

                case 3:
                    setPlayerInTurn(board.getPlayers().get(2));
                    randomIndexLand[0] = Utils.randomInteger(0, board.getEmptyLands().size() - 1);
                    land = board.getEmptyLands().get(randomIndexLand[0]);
                    board.setOccupied(land, board.getPlayers().get(2));
                    gamePage.addPlayerFlagInLand(land);

                case 2:
                    setPlayerInTurn(board.getPlayers().get(1));
                    randomIndexLand[0] = Utils.randomInteger(0, board.getEmptyLands().size() - 1);
                    land = board.getEmptyLands().get(randomIndexLand[0]);
                    board.setOccupied(land, board.getPlayers().get(1));
                    gamePage.addPlayerFlagInLand(land);
                    setPlayerInTurn(board.getPlayers().get(0));
                    randomIndexLand[0] = Utils.randomInteger(0, board.getEmptyLands().size() - 1);
                    land = board.getEmptyLands().get(randomIndexLand[0]);
                    board.setOccupied(land, board.getPlayers().get(0));
                    gamePage.addPlayerFlagInLand(land);
            }
        }
        int randomIndexPlayer = Utils.randomInteger(0, board.getPlayerCount() - 1);
        land = board.getEmptyLands().get(0);
        board.setOccupied(land, board.getPlayers().get(randomIndexPlayer));
        gamePage.addPlayerFlagInLand(land);
        if (board.getPlayerCount() == 3) {
            int randomValue;
            do {
                randomValue = Utils.randomInteger(0, board.getPlayerCount() - 1);
            } while (randomValue == randomIndexPlayer);
            land = board.getEmptyLands().get(0);
            board.setOccupied(land, board.getPlayers().get(randomValue));
            gamePage.addPlayerFlagInLand(land);
        }
    }

    //this method add soldiers to any players in amount of primitive number.
    private void primitiveAddSoldierToPlayers() {
        System.out.println("add primitive number of soldiers to players...");
        if (board.getPlayerCount() == 4) {
            numOfSoldiersAnyPlayer = 20;
        } else if (board.getPlayerCount() == 3) {
            numOfSoldiersAnyPlayer = 25;
        } else if (board.getPlayerCount() == 2) {
            numOfSoldiersAnyPlayer = 30;
        }
        for (Player player : board.getPlayers()) {
            board.addSoldiersToPlayer(player, numOfSoldiersAnyPlayer);
        }
    }

    //this method put one soldier to players lands automatically.
    private void putOneSoldierInAnyLand() {
        System.out.println("automatically put one soldier in any land...");
        for (Player player : board.getPlayers()) {
            for (Land land : player.getLands()) {
                board.putSoldierByPlayer(player, land);
            }
        }
        setPlayerInTurn(board.getPlayers().get(0));
        System.out.println("give handout to player number one : " + playerInTurn.getName());
    }


    public void nextState() {
        if (gameState == GameState.RANDOM_DEAL) {
            gameState = GameState.REIN_FORCE;
        } else if (gameState == GameState.REIN_FORCE) {
            gameState = GameState.ATTACK;
        } else if (gameState == GameState.ATTACK) {
            gameState = GameState.FORTIFY;
        } else if (gameState == GameState.FORTIFY) {
            gameState = GameState.REIN_FORCE;
            nextHandout();
            if (board.receiveAwardingSoldiers(playerInTurn) == 0)
                nextState();
            else board.giveSoldiersToPlayer(playerInTurn);
        }
        System.out.println("the state of game changed to " + gameState);
    }

    private void nextHandout() {
        outer:
        for (Player player : board.getPlayers()) {
            if (player.isHandout()) {
                player.setHandout(false);
                if (board.getPlayers().indexOf(player) == board.getPlayers().size() - 1) {
                    setPlayerInTurn(board.getPlayers().get(0));
                    numOfRounds++;
                }
                else
                    setPlayerInTurn(board.getPlayers().get(board.getPlayers().indexOf(player) + 1));
                break outer;
            }
        }
    }

    public void randomDeal(Land land) {
        System.out.println("players puts their unplaced soldiers on their lands in RandomDeal state ...");
        if (playerInTurn == land.getCommander()) {
            if (playerInTurn.getNumOfUnplacedSoldiers() > 0) {
                board.putSoldierByPlayer(playerInTurn, land);
                gamePage.showPlayerInTurn();
                System.out.println(playerInTurn.getName() + " put a soldier on " + land.getName());
                if (!isEndedPutSoldiersState()) {
                    do {
                        System.out.println("Change handout to player that have soldier step by step...");
                        nextHandout(); //this loop, give handout to player that has soldiers.
                    } while (playerInTurn.getNumOfUnplacedSoldiers() < 1);
                } else {
                    System.out.println("The game state changed to ReinForce from RandomDeal...");
                    gameState = GameState.REIN_FORCE;
                    setPlayerInTurn(board.getPlayers().get(0));
                    board.giveSoldiersToPlayer(playerInTurn);
                }
            }
        }
    }


    void reinForce(Land land) {
        if (gameState == GameState.REIN_FORCE) {
            if (playerInTurn.equals(land.getCommander())) {
                if (playerInTurn.getNumOfUnplacedSoldiers() > 0) {
                    board.putSoldierByPlayer(playerInTurn, land);
                    gamePage.showPlayerInTurn();
                    System.out.println(playerInTurn.getName() + " put a soldier on " + land.getName());
                    if (playerInTurn.getNumOfUnplacedSoldiers() == 0) {
                        nextState();
                    }
                }
            }
        }
    }

    void initializationForAttack(Land attackerLand, Land defenderLand) {
        this.attackerLand = attackerLand;
        this.defenderLand = defenderLand;
        soldiersForAttack = board.getSoldiersToAttack(attackerLand);
        soldiersForDefend = board.getSoldiersToDefend(defenderLand);
        diceValueForAttackers = new Integer[soldiersForAttack.size()];
        for (int i = 0; i < diceValueForAttackers.length; i++) {
            diceValueForAttackers[i] = Dice.getValue();
        }
        Arrays.sort(diceValueForAttackers, Collections.reverseOrder());

        diceValueForDefenders = new Integer[soldiersForDefend.size()];
        for (int i = 0; i < diceValueForDefenders.length; i++) {
            diceValueForDefenders[i] = Dice.getValue();
        }
        Arrays.sort(diceValueForDefenders, Collections.reverseOrder());
    }

    void attack() {
        for (int i = 0; i < ((diceValueForAttackers.length < diceValueForDefenders.length) ? diceValueForAttackers.length : diceValueForDefenders.length); i++) {
            if (diceValueForAttackers[i] > diceValueForDefenders[i]) {
                defenderLand.getSoldiers().remove(soldiersForDefend.get(i));
                defenderLand.getCommander().getSoldiers().remove(soldiersForDefend.get(i));
                //soldiersForDefend.remove(i);
                if (defenderLand.getNumOfSoldier() == 0) {
                    defenderLand.getCommander().removeLand(defenderLand);
                    board.setOccupied(defenderLand, attackerLand.getCommander());
                    board.moveSoldiersFromALandToAnotherLandInAttack(soldiersForAttack, attackerLand, defenderLand);
                    gamePage.addPlayerFlagInLand(defenderLand);
                }
            } else {
                attackerLand.getSoldiers().remove(soldiersForAttack.get(i));
                attackerLand.getCommander().getSoldiers().remove(soldiersForAttack.get(i));
                //soldiersForAttack.remove(i);
            }
        }
    }

    void fortify(Land landA, Land landB, int numOfSoldiersForMove) {
        board.moveSoldiersFromALandToAntherLandInFortify(landA, landB, numOfSoldiersForMove);
    }

    public Board getBoard() {
        return board;
    }

    private void updateInformation() {
        //with getEmptyLands method, the list of the emptyLand updates.
        board.getEmptyLands();

        //for returnPlayerInTurn
        for (Player player : board.getPlayers()) {
            if (player.isHandout()) {
                playerInTurn = player;
            }
        }
    }

    private void setPlayerInTurn(Player playerInTurn) {
        playerInTurn.setHandout(true);
        this.playerInTurn = playerInTurn;
        for (Player player : board.getPlayers()) {
            if (player != playerInTurn) {
                player.setHandout(false);
            }
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    private boolean isEndedPutSoldiersState() {
        int numOfUnplacedSoldiers = 0;
        for (Player player : board.getPlayers()) {
            numOfUnplacedSoldiers += player.getNumOfUnplacedSoldiers();
        }
        return numOfUnplacedSoldiers == 0;
    }

    public Integer[] getDiceValueForAttackers() {
        return diceValueForAttackers;
    }

    public Integer[] getDiceValueForDefenders() {
        return diceValueForDefenders;
    }

    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    public int getNumOfRounds() {
        return numOfRounds;
    }
}

