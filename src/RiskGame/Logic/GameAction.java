package RiskGame.Logic;

import RiskGame.UI.GamePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameAction implements ActionListener, MouseListener {


    private Controller controller;
    private GamePage gamePage;

    private Land attackerLand, defenderLand;
    private Land sourceLand, destinationLand;

    private boolean isSelectDefenderLandAllowable;
    private boolean isSelectDestinationLandAllowable;

    public GameAction(Controller controller, GamePage gamePage) {
        this.controller = controller;
        this.gamePage = gamePage;
        gamePage.showPlayers(controller.getBoard().getPlayers());
        gamePage.getMapGUI().landsActionListener(this);
        gamePage.newButtonDicesPageAndMovePage();
        gamePage.dicesButtonActionListeners(this);
        controller.startGame();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String actionEvent = evt.getActionCommand();
        if (actionEvent.equals("rollBtn")) {
            gamePage.showRotationDices();
            controller.attack();
        } else if (actionEvent.equals("retreatBtn")) {
            gamePage.getShowDicesDialog().dispose();
            gamePage.showBrownDice();
        } else if (actionEvent.equals("confirmBtn")) {
            controller.fortify(sourceLand, destinationLand, gamePage.getSliderValue());
            sourceLand = null;
            destinationLand = null;
            gamePage.getShowMoveSoldiersDialog().dispose();
        } else if (actionEvent.equals("againBtn")) {
            if (controller.getPlayerInTurn() == attackerLand.getCommander() && attackerLand.getNumOfSoldier() > 1 && defenderLand.getCommander() != controller.getPlayerInTurn()) {
                gamePage.getShowDicesDialog().dispose();
                controller.initializationForAttack(attackerLand, defenderLand);
                gamePage.primitiveShowDices(controller.getBoard().getSoldiersToAttack(attackerLand).size(), controller.getBoard().getSoldiersToDefend(defenderLand).size());
            }
        } else {
            for (Land land : controller.getBoard().getMap().getLands()) {
                if (actionEvent.equals(land.getName())) {
                    if (controller.getGameState() == GameState.RANDOM_DEAL) {
                        controller.randomDeal(land);
/*
                    if (!controller.isEndedRandomDeal()) {
                        if (controller.)
                    }
*/
                    } else if (controller.getGameState() == GameState.REIN_FORCE) {
                        controller.reinForce(land);
                    } else if (controller.getGameState() == GameState.ATTACK) {
                        if (controller.getPlayerInTurn() == land.getCommander() && land.getNumOfSoldier() > 1) {
                            System.out.println("please click a land for attack to it...");
                            attackerLand = land;
                            //((JButton)evt.getSource()).setIcon();
                            isSelectDefenderLandAllowable = true;
                        } else if (isSelectDefenderLandAllowable && controller.getPlayerInTurn() != land.getCommander()) {
                            if (controller.getBoard().getAbuttingLands(attackerLand).contains(land)) {
                                defenderLand = land;
                                controller.initializationForAttack(attackerLand, defenderLand);
                                gamePage.primitiveShowDices(controller.getBoard().getSoldiersToAttack(attackerLand).size(), controller.getBoard().getSoldiersToDefend(defenderLand).size());
                            }
                        }
                    } else if (controller.getGameState() == GameState.FORTIFY) {
                        if (land.getCommander() == controller.getPlayerInTurn()) {
                            if (land == sourceLand ) {
                                isSelectDestinationLandAllowable = false;
                                sourceLand = null;
                            } else if (sourceLand == null && land.getNumOfSoldier() > 1) {
                                sourceLand = land;
                                isSelectDestinationLandAllowable = true;
                            }
                            if (isSelectDestinationLandAllowable && land != sourceLand && controller.getBoard().hasPathBFS(sourceLand, land) && land.getCommander() == sourceLand.getCommander()) {
                                destinationLand = land;
                                gamePage.showMoveSoldiersDialog(sourceLand.getNumOfSoldier() - 1);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isLandValid(Land land) {
        return controller.getPlayerInTurn() == land.getCommander();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void updateGamePage() {

    }

}
