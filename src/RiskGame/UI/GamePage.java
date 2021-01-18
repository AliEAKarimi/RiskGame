package RiskGame.UI;

import RiskGame.Logic.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Duration;
import java.util.Iterator;

import static RiskGame.UI.Utils.*;

public class GamePage extends JPanel implements MouseListener {
    private JFrame owner;
    private MainPage mainPage;
    private Controller controller;
    private GameAction gameAction;
    private Timer updateGamePageTimer;
    private java.util.List<Player> players;
    private java.util.List<Land> lands;

    private JButton menuBtn;
    private ImageIcon menuBtnImageIc1;
    private ImageIcon menuBtnImageIc2;

    private MapGUI mapGUI;
    private JPanel gameMap;

    //for show land that mouse entered in it.
    private JLabel selectedLandBackgroundJL;
    private ImageIcon selectedLandImageIc;
    private ImageIcon selectedLandPlayer1Ic;
    private ImageIcon selectedLandPlayer2Ic;
    private ImageIcon selectedLandPlayer3Ic;
    private ImageIcon selectedLandPlayer4Ic;

    private JLabel selectedLandJL;
    //for background of this panel.
    private ImageIcon backgroundOfMapIc;
    private JLabel backgroundOfMapJL;

    //for show players
    private JLabel showPlayersJL;
    private JLabel player1JL;
    private ImageIcon player1ImageIc1;
    private ImageIcon player1ImageIc2;
    private JLabel player2JL;
    private ImageIcon player2ImageIc1;
    private ImageIcon player2ImageIc2;
    private JLabel player3JL;
    private ImageIcon player3ImageIc1;
    private ImageIcon player3ImageIc2;
    private JLabel player4JL;
    private ImageIcon player4ImageIc1;
    private ImageIcon player4ImageIc2;

    //for GameState JLabel.
    private JLayeredPane layeredPane;
    private CircleButton changeStateBtn;
    private JButton fortifyBtn;
    private JButton attackBtn;
    private JButton reinforceBtn;

    //for gameInformation
    private long lastTickTime;
    private ImageIcon sandClockIc;
    private JLabel sandClockJL;
    private int numOfRound;

    //for showSoldiersLabel
    private JLabel showSoldiersJL;
    private FlowLayout showSoldiersGridLayout;
    private ImageIcon soldiersPlayerIc;
    private ImageIcon soldiersPlayer1Ic;
    private ImageIcon soldiersPlayer2Ic;
    private ImageIcon soldiersPlayer3Ic;
    private ImageIcon soldiersPlayer4Ic;

    //for show dices
    private JDialog showDicesDialog;
    private JPanel showDicesJP;
    private JLabel brownDiceJL;
    private JLabel buttonJL;
    private JButton retreatBtn;
    private JButton rollBtn;
    private JButton againBtn;
    private JLabel[] defenderDiceJL;
    private JLabel[] attackerDiceJL;

    //for show movePage
    private JButton retreatBtnN;
    private JButton confirmBtn;
    private int sliderValue;
    private JDialog showMoveSoldiersDialog;

    //*******
    private JLabel numOfSoldiers;

    public GamePage(java.util.List<Player> players, JFrame owner, MainPage mainPage) {

        this.owner = owner;
        this.mainPage = mainPage;
        backgroundOfMapIc = new ImageIcon("resource\\images\\backgroundOfPage2.jpg");
        backgroundOfMapJL = new JLabel(backgroundOfMapIc);
        backgroundOfMapJL.setBounds(0, 0, backgroundOfMapIc.getIconWidth(), backgroundOfMapIc.getIconHeight());
        add(backgroundOfMapJL);

        menuBtn = new JButton();
        menuBtn.setActionCommand("menuBtn");
        menuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(owner, "You're trying to get back to main menu.All game data will be lost.Are you sure?", "Menu", JOptionPane.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
                if (a == JOptionPane.YES_OPTION) {
                    changePanel(owner, mainPage);
                    mainPage.backBtnAction();
                }
            }
        });
        menuBtn.addMouseListener(this);
        menuBtnImageIc1 = new ImageIcon("resource\\images\\icon\\button\\menu1.jpg");
        menuBtnImageIc2 = new ImageIcon("resource\\images\\icon\\button\\menu2.jpg");
        menuBtn.setBounds(195, 1000, menuBtnImageIc1.getIconWidth(), menuBtnImageIc1.getIconHeight());
        menuBtn.setIcon(menuBtnImageIc1);
        Utils.setOffBtn(menuBtn);
        backgroundOfMapJL.add(menuBtn);


        setBounds(0, 0, WIDTH_SCREEN, HEIGHT_SCREEN);

        controller = new Controller(players, this);
        mapGUI = (MapGUI) controller.getBoard().getMap();
        mapGUI.landsMouseListener(this);
        gameAction = new GameAction(controller, this);

        lastTickTime = System.currentTimeMillis();
        showGameInformation();

        addGameMap();
        backgroundOfMapJL.add(gameState());
        showLandThatSelected();

        //initializationShowSoldierLabel();
        updateGamePageTimer = new Timer(33, e -> updateGamePage());
        updateGamePageTimer.start();

        numOfSoldiers = new JLabel();

        initializationShowSoldierLabel();

        showBrownDice();
        showPlayerInTurn();

        setLayout(null);
        setVisible(true);

    }

    public void addGameMap() {
        gameMap = mapGUI.getMapGUIJP();
        backgroundOfMapJL.add(gameMap);
    }


    public JLayeredPane gameState() {
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(1465, 100, 400, 400);
        layeredPane.setBorder(BorderFactory.createTitledBorder("---Game State---"));


        ImageIcon reinforceIc = new ImageIcon("resource\\images\\gamePage\\gameState\\reinforce.png");
        reinforceBtn = new JButton(reinforceIc);
        reinforceBtn.setBounds(25, 225, reinforceIc.getIconWidth(), reinforceIc.getIconHeight());
        Utils.setOffBtn(reinforceBtn);
        reinforceBtn.setEnabled(false);
        layeredPane.add(reinforceBtn, new Integer(1));

        ImageIcon attackIc = new ImageIcon("resource\\images\\gamePage\\gameState\\attack.png");
        attackBtn = new JButton(attackIc);
        attackBtn.setBounds(125, 125, attackIc.getIconWidth(), attackIc.getIconHeight());
        Utils.setOffBtn(attackBtn);
        attackBtn.setEnabled(false);
        layeredPane.add(attackBtn, new Integer(1));

        ImageIcon fortifyIc = new ImageIcon("resource\\images\\gamePage\\gameState\\fortify.png");
        fortifyBtn = new JButton(fortifyIc);
        fortifyBtn.setBounds(225, 25, fortifyIc.getIconWidth(), fortifyIc.getIconHeight());
        Utils.setOffBtn(fortifyBtn);
        fortifyBtn.setEnabled(false);
        layeredPane.add(fortifyBtn, new Integer(1));

        changeStateBtn = new CircleButton("Change State");
        changeStateBtn.setBounds(230, 290, 200, 100);
        changeStateBtn.setBackground(Color.BLACK);
        changeStateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.getGameState() == GameState.ATTACK) {
                    controller.nextState();
                } else if (controller.getGameState() == GameState.FORTIFY) {
                    controller.nextState();
                    showPlayerInTurn();
                }
            }
        });
        layeredPane.add(changeStateBtn);

        layeredPane.setLayout(null);
        return layeredPane;
    }


    public void showPlayers(java.util.List<Player> players) {
        showPlayersJL = new JLabel();

        showPlayersJL.setLayout(new FlowLayout(FlowLayout.CENTER));
        showPlayersJL.setBounds(510, 0, 900, 60);
        if (players.size() >= 2) {
            player1JL = new JLabel(controller.getBoard().getPlayers().get(0).getName());
            player1ImageIc1 = new ImageIcon("resource\\images\\gamePage\\playersImage\\player11.jpg");
            player1ImageIc2 = new ImageIcon("resource\\images\\gamePage\\playersImage\\player12.jpg");
            player1JL.setIcon(player1ImageIc1);
            player1JL.setText(controller.getBoard().getPlayers().get(0).getName());
            player1JL.setHorizontalTextPosition(JLabel.CENTER);
            showPlayersJL.add(player1JL);
            player2JL = new JLabel(controller.getBoard().getPlayers().get(1).getName());
            player2ImageIc1 = new ImageIcon("resource\\images\\gamePage\\playersImage\\player21.jpg");
            player2ImageIc2 = new ImageIcon("resource\\images\\gamePage\\playersImage\\player22.jpg");
            player2JL.setIcon(player2ImageIc1);
            player2JL.setText(controller.getBoard().getPlayers().get(1).getName());
            player2JL.setHorizontalTextPosition(JLabel.CENTER);
            showPlayersJL.add(player2JL);

        }
        if (players.size() >= 3) {
            player3JL = new JLabel(controller.getBoard().getPlayers().get(2).getName());
            player3ImageIc1 = new ImageIcon("resource\\images\\gamePage\\playersImage\\player31.jpg");
            player3ImageIc2 = new ImageIcon("resource\\images\\gamePage\\playersImage\\player32.jpg");
            player3JL.setIcon(player3ImageIc1);
            player3JL.setHorizontalTextPosition(JLabel.CENTER);
            showPlayersJL.add(player3JL);
        }
        if (players.size() == 4) {
            player4JL = new JLabel(controller.getBoard().getPlayers().get(3).getName());
            player4ImageIc1 = new ImageIcon("resource\\images\\gamePage\\playersImage\\player41.jpg");
            player4ImageIc2 = new ImageIcon("resource\\images\\gamePage\\playersImage\\player42.jpg");
            player4JL.setIcon(player4ImageIc1);
            player4JL.setHorizontalTextPosition(JLabel.CENTER);
            showPlayersJL.add(player4JL);
        }
        backgroundOfMapJL.add(showPlayersJL);
    }

    public void menuBtnAction() {
        //changePanel(owner, new MainPage(owner));
    }

    public void showLandThatSelected() { //in this part shows flag, name, number of its soldiers , ...
        selectedLandJL = new JLabel();
        selectedLandImageIc = new ImageIcon("resource\\images\\gamePage\\television\\television1.jpg");
        selectedLandPlayer1Ic = new ImageIcon("resource\\images\\gamePage\\television\\TVPlayer1.jpg");
        selectedLandPlayer2Ic = new ImageIcon("resource\\images\\gamePage\\television\\TVPlayer2.jpg");
        selectedLandPlayer3Ic = new ImageIcon("resource\\images\\gamePage\\television\\TVPlayer3.jpg");
        selectedLandPlayer4Ic = new ImageIcon("resource\\images\\gamePage\\television\\TVPlayer4.jpg");
        selectedLandJL.setIcon(selectedLandImageIc);
        selectedLandJL.setBounds(100, 100, 300, 400);
        selectedLandJL.setLayout(new BoxLayout(selectedLandJL, BoxLayout.Y_AXIS));
        selectedLandJL.setVisible(true);
        backgroundOfMapJL.add(selectedLandJL);
    }

    public void showLandThatSelected2(int i, int j) {
        Object object = mapGUI.getSortedMap().get(i * 6 + j);
        if (object instanceof Land) {
            //object = mapGUI.getLands().get(mapGUI.getLands().indexOf(object));
            Land landThatMouseEntered;
            String landFlagImageThatMouseEntered;
            String[][] addressFlapIcon;
            JLabel landNameJL;
            JLabel flagImageJL;
            JLabel commanderJL;
            JLabel numOfSoldiersJL;
            JLabel landsContinentJL;
            String landName = "";
            String landCommander = "";
            String numOfSoldiers = "";
            String landsContinent = "";
            ImageIcon flagImageIc;

            addressFlapIcon = new String[][]{
                    {"resource\\images\\gamePage\\Lands\\continent\\american\\canada_flag1.gif", "resource\\images\\gamePage\\Lands\\continent\\american\\usa_flag1.gif", "resource\\images\\gamePage\\Lands\\continent\\europe\\denmark1.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\poland1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\china1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\northKorea1.png"},
                    {"resource\\images\\gamePage\\Lands\\continent\\american\\mexico_flag1.gif", "resource\\images\\gamePage\\Lands\\continent\\american\\cuba_flag1.gif", "resource\\images\\gamePage\\Lands\\continent\\europe\\belgium1.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\germany1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\turkey1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\iran1.png"},
                    {"resource\\images\\gamePage\\Lands\\continent\\american\\colombia_flag1.gif", "", "resource\\images\\gamePage\\Lands\\continent\\europe\\france1.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\italy1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\iraq1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\pakistan1.png"},
                    {"resource\\images\\gamePage\\Lands\\continent\\american\\peru_flag1.gif", "", "", "resource\\images\\gamePage\\Lands\\continent\\africa\\egypt1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\india1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\japan1.png"},
                    {"resource\\images\\gamePage\\Lands\\continent\\american\\brazil_flag1.gif", "", "resource\\images\\gamePage\\Lands\\continent\\africa\\mali1.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\niger1.png", "", ""},
                    {"resource\\images\\gamePage\\Lands\\continent\\american\\chile_flag1.gif", "resource\\images\\gamePage\\Lands\\continent\\american\\argentina_flag1.gif", "resource\\images\\gamePage\\Lands\\continent\\africa\\sudan1.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\kenya1.png", "", ""},
                    {"", "", "resource\\images\\gamePage\\Lands\\continent\\africa\\zambia1.png", "", "", ""}
            };

            landThatMouseEntered = (Land) object;
            landFlagImageThatMouseEntered = addressFlapIcon[i][j];
            landName = "the name of land : " + landThatMouseEntered.getName();
            if (landThatMouseEntered.getCommander() != null)
                landCommander = "the commander of land : " + landThatMouseEntered.getCommander().getName();
            numOfSoldiers = "the number of soldiers : " + landThatMouseEntered.getSoldiers().size();
            for (Continent continent : mapGUI.getContinents()) {
                if (continent.getLands().contains(landThatMouseEntered)) {
                    landsContinent = "its continent : " + continent.getName();
                }
            }

            //if (landNameJL != null) selectedLandJP.remove(landNameJL);
            landNameJL = new JLabel();
            landNameJL.setText(landName);
            landNameJL.revalidate();
            landNameJL.repaint();

            flagImageIc = new ImageIcon(landFlagImageThatMouseEntered);

            flagImageJL = new JLabel();
            flagImageJL.setIcon(flagImageIc);

            commanderJL = new JLabel();
            commanderJL.setText(landCommander);
            commanderJL.revalidate();
            commanderJL.repaint();

            numOfSoldiersJL = new JLabel();
            numOfSoldiersJL.setText(numOfSoldiers);
            revalidate();
            repaint();

            landsContinentJL = new JLabel();
            landsContinentJL.setText(landsContinent);
            revalidate();
            repaint();

            RiskGame.Logic.Color color = ((Land) object).getCommander().getColor();
            if (color == RiskGame.Logic.Color.RED) {
                selectedLandJL.setIcon(selectedLandPlayer1Ic);
            } else if (color == RiskGame.Logic.Color.BLUE) {
                selectedLandJL.setIcon(selectedLandPlayer2Ic);
            } else if (color == RiskGame.Logic.Color.GREEN) {
                selectedLandJL.setIcon(selectedLandPlayer3Ic);
            } else if (color == RiskGame.Logic.Color.CYAN) {
                selectedLandJL.setIcon(selectedLandPlayer4Ic);
            }
            selectedLandJL.add(Box.createRigidArea(new Dimension(80, 85)));
            selectedLandJL.add(flagImageJL);
            selectedLandJL.add(Box.createRigidArea(new Dimension(80, 15)));
            selectedLandJL.add(landNameJL);
            selectedLandJL.add(Box.createRigidArea(new Dimension(50, 15)));
            selectedLandJL.add(landsContinentJL);
            selectedLandJL.add(Box.createRigidArea(new Dimension(50, 15)));
            selectedLandJL.add(commanderJL);
            selectedLandJL.add(Box.createRigidArea(new Dimension(50, 15)));
            selectedLandJL.add(numOfSoldiersJL);
            backgroundOfMapJL.add(selectedLandJL);
        }
    }


    public void showGameInformation() {

        JLabel backgroundJL = new JLabel(new ImageIcon("resource\\images\\icon\\button\\backgroundForGameInformation.jpg"));
        backgroundJL.setBounds(100, 820, 300, 155);
        backgroundJL.setVisible(true);

        //for show sand Clock
        sandClockIc = new ImageIcon("resource\\gif\\sandClock1.gif");
        sandClockJL = new JLabel(sandClockIc);
        sandClockJL.setBounds(100, 10, sandClockIc.getIconWidth(), sandClockIc.getIconHeight());
        backgroundJL.add(sandClockJL);

        JLabel roundJL = new JLabel();
        roundJL.setBounds(85, 100, 200, 20);
        roundJL.setForeground(Color.CYAN);
        new Timer(33, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roundJL.setText("the number of rounds : " + numOfRound);
                roundJL.revalidate();
                roundJL.repaint();
                backgroundJL.add(roundJL);
            }
        }).start();

        //this label show timer and round of game , ...
        JLabel timerJL = new JLabel(String.format("the elapsed time : %02d:%02d:%02d.%3d", 0, 0, 0, 0));
        timerJL.setFont(new Font("Verdana", Font.BOLD, 12));
        timerJL.setBounds(43, 110, 300, 35);
        timerJL.setVisible(true);
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long runningTime = System.currentTimeMillis() - lastTickTime;
                Duration duration = Duration.ofMillis(runningTime);
                long hours = duration.toHours();
                duration = duration.minusHours(hours);
                long minutes = duration.toMinutes();
                duration = duration.minusMinutes(minutes);
                long millis = duration.toMillis();
                long seconds = millis / 1000;
                millis -= (seconds * 1000);
                timerJL.setText(String.format("the elapsed time : %02d:%02d:%02d.%3d", hours, minutes, seconds, millis));
                timerJL.setForeground(Color.CYAN);
            }
        });
        timer.start();
        backgroundJL.add(timerJL);
        backgroundOfMapJL.add(backgroundJL);
    }

    public MapGUI getMapGUI() {
        return mapGUI;
    }


    //this method show somethings like "player '' attack." in below map.
    public void guid() {

    }

    public void newButtonDicesPageAndMovePage() {
        retreatBtn = new JButton();
        rollBtn = new JButton();
        retreatBtnN = new JButton();
        confirmBtn = new JButton();
        againBtn = new JButton();
    }

    public void primitiveShowDices(int numOfAttacker, int numOfDefender) {
        showBlackDice();

        showDicesDialog = new JDialog(owner, "", true);
        showDicesDialog.setLayout(null);
        showDicesDialog.setSize(500, 700);
        showDicesDialog.setLocation(WIDTH_SCREEN / 2 - showDicesDialog.getWidth() / 2, HEIGHT_SCREEN / 2 - showDicesDialog.getHeight() / 2);
        showDicesDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        showDicesDialog.setResizable(false);
        showDicesDialog.setUndecorated(true);

        JPanel showDicesJP = new JPanel();
        showDicesJP.setBounds(0, 0, 500, 630);
        showDicesJP.setLayout(null);
        showDicesJP.setVisible(true);
        JLabel backgroundShowDicesJL = new JLabel(new ImageIcon("resource\\images\\icon\\button\\dicesBackground.jpg"));
        backgroundShowDicesJL.setBounds(0, 0, 500, 630);
        showDicesJP.add(backgroundShowDicesJL);

        ImageIcon diceIc = new ImageIcon("resource\\images\\dice\\stoppedDice.png");
        int width = diceIc.getIconWidth();
        int height = diceIc.getIconHeight();

        attackerDiceJL = new JLabel[numOfAttacker];
        for (int i = 0; i < numOfAttacker; i++) {
            attackerDiceJL[i] = new JLabel();
            attackerDiceJL[i].setIcon(diceIc);
            attackerDiceJL[i].setBounds(75, 100 + i * 170, width, height);
            backgroundShowDicesJL.add(attackerDiceJL[i]);
        }
        defenderDiceJL = new JLabel[numOfDefender];
        for (int i = 0; i < numOfDefender; i++) {
            defenderDiceJL[i] = new JLabel();
            defenderDiceJL[i].setIcon(diceIc);
            defenderDiceJL[i].setBounds(325, 100 + i * 170, width, height);
            backgroundShowDicesJL.add(defenderDiceJL[i]);
        }
        showDicesDialog.add(showDicesJP);

        buttonJL = new JLabel();
        buttonJL.setBounds(0, 630, 500, 70);
        buttonJL.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonJL.setOpaque(true);
        buttonJL.setBackground(Color.BLACK);
        showDicesDialog.add(buttonJL);


/*
        ImageIcon retreatBtnIc1 = new ImageIcon();
        ImageIcon retreatBtnIc2 = new ImageIcon();
*/
        retreatBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\retreat1.jpg"));
        retreatBtn.addMouseListener(this);
        retreatBtn.setActionCommand("retreatBtn");
        Utils.setOffBtn(retreatBtn);
        retreatBtn.setSize(200, 50);
        buttonJL.add(retreatBtn);

/*
        ImageIcon rollBtnIc1 = new ImageIcon();
        ImageIcon rollBtnIc2 = new ImageIcon();

*/
        rollBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\roll1.jpg"));
        rollBtn.addMouseListener(this);
        rollBtn.setActionCommand("rollBtn");
        Utils.setOffBtn(rollBtn);
        rollBtn.setSize(200, 50);
        buttonJL.add(rollBtn);

        againBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\again1.jpg"));
        againBtn.addMouseListener(this);
        againBtn.setActionCommand("againBtn");
        Utils.setOffBtn(againBtn);
        againBtn.setLocation(266, 10);
        againBtn.setSize(200, 50);

        showDicesDialog.setVisible(true);
    }

    public void dicesButtonActionListeners(ActionListener evt) {
        rollBtn.addActionListener(evt);
        retreatBtn.addActionListener(evt);
        confirmBtn.addActionListener(evt);
        retreatBtnN.addActionListener(evt);
        againBtn.addActionListener(evt);
    }

    public void showRotationDices() {
        new Timer(1000, new ActionListener() {
            int counter = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                if (counter >= 2) {
                    ((Timer) e.getSource()).stop();
                    finallyShowDices();
                }
            }
        }).start();

        ImageIcon rotationDiceIc = new ImageIcon("resource\\images\\dice\\dice.gif");
        for (JLabel label : attackerDiceJL) {
            label.setIcon(rotationDiceIc);
        }
        for (JLabel label : defenderDiceJL) {
            label.setIcon(rotationDiceIc);
        }
    }

    public void finallyShowDices() {
        new Timer(300, new ActionListener() {
            int counter = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                if (counter >= 2) {
                    ((Timer) e.getSource()).stop();
                    showDicesAfterWar();
                }
            }
        }).start();
        for (int i = 0; i < attackerDiceJL.length; i++) {
            int diceValue = controller.getDiceValueForAttackers()[i];
            if (diceValue == 1) {
                attackerDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\1.gif"));
            } else if (diceValue == 2) {
                attackerDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\2.gif"));
            } else if (diceValue == 3) {
                attackerDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\3.gif"));
            } else if (diceValue == 4) {
                attackerDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\4.gif"));
            } else if (diceValue == 5) {
                attackerDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\5.gif"));
            } else if (diceValue == 6) {
                attackerDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\6.gif"));
            }
        }
        for (int i = 0; i < defenderDiceJL.length; i++) {
            int diceValue = controller.getDiceValueForDefenders()[i];
            if (diceValue == 1) {
                defenderDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\1.gif"));
            } else if (diceValue == 2) {
                defenderDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\2.gif"));
            } else if (diceValue == 3) {
                defenderDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\3.gif"));
            } else if (diceValue == 4) {
                defenderDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\4.gif"));
            } else if (diceValue == 5) {
                defenderDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\5.gif"));
            } else if (diceValue == 6) {
                defenderDiceJL[i].setIcon(new ImageIcon("resource\\images\\dice\\6.gif"));
            }
        }
    }

    public void showBrownDice() {
        ImageIcon brownDiceIc = new ImageIcon("resource\\images\\dice\\brownDice.png");
        if (brownDiceJL == null) brownDiceJL = new JLabel();
        brownDiceJL.setBounds(1460, 550, 400, 400);
        brownDiceJL.setIcon(brownDiceIc);
        brownDiceJL.setHorizontalAlignment(JLabel.CENTER);
        backgroundOfMapJL.add(brownDiceJL);
    }

    public void showBlackDice() {
        ImageIcon blackDiceIc = new ImageIcon("resource\\images\\dice\\blackDice.png");
        new Timer(1, e -> Utils.moveVerticalComponent(backgroundOfMapJL, brownDiceJL, 551, null)).start();
        brownDiceJL.setIcon(blackDiceIc);
    }

    public void showDicesPanel() {
        showDicesJP.setOpaque(true);

    }

    public void showDicesAfterWar() {
        for (int i = 0; i < ((attackerDiceJL.length < defenderDiceJL.length) ? attackerDiceJL.length : defenderDiceJL.length); i++) {
            int diceValueForAttack = controller.getDiceValueForAttackers()[i];
            int diceValueForDefend = controller.getDiceValueForDefenders()[i];
            int diceValue;
            JLabel[] labels;
            if (diceValueForAttack <= diceValueForDefend) {
                diceValue = diceValueForAttack;
                labels = attackerDiceJL;
            } else {
                diceValue = diceValueForDefend;
                labels = defenderDiceJL;
            }
            if (diceValue == 1) {
                labels[i].setIcon(new ImageIcon("resource\\images\\dice\\broken1.gif"));
            } else if (diceValue == 2) {
                labels[i].setIcon(new ImageIcon("resource\\images\\dice\\broken2.gif"));
            } else if (diceValue == 3) {
                labels[i].setIcon(new ImageIcon("resource\\images\\dice\\broken3.gif"));
            } else if (diceValue == 4) {
                labels[i].setIcon(new ImageIcon("resource\\images\\dice\\broken4.gif"));
            } else if (diceValue == 5) {
                labels[i].setIcon(new ImageIcon("resource\\images\\dice\\broken5.gif"));
            } else if (diceValue == 6) {
                labels[i].setIcon(new ImageIcon("resource\\images\\dice\\broken6.gif"));
            }

        }
    }


    public void initializationShowSoldierLabel() {
        showSoldiersJL = new JLabel();
        showSoldiersGridLayout = new FlowLayout(FlowLayout.LEFT);
        soldiersPlayerIc = null;
        soldiersPlayer1Ic = new ImageIcon("resource\\images\\gamePage\\soldiers\\soldierPlayer11.png");
        soldiersPlayer2Ic = new ImageIcon("resource\\images\\gamePage\\soldiers\\soldierPlayer22.png");
        soldiersPlayer3Ic = new ImageIcon("resource\\images\\gamePage\\soldiers\\soldierPlayer33.png");
        soldiersPlayer4Ic = new ImageIcon("resource\\images\\gamePage\\soldiers\\soldierPlayer44.png");
        showSoldiersJL.setBounds(100, 510, 300, 300);
        showSoldiersJL.setLayout(showSoldiersGridLayout);
        showSoldiersJL.setIcon(new ImageIcon("resource\\images\\icon\\button\\backgroundForShowSoldiers.jpg"));
        showSoldiersGridLayout.setHgap(10);
        showSoldiersGridLayout.setVgap(10);
        showSoldiersJL.setVisible(true);
    }

    public void updateShowSoldiersLabel() {
        showSoldiersJL.removeAll();
        showSoldiersJL.revalidate();
        showSoldiersJL.repaint();

        if (controller.getGameState() == GameState.RANDOM_DEAL || controller.getGameState() == GameState.REIN_FORCE) {
            switch (controller.getPlayerInTurn().getColor()) {
                case RED:  //for player number one
                    soldiersPlayerIc = soldiersPlayer1Ic;
                    break;
                case BLUE:  //for player number two
                    soldiersPlayerIc = soldiersPlayer2Ic;
                    break;
                case GREEN:  //for player number three
                    soldiersPlayerIc = soldiersPlayer3Ic;
                    break;
                case CYAN:  //for player number four
                    soldiersPlayerIc = soldiersPlayer4Ic;
                    break;
            }

            for (int i = 0; i < controller.getPlayerInTurn().getNumOfUnplacedSoldiers(); i++) {
                JButton soldiersPlayerBtn = new JButton(soldiersPlayerIc);
                Utils.setOffBtn(soldiersPlayerBtn);
                soldiersPlayerBtn.setSize(soldiersPlayerIc.getIconWidth(), soldiersPlayerIc.getIconHeight());
                showSoldiersJL.add(soldiersPlayerBtn);
            }
        }
        backgroundOfMapJL.add(showSoldiersJL);
    }


    public void addPlayerFlagInLand(Land land) {
        JLabel playerFlagJL = new JLabel();
        ImageIcon playerFlagIc = null;
        JButton landBtn = getLandBtn(land);
        landBtn.removeAll();
        RiskGame.Logic.Color color = land.getCommander().getColor();
        if (color == RiskGame.Logic.Color.RED) {
            playerFlagIc = new ImageIcon("resource\\gif\\player\\flag1.gif");
        } else if (color == RiskGame.Logic.Color.BLUE) {
            playerFlagIc = new ImageIcon("resource\\gif\\player\\flag2.gif");
        } else if (color == RiskGame.Logic.Color.GREEN) {
            playerFlagIc = new ImageIcon("resource\\gif\\player\\flag3.gif");
        } else if (color == RiskGame.Logic.Color.CYAN) {
            playerFlagIc = new ImageIcon("resource\\gif\\player\\flag4.gif");
        }
        playerFlagJL.setIcon(playerFlagIc);
        landBtn.setLayout(null);
        landBtn.add(playerFlagJL);
        playerFlagJL.setBounds(50, 50, 50, 40);
    }

    public JButton getLandBtn(Land land) {
        for (JButton[] landBtn1 : mapGUI.getLandsBtn()) {
            for (JButton landBtn2 : landBtn1) {
                if (landBtn2.getToolTipText() == land.getName())
                    return landBtn2;
            }
        }
        return null;
    }

    public void showMoveSoldiersDialog(int maximumNumberOfSoldiers) {
        showMoveSoldiersDialog = new JDialog(owner, "", true);
        showMoveSoldiersDialog.setLayout(null);
        showMoveSoldiersDialog.setSize(500, 700);
        showMoveSoldiersDialog.setLocation(WIDTH_SCREEN / 2 - showMoveSoldiersDialog.getWidth() / 2, HEIGHT_SCREEN / 2 - showMoveSoldiersDialog.getHeight() / 2);
        showMoveSoldiersDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        showMoveSoldiersDialog.setResizable(false);
        showMoveSoldiersDialog.setUndecorated(true);

        JLabel backgroundShowSoldiersDialogJL = new JLabel();
        ImageIcon backgroundShowSoldiersIc = new ImageIcon("resource\\images\\icon\\button\\dicesBackground.jpg");
        backgroundShowSoldiersDialogJL.setIcon(backgroundShowSoldiersIc);
        backgroundShowSoldiersDialogJL.setBounds(0, 0, 500, 630);
        showMoveSoldiersDialog.add(backgroundShowSoldiersDialogJL);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, maximumNumberOfSoldiers, 1);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSliderValue(slider.getValue());
            }
        });
        slider.setSize(300, 50);
        slider.setLocation(100, 500);
        slider.setBackground(Color.BLACK);
        backgroundShowSoldiersDialogJL.add(slider);

        JLabel buttonJL = new JLabel();
        buttonJL.setBounds(0, 630, 500, 70);
        buttonJL.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonJL.setOpaque(true);
        buttonJL.setBackground(Color.BLACK);
        showMoveSoldiersDialog.add(buttonJL);

        retreatBtnN.addMouseListener(this);
        retreatBtnN.setName("retreatBtnN");
        retreatBtnN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMoveSoldiersDialog.dispose();
            }
        });
        ImageIcon retreatBtnIc1 = new ImageIcon("resource\\images\\icon\\button\\retreat1.jpg");
        ImageIcon retreatBtnIc2 = new ImageIcon("resource\\images\\icon\\button\\retreat2.jpg");
        retreatBtnN.setIcon(retreatBtnIc1);
        retreatBtnN.setSize(retreatBtnIc1.getIconWidth(), retreatBtnIc2.getIconHeight());
        Utils.setOffBtn(retreatBtnN);
        buttonJL.add(retreatBtnN);

        confirmBtn.setActionCommand("confirmBtn");
        confirmBtn.setName("confirmBtnN");
        confirmBtn.addMouseListener(this);
        ImageIcon confirmBtnIc1 = new ImageIcon("resource\\images\\icon\\button\\confirm11.jpg");
        ImageIcon confirmBtnIc2 = new ImageIcon("resource\\images\\icon\\button\\confirm22.jpg");
        confirmBtn.setIcon(confirmBtnIc1);
        confirmBtn.setSize(confirmBtnIc1.getIconWidth(), confirmBtnIc2.getIconHeight());
        Utils.setOffBtn(confirmBtn);
        buttonJL.add(confirmBtn);

        showMoveSoldiersDialog.setVisible(true);
    }

    private void setSliderValue(int sliderValue) {
        this.sliderValue = sliderValue;
    }

    public int getSliderValue() {
        return sliderValue;
    }

    public JDialog getShowMoveSoldiersDialog() {
        return showMoveSoldiersDialog;
    }

    public void showPlayerInTurn() {
        //update show player icon and his handout. //this part has a bug.and this part must be implement in term Player in turn color.
        RiskGame.Logic.Color playerColor = controller.getPlayerInTurn().getColor();
        if (playerColor == RiskGame.Logic.Color.RED) {
            player1JL.setIcon(player1ImageIc2);
            if (player2JL != null) player2JL.setIcon(player2ImageIc1);
            if (player3JL != null) player3JL.setIcon(player3ImageIc1);
            if (player4JL != null) player4JL.setIcon(player4ImageIc1);
        } else if (playerColor == RiskGame.Logic.Color.BLUE) {
            player2JL.setIcon(player2ImageIc2);
            if (player3JL != null) player3JL.setIcon(player3ImageIc1);
            if (player4JL != null) player4JL.setIcon(player4ImageIc1);
            player1JL.setIcon(player1ImageIc1);
        } else if (playerColor == RiskGame.Logic.Color.GREEN) {
            player3JL.setIcon(player3ImageIc2);
            if (player4JL != null) player4JL.setIcon(player4ImageIc1);
            player2JL.setIcon(player2ImageIc1);
            player1JL.setIcon(player1ImageIc1);
        } else if (playerColor == RiskGame.Logic.Color.CYAN) {
            player4JL.setIcon(player4ImageIc2);
            player3JL.setIcon(player3ImageIc1);
            player2JL.setIcon(player2ImageIc1);
            player1JL.setIcon(player1ImageIc1);
        }
    }
    public void updateGamePage() {
        //for update state of the game.
        players = controller.getBoard().getPlayers();
        if (controller.getGameState() == GameState.RANDOM_DEAL) { //for enabled or disabled changeStateBtn
            changeStateBtn.setEnabled(false);
        } else {
            changeStateBtn.setEnabled(true);
            if (controller.getGameState() == GameState.REIN_FORCE) { //for update gameState layeredPane.
                layeredPane.moveToFront(attackBtn);
                layeredPane.moveToFront(reinforceBtn);
                attackBtn.setEnabled(false);
                reinforceBtn.setEnabled(true);
                fortifyBtn.setEnabled(false);
            } else if (controller.getGameState() == GameState.ATTACK) {
                layeredPane.moveToFront(attackBtn);
                attackBtn.setEnabled(true);
                reinforceBtn.setEnabled(false);
                fortifyBtn.setEnabled(false);
            } else if (controller.getGameState() == GameState.FORTIFY) {
                layeredPane.moveToFront(fortifyBtn);
                attackBtn.setEnabled(false);
                reinforceBtn.setEnabled(false);
                fortifyBtn.setEnabled(true);
            }
        }

        //for show soldiers
        updateShowSoldiersLabel();
        numOfSoldiers.revalidate();
        numOfSoldiers.repaint();
        numOfSoldiers.setText("The number of soldier  : " + controller.getPlayerInTurn().getNumOfUnplacedSoldiers());
        numOfSoldiers.setForeground(Color.CYAN);
        numOfSoldiers.setBounds(10, 850, 200, 50);
        showSoldiersJL.add(numOfSoldiers);

        //for show land that was selected.
        numOfRound = controller.getNumOfRounds();

        //for delete player that game over and show winner.
        if (controller.getBoard().getPlayers().size() == 1) {
            if (showDicesDialog.isActive()) {
                showDicesDialog.dispose();
            }
            showWinner();
            new Timer(1000, new ActionListener() {
                int counter = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    counter++;
                    if (counter >= 1) {
                        ((Timer) e.getSource()).stop();
                    }
                }
            }).start();
            updateGamePageTimer.stop();
        }
        Iterator itr = controller.getBoard().getPlayers().iterator();
        while (itr.hasNext()) {
            Player player = ((Player) itr.next());
            if (player.getLands().size() == 0) {
                RiskGame.Logic.Color color = player.getColor();
                if (color == RiskGame.Logic.Color.CYAN) {
                    showPlayersJL.remove(player4JL);
                } else if (color == RiskGame.Logic.Color.GREEN) {
                    showPlayersJL.remove(player3JL);
                } else if (color == RiskGame.Logic.Color.BLUE) {
                    showPlayersJL.remove(player2JL);
                } else if (color == RiskGame.Logic.Color.RED) {
                    showPlayersJL.remove(player1JL);
                }
                showPlayersJL.revalidate();
                showPlayersJL.repaint();
                itr.remove();
                showPlayerInTurn();
            }
        }
    }

    public JDialog getShowDicesDialog() {
        return showDicesDialog;
    }

    private void showWinner() {
        JDialog showWinnerDialog = new JDialog();
        showWinnerDialog = new JDialog(owner, "", true);
        showWinnerDialog.setLayout(null);
        showWinnerDialog.setSize(300, 400);
        showWinnerDialog.setLocation(WIDTH_SCREEN / 2 - showWinnerDialog.getWidth() / 2, HEIGHT_SCREEN / 2 - showWinnerDialog.getHeight() / 2);
        showWinnerDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        showWinnerDialog.setResizable(false);
        showWinnerDialog.setUndecorated(true);


        JLabel backgroundShowWinnerJL = new JLabel(new ImageIcon("resource\\images\\icon\\button\\background.jpg"));
        backgroundShowWinnerJL.setBounds(0, 0, 300, 400);
        showWinnerDialog.add(backgroundShowWinnerJL);

        JLabel winnerTextJL = new JLabel();
        winnerTextJL.setText(controller.getBoard().getPlayers().get(0).getName() + " won the game.");
        winnerTextJL.setForeground(Color.CYAN);
        winnerTextJL.setFont(new Font("Verdana", Font.ITALIC, 14));
        winnerTextJL.setBounds(50, 50, 250, 50);
        backgroundShowWinnerJL.add(winnerTextJL, JLabel.CENTER);

        JButton menuBtn = new JButton();
        menuBtn.setBounds(100, 300, 100, 50);
        menuBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\menu1.jpg"));
        JDialog finalShowWinnerDialog = showWinnerDialog;
        menuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(owner, mainPage);
                mainPage.backBtnAction();
                finalShowWinnerDialog.dispose();
            }
        });
        menuBtn.setName("menuBtnForShowWinner");
        menuBtn.addMouseListener(this);
        backgroundShowWinnerJL.add(menuBtn);

        showWinnerDialog.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == rollBtn) {
            buttonJL.remove(rollBtn);
            buttonJL.revalidate();
            buttonJL.repaint();
            buttonJL.add(againBtn);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (((JButton) e.getSource()).getName() == "retreatBtnN") {
            ((JButton) e.getSource()).setIcon(new ImageIcon("resource\\images\\icon\\button\\retreat2.jpg"));
        } else if (((JButton) e.getSource()).getName() == "confirmBtnN") {
            ((JButton) e.getSource()).setIcon(new ImageIcon("resource\\images\\icon\\button\\confirm22.jpg"));
        } else if (e.getSource() == retreatBtn) {
            retreatBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\retreat2.jpg"));
        } else if (e.getSource() == rollBtn) {
            rollBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\roll2.jpg"));
        } else if (e.getSource() == againBtn) {
            againBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\again2.jpg"));
        } else if (e.getSource() == menuBtn) {
            menuBtn.setIcon(menuBtnImageIc2);
        } else if (((JButton) e.getSource()).getName() == "menuBtnForShowWinner") {
            ((JButton) e.getSource()).setIcon(new ImageIcon("resource\\images\\icon\\button\\menu2.jpg"));
        } else {
            for (int i = 0; i < mapGUI.getLandsBtn().length; i++) {
                for (int j = 0; j < mapGUI.getLandsBtn()[i].length; j++) {
                    if (e.getSource() == mapGUI.getLandsBtn()[i][j]) {
                        showLandThatSelected2(i, j);
                    }
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (((JButton) e.getSource()).getName() == "retreatBtnN") {
            ((JButton) e.getSource()).setIcon(new ImageIcon("resource\\images\\icon\\button\\retreat1.jpg"));
        } else if (((JButton) e.getSource()).getName() == "confirmBtnN") {
            ((JButton) e.getSource()).setIcon(new ImageIcon("resource\\images\\icon\\button\\confirm11.jpg"));
        } else if (e.getSource() == retreatBtn) {
            retreatBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\retreat1.jpg"));
        } else if (e.getSource() == rollBtn) {
            rollBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\roll1.jpg"));
        } else if (e.getSource() == againBtn) {
            againBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\again1.jpg"));
        } else if (e.getSource() == menuBtn) {
            menuBtn.setIcon(menuBtnImageIc1);
        } else if (((JButton) e.getSource()).getName() == "menuBtnForShowWinner") {
            ((JButton) e.getSource()).setIcon(new ImageIcon("resource\\images\\icon\\button\\menu1.jpg"));
        } else {
            for (int i = 0; i < mapGUI.getLandsBtn().length; i++)
                for (int j = 0; j < mapGUI.getLandsBtn()[i].length; j++) {
                    if (e.getSource() == mapGUI.getLandsBtn()[i][j]) {
                        selectedLandJL.removeAll();
                        selectedLandJL.revalidate();
                        selectedLandJL.repaint();
                        selectedLandJL.setIcon(selectedLandImageIc);
                    }
                }
        }
    }
}
