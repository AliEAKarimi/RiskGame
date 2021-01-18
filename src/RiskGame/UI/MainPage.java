package RiskGame.UI;

import RiskGame.Logic.Controller;
import RiskGame.Logic.GameAction;
import RiskGame.Logic.Player;
import jaco.mp3.player.MP3Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Timer;

import static RiskGame.UI.Utils.*;
import static java.lang.Thread.sleep;

public class MainPage extends JPanel implements MouseListener, ActionListener {
    private GamePage gamePage;
    private Controller controller;
    private java.util.List<Player> players;
    private int playerCount;
    private GameAction gameAction;
    private GUI owner;

    private JButton playNowBtn;
    private ImageIcon playNowImageIc2;
    private ImageIcon playNowImageIc1;
    private String playNowBtnName = "playNowBtn";
    private Timer playNowBtnTimer;

    private JButton optionBtn;
    private ImageIcon optionImageIc1;
    private ImageIcon optionImageIc2;
    private String optionBtnName = "optionBtn";
    private Timer optionBtnTimer;

    private JButton rulesBtn;
    private ImageIcon rulesImageIc1;
    private ImageIcon rulesImageIc2;

    private JButton exitBtn;
    private ImageIcon exitImageIc1;
    private ImageIcon exitImageIc2;
    private String exitBtnName = "exitBtn";

    private JLabel backgroundJL;
    private ImageIcon backgroundIc;

    private JButton newGameBtn;
    private ImageIcon newGameImageIc1;
    private ImageIcon newGameImageIc2;
    private String newGameBtnName;
    private Timer newGameBtnTimer;

    private JButton loadGameBtn;
    private ImageIcon loadGameIc1;
    private ImageIcon loadGameIc2;
    private String loadGameBtnName;

    private JButton menuBtn;
    private ImageIcon menuImageIc1;
    private ImageIcon menuImageIc2;
    private String menuBtnName;

    private JButton backBtn;
    private ImageIcon backImageIc1;
    private ImageIcon backImageIc2;
    private String backBtnName;

    private JLabel playerCountJL;
    private String[] playerCountStr;
    private JRadioButton playerCountJRB1;
    private JRadioButton playerCountJRB2;
    private JRadioButton playerCountJRB3;
    private JTextField player1Name;
    private JTextField player2Name;
    private JTextField player3Name;
    private JTextField player4Name;
    //private JButton confirmBtn;
    private ImageIcon confirmImageIc1;
    private ImageIcon confirmImageIc2;
    private JLabel player1JL;
    private JLabel player2JL;
    private JLabel player3JL;
    private JLabel player4JL;
    private JButton startGameBtn;
    private ImageIcon startBtnImageIcon1;
    private ImageIcon startBtnImageIcon2;

    private JLabel mainPageJL;
    private ImageIcon mainPageJLImageIc;

    //for play sound
    private MP3Player mp3Player;
    public MainPage(GUI owner) {
        //mp3Player = new MP3Player(new File());
        //mp3Player.play();

        this.owner = owner;
        players = new ArrayList<>();
        initPanel();
        setMainPageImage();
        setButtons();
    }

    public void initPanel() {
        setBackground(Color.black);
        setBounds(0, 0, WIDTH_SCREEN, HEIGHT_SCREEN);

    }

    private void setMainPageImage() {
        mainPageJL = new JLabel();
        mainPageJLImageIc = new ImageIcon("resource\\images\\Logo1.jpg");
        mainPageJL.setIcon(mainPageJLImageIc);
        add(mainPageJL);
    }

    private void setButtons() {
        playNowBtn = new JButton();
        playNowImageIc1 = new ImageIcon("resource\\images\\icon\\button\\playNow1.jpg");
        playNowImageIc2 = new ImageIcon("resource\\images\\icon\\button\\playNow2.jpg");
        playNowBtn.setIcon(playNowImageIc1);
        playNowBtn.setBounds(1240, 480, playNowImageIc1.getIconWidth(), playNowImageIc1.getIconHeight());
        //playNowBtn.setEnabled();
        //Auxiliary.setOffBtn(playNowBtn);
        playNowBtn.setActionCommand(playNowBtnName);
        playNowBtn.addActionListener(this);
        playNowBtn.addMouseListener(this);
        playNowBtn.setEnabled(true);
        playNowBtn.setVisible(true);
        mainPageJL.add(playNowBtn);

        optionBtn = new JButton();
        optionImageIc1 = new ImageIcon("resource\\images\\icon\\button\\option1.jpg");
        optionImageIc2 = new ImageIcon("resource\\images\\icon\\button\\option2.jpg");
        optionBtn.setIcon(optionImageIc1);
        optionBtn.setBounds(1295, 580, optionImageIc1.getIconWidth(), optionImageIc1.getIconHeight());
        optionBtn.setActionCommand(optionBtnName);
        optionBtn.addMouseListener(this);
        optionBtn.addActionListener(this);
        optionBtn.setVisible(true);
        mainPageJL.add(optionBtn);

        rulesBtn = new JButton();
        rulesImageIc1 = new ImageIcon("resource\\images\\icon\\button\\Rules1.jpg");
        rulesImageIc2 = new ImageIcon("resource\\images\\icon\\button\\Rules2.jpg");
        rulesBtn.setIcon(rulesImageIc1);
        rulesBtn.setBounds(1350, 660, rulesImageIc1.getIconWidth(), rulesImageIc1.getIconHeight());
        rulesBtn.setActionCommand("rulesBtn");
        rulesBtn.addActionListener(this);
        rulesBtn.addMouseListener(this);
        rulesBtn.setVisible(true);
        mainPageJL.add(rulesBtn);

        exitBtn = new JButton();
        exitImageIc1 = new ImageIcon("resource\\images\\icon\\button\\quit1.jpg");
        exitImageIc2 = new ImageIcon("resource\\images\\icon\\button\\6.jpg");
        exitBtn.setIcon(exitImageIc1);
        exitBtn.setBounds(1400, 720, exitImageIc1.getIconWidth(), exitImageIc1.getIconHeight());
        exitBtn.setActionCommand(exitBtnName);
        exitBtn.addActionListener(this);
        exitBtn.addMouseListener(this);
        exitBtn.setVisible(true);
        mainPageJL.add(exitBtn);
    }

    public void playNowBtnAction() {
        mainPageJL.remove(rulesBtn);
        mainPageJL.remove(exitBtn);
        mainPageJL.remove(optionBtn);
        mainPageJL.revalidate();
        mainPageJL.repaint();

        playNowBtnTimer = new Timer(1, e -> Utils.moveVerticalComponent(mainPageJL, playNowBtn, 200, playNowBtnTimer));
        playNowBtnTimer.start();

        playNowBtn.setEnabled(false);


        newGameBtn = new JButton();
        newGameImageIc1 = new ImageIcon("resource\\images\\icon\\button\\newGame11.jpg");
        newGameImageIc2 = new ImageIcon("resource\\images\\icon\\button\\newGame22.jpg");
        newGameBtn.setIcon(newGameImageIc1);
        newGameBtn.setBounds(1290, 360, newGameImageIc1.getIconWidth(), newGameImageIc1.getIconHeight());
        newGameBtn.setActionCommand(newGameBtnName);
        newGameBtn.addActionListener(this);
        newGameBtn.addMouseListener(this);
        newGameBtn.setVisible(true);
        mainPageJL.add(newGameBtn);


        loadGameBtn = new JButton();
        loadGameIc1 = new ImageIcon("resource\\images\\icon\\button\\continue1.jpg");
        loadGameIc2 = new ImageIcon("resource\\images\\icon\\button\\continue2.jpg");
        loadGameBtn.setIcon(loadGameIc1);
        loadGameBtn.setBounds(1290, 460, loadGameIc1.getIconWidth(), loadGameIc1.getIconHeight());
        loadGameBtn.setActionCommand(loadGameBtnName);
        loadGameBtn.addActionListener(this);
        loadGameBtn.addMouseListener(this);
        loadGameBtn.setVisible(true);
        mainPageJL.add(loadGameBtn);

        menuBtn = new JButton();
        menuImageIc1 = new ImageIcon("resource\\images\\icon\\button\\menu1.jpg");
        menuImageIc2 = new ImageIcon("resource\\images\\icon\\button\\menu2.jpg");
        menuBtn.setIcon(menuImageIc1);
        menuBtn.setBounds(1370, 620, menuImageIc1.getIconWidth(), menuImageIc1.getIconHeight());
        menuBtn.setActionCommand(menuBtnName);
        menuBtn.addActionListener(this);
        menuBtn.addMouseListener(this);
        menuBtn.setVisible(true);
        mainPageJL.add(menuBtn);
        PTextField pTextField = new PTextField("Enter your name");

        drawBackground();
        mainPageJL.add(backgroundJL);

    }

    private void drawBackground() {
        backgroundJL = new JLabel();
        backgroundIc = new ImageIcon("resource\\images\\icon\\button\\background.jpg");
        backgroundJL.setIcon(backgroundIc);
        backgroundJL.setBounds(1270, 300, backgroundIc.getIconWidth(), backgroundIc.getIconHeight());
        backgroundJL.setVisible(true);
    }

    public void optionBtnAction() {

        JDialog optionDialog = new JDialog(owner, "", true);
        optionDialog.setLayout(null);
        optionDialog.setSize(500, 700);
        optionDialog.setLocation(600, 200);
        optionDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        optionDialog.setResizable(false);
        optionDialog.setUndecorated(true);

        JLabel optionLabel = new JLabel(new ImageIcon("resource\\images\\icon\\button\\background2.jpg"));
        optionLabel.setSize(500, 700);
        optionDialog.add(optionLabel);

        optionBtn.setEnabled(false);


        JButton backBtn = new JButton();
        backBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\back1.jpg"));
        backBtn.setName("optionBackButton");
        backBtn.addMouseListener(this);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionDialog.dispose();
                optionBtn.setEnabled(true);
            }
        });
        backBtn.setBounds(200, 610, 100, 50);
        optionLabel.add(backBtn);
        optionDialog.setVisible(true);
    }

    public void rulesBtnAction() {

        JDialog rulesDialog = new JDialog(owner, "", true);
        rulesDialog.setLayout(null);
        rulesDialog.setSize(500, 700);
        rulesDialog.setLocation(600, 200);
        rulesDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        rulesDialog.setResizable(false);
        rulesDialog.setUndecorated(true);

        JLabel rulesLabel = new JLabel(new ImageIcon("resource\\images\\icon\\button\\background2.jpg"));
        rulesLabel.setSize(500, 700);
        rulesDialog.add(rulesLabel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(50, 50, 400, 550);
        JLabel page1 = new JLabel();
        JLabel page2 = new JLabel();
        JLabel page3 = new JLabel();
        JLabel page4 = new JLabel();
        JLabel page5 = new JLabel();
        JLabel page6 = new JLabel();
        JLabel page7 = new JLabel();
        JLabel page8 = new JLabel();
        JLabel page9 = new JLabel();

        tabbedPane.add("1", page1);
        tabbedPane.add("2", page2);
        tabbedPane.add("3", page3);
        tabbedPane.add("4", page4);
        tabbedPane.add("5", page5);
        tabbedPane.add("6", page6);
        tabbedPane.add("7", page7);
        tabbedPane.add("8", page8);
        tabbedPane.add("9", page9);
        tabbedPane.setBackground(Color.GRAY);
        rulesLabel.add(tabbedPane);

        page1.setVerticalAlignment(JLabel.NORTH);
        ImageIcon page1Ic = new ImageIcon("resource\\images\\Rules\\mapForRules.png");
        page1.setIcon(page1Ic);
        JTextArea page1JTA = new JTextArea();
        page1JTA.setEnabled(false);
        page1JTA.setBounds(0, 275, 400, 275);
        page1JTA.setText("The object of Risk is to control all 29 territories of the world.Territories are claimed at the beginning of the game.On your turn,you can reinforce your territories,attack neighboring territories and fortify many territory for strategic purposes.\n");
        page1JTA.setLineWrap(true);
        //page1JTA.setSelectedTextColor();
        page1JTA.setBackground(Color.BLACK);
        page1JTA.setFont(new Font("Verdana", Font.ITALIC, 21));
        page1.add(page1JTA);

        page2.setVerticalAlignment(JLabel.NORTH);
        ImageIcon page2Ic = new ImageIcon("resource\\images\\Rules\\newGame.png");
        page2.setIcon(page2Ic);
        JTextArea page2JTA = new JTextArea();
        page2JTA.setEnabled(false);
        page2JTA.setBounds(0, 275, 400, 275);
        page2JTA.setText("You need at least 2 Players(Generals) to play the game, all of them must be a human General. Create your own to play against other people at the this computer. The order in which you select the Generals will be the order they appear in the game.");
        page2JTA.setLineWrap(true);
        //page2JTA.setSelectedTextColor();
        page2JTA.setBackground(Color.BLACK);
        page2JTA.setFont(new Font("Verdana", Font.ITALIC, 21));
        page2.add(page2JTA);

        page3.setVerticalAlignment(JLabel.NORTH);
        ImageIcon page3Ic = new ImageIcon("resource\\images\\Rules\\randomDeal.PNG");
        page3.setIcon(page3Ic);
        JTextArea page3JTA = new JTextArea();
        page3JTA.setEnabled(false);
        page3JTA.setBounds(0, 275, 400, 275);
        page3JTA.setText("With Random Deal, the computer assign lands to each player. Then, each player claims one land at a time by placing a soldier on its land. Once all lands are claimed, players reinforce their lands with any remaining soldiers and awarding soldiers.");
        page3JTA.setLineWrap(true);
        //page3JTA.setSelectedTextColor();
        page3JTA.setBackground(Color.BLACK);
        page3JTA.setFont(new Font("Verdana", Font.ITALIC, 21));
        page3.add(page3JTA);

        page4.setVerticalAlignment(JLabel.NORTH);
        ImageIcon page4Ic = new ImageIcon("resource\\images\\Rules\\soldiers.png");
        page4.setIcon(page4Ic);
        JTextArea page4JTA = new JTextArea();
        page4JTA.setEnabled(false);
        page4JTA.setBounds(0, 275, 400, 275);
        page4JTA.setText("At the start of each turn, you are awarded the following soldiers to reinforce your armies:\n" +
                "- 1 soldier for every 3 lands you control.\n" +
                "- Bonus soldiers for each continent you control.");
        page4JTA.setLineWrap(true);
        //page4JTA.setSelectedTextColor();
        page4JTA.setBackground(Color.BLACK);
        page4JTA.setFont(new Font("Verdana", Font.ITALIC, 21));
        page4.add(page4JTA);

        page5.setVerticalAlignment(JLabel.NORTH);
        ImageIcon page5Ic = new ImageIcon("resource\\images\\Rules\\attack.PNG");
        page5.setIcon(page5Ic);
        JTextArea page5JTA = new JTextArea();
        page5JTA.setEnabled(false);
        page5JTA.setBounds(0, 275, 400, 275);
        page5JTA.setText("Choose a land with 2 or more soldiers to attack an enemy in an adjacent land. Lands are adjacent if they share a border, or a sea-line runs between them. You may attack with up to 3 soldiers, but defend with a maximum of 2 soldiers.Roll the dice and compare your highest dice with your opponent`s to see who won. Ties are awarded to the defender.");
        page5JTA.setLineWrap(true);
        //page5JTA.setSelectedTextColor();
        page5JTA.setBackground(Color.BLACK);
        page5JTA.setFont(new Font("Verdana", Font.ITALIC, 18));
        page5.add(page5JTA);

        page6.setVerticalAlignment(JLabel.NORTH);
        ImageIcon page6Ic = new ImageIcon("resource\\images\\Rules\\roll.PNG");
        page6.setIcon(page6Ic);
        JTextArea page6JTA = new JTextArea();
        page6JTA.setEnabled(false);
        page6JTA.setBounds(0, 275, 400, 275);
        page6JTA.setText("You may end an attack at any time by pressing the Retreat button, and you may start an attack by pressing the Roll button, then you may click on the Again button for attack again.");
        page6JTA.setLineWrap(true);
        //page6JTA.setSelectedTextColor();
        page6JTA.setBackground(Color.BLACK);
        page6JTA.setFont(new Font("Verdana", Font.ITALIC, 21));
        page6.add(page6JTA);

        page7.setVerticalAlignment(JLabel.NORTH);
        page7.setBackground(Color.BLACK);
        JTextArea page7JTA = new JTextArea();
        page7JTA.setEnabled(false);
        page7JTA.setBounds(0, 275, 400, 275);
        page7JTA.setText("You win the invasion when you defeat the last defending soldier from a land. The soldiers used to win the battle automatically move to the land you invaded.");
        page7JTA.setLineWrap(true);
        //page7JTA.setSelectedTextColor();
        page7JTA.setBackground(Color.BLACK);
        page7JTA.setFont(new Font("Verdana", Font.ITALIC, 21));
        page7.add(page7JTA);

        page8.setVerticalAlignment(JLabel.NORTH);
        ImageIcon page8Ic = new ImageIcon("resource\\images\\Rules\\fortify.PNG");
        page8.setIcon(page8Ic);
        JTextArea page8JTA = new JTextArea();
        page8JTA.setEnabled(false);
        page8JTA.setBounds(0, 275, 400, 275);
        page8JTA.setText("After you have finished attacking, you get many 'free move' to fortify (or redeploy) soldiers from one land to another. To fortify your position, take as many soldiers as you`d like from one of your as land and move them to a connected land.Lands are connected if all the lands in between are also controlled by you. You must leave at least one soldier behind-you cannot abandon a land.");
        page8JTA.setLineWrap(true);
        //page8JTA.setSelectedTextColor();
        page8JTA.setBackground(Color.BLACK);
        page8JTA.setFont(new Font("Verdana", Font.ITALIC, 18));
        page8.add(page8JTA);

        page9.setVerticalAlignment(JLabel.NORTH);
        page9.setBackground(Color.BLACK);
        JTextArea page9JTA = new JTextArea();
        page9JTA.setEnabled(false);
        page9JTA.setBounds(0, 275, 400, 275);
        page9JTA.setText("Eliminate other players by defeating their last remaining land.");
        page9JTA.setLineWrap(true);
        //page9JTA.setSelectedTextColor();
        page9JTA.setBackground(Color.BLACK);
        page9JTA.setFont(new Font("Verdana", Font.ITALIC, 21));
        page9.add(page9JTA);


        JButton backBtn = new JButton();
        backBtn.setIcon(new ImageIcon("resource\\images\\icon\\button\\back1.jpg"));
        backBtn.setName("optionBackButton");
        backBtn.addMouseListener(this);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rulesDialog.dispose();
                rulesBtn.setEnabled(true);
            }
        });
        backBtn.setBounds(200, 610, 100, 50);
        rulesLabel.add(backBtn);

        rulesBtn.setEnabled(false);

        rulesDialog.setVisible(true);
    }

    public void exitBtnAction() {
        int a = JOptionPane.showConfirmDialog(owner, "Are you sure you want to quit this game?", "Quit", JOptionPane.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        if (a == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void newGameAction() {
        mainPageJL.remove(playNowBtn);
        mainPageJL.remove(loadGameBtn);
        mainPageJL.remove(menuBtn);
        mainPageJL.remove(backgroundJL);
        mainPageJL.invalidate();
        mainPageJL.repaint();

        newGameBtnTimer = new Timer(1, e -> Utils.moveVerticalComponent(mainPageJL, newGameBtn, 200, null));
        newGameBtnTimer.start();

        newGameBtn.setEnabled(false);

        backBtn = new JButton();
        backImageIc1 = new ImageIcon("resource\\images\\icon\\button\\back1.jpg");
        backImageIc2 = new ImageIcon("resource\\images\\icon\\button\\back2.jpg");
        backBtn.setIcon(backImageIc1);
        backBtn.setBounds(1300, 620, backImageIc1.getIconWidth(), backImageIc1.getIconHeight());
        backBtn.setActionCommand(backBtnName);
        backBtn.addActionListener(this);
        backBtn.addMouseListener(this);
        backBtn.setVisible(true);
        mainPageJL.add(backBtn);

        playerCountJL = new JLabel("Please select the count of players");
        playerCountJL.setBounds(1310, 300, 260, 50);
        playerCountJL.setFont(new Font("Verdana", Font.BOLD, 12));
        playerCountJL.setForeground(Color.WHITE);
        mainPageJL.add(playerCountJL);

        playerCountJRB1 = new JRadioButton("2");
        playerCountJRB1.setBounds(1310, 340, 35, 30);
        playerCountJRB1.addActionListener(this);
        playerCountJRB1.setBackground(Color.GRAY);
        mainPageJL.add(playerCountJRB1);
        playerCountJRB2 = new JRadioButton("3");
        playerCountJRB2.setBounds(1400, 340, 35, 30);
        playerCountJRB2.addActionListener(this);
        playerCountJRB2.setBackground(Color.GRAY);
        mainPageJL.add(playerCountJRB2);
        playerCountJRB3 = new JRadioButton("4");
        playerCountJRB3.setBounds(1490, 340, 35, 30);
        playerCountJRB3.addActionListener(this);
        playerCountJRB3.setBackground(Color.GRAY);
        mainPageJL.add(playerCountJRB3);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(playerCountJRB1);
        buttonGroup.add(playerCountJRB2);
        buttonGroup.add(playerCountJRB3);

        startGameBtn = new JButton();
        startGameBtn.addActionListener(this);
        startGameBtn.addMouseListener(this);
        startBtnImageIcon1 = new ImageIcon("resource\\images\\icon\\button\\start1.jpg");
        startBtnImageIcon2 = new ImageIcon("resource\\images\\icon\\button\\start2.jpg");
        startGameBtn.setIcon(startBtnImageIcon1);
        startGameBtn.setBounds(1440, 620, 100, 50);
        mainPageJL.add(startGameBtn);

        mainPageJL.add(backgroundJL);
    }

    public void showTextFieldPlayersName(int playerCount) {
        this.playerCount = playerCount;
        String player1NameStr = "", player2NameStr = "", player3NameStr = "", player4NameStr = "";
        if (player1Name != null) {
            player1NameStr = player1Name.getText();
            mainPageJL.remove(player1Name);
        }if(player1JL != null) mainPageJL.remove(player1JL);
        if (player2Name != null) {
            player2NameStr = player2Name.getText();
            mainPageJL.remove(player2Name);
        } if(player2JL != null) mainPageJL.remove(player2JL);
        if (player3Name != null) {
            player3NameStr = player3Name.getText();
            mainPageJL.remove(player3Name);
        }
        if (player3JL != null) mainPageJL.remove(player3JL);
        if (player4Name != null) {
            player4NameStr = player4Name.getText();
            mainPageJL.remove(player4Name);
        }
        if (player4JL != null) mainPageJL.remove(player4JL);

        String promptText = "Enter player's name";
        switch (playerCount) {
            case 4:
                player4Name = new PTextField(promptText);
                player4Name.setText(player4NameStr);
                player4Name.setBackground(Color.CYAN);
                player4Name.setBounds(1400, 550, 130, 30);
                player4Name.setDocument(new JTextFieldLimit(8));
                mainPageJL.add(player4Name);
                player4JL = new JLabel("player 4 : ");
                player4JL.setBounds(1300, 550, 100, 30);
                player4JL.setFont(new Font("Verdana", Font.BOLD, 13));
                player4JL.setForeground(Color.CYAN);
                mainPageJL.add(player4JL);
            case 3:
                player3Name = new PTextField(promptText);
                player3Name.setText(player3NameStr);
                player3Name.setBackground(Color.GREEN);
                player3Name.setBounds(1400, 500, 130, 30);
                player3Name.setDocument(new JTextFieldLimit(8));
                mainPageJL.add(player3Name);
                player3JL = new JLabel("player 3 : ");
                player3JL.setBounds(1300, 500, 100, 30);
                player3JL.setFont(new Font("Verdana", Font.BOLD, 13));
                player3JL.setForeground(Color.GREEN);
                mainPageJL.add(player3JL);
            case 2:
                player2Name = new PTextField(promptText);
                player2Name.setText(player2NameStr);
                player2Name.setBackground(Color.BLUE);
                player2Name.setBounds(1400, 450, 130, 30);
                player2Name.setDocument(new JTextFieldLimit(8));
                player1Name = new PTextField(promptText);
                player1Name.setText(player1NameStr);
                player1Name.setBackground(Color.RED);
                player1Name.setBounds(1400, 400, 130, 30);
                player1Name.setDocument(new JTextFieldLimit(8));
                mainPageJL.add(player2Name);
                mainPageJL.add(player1Name);
                player2JL = new JLabel("player 2 : ");
                player2JL.setBounds(1300, 450, 100, 30);
                player2JL.setFont(new Font("Verdana", Font.BOLD, 13));
                player2JL.setForeground(Color.BLUE);
                mainPageJL.add(player2JL);
                player1JL = new JLabel("player 1 : ");
                player1JL.setBounds(1300, 400, 100, 30);
                player1JL.setFont(new Font("Verdana", Font.BOLD, 13));
                player1JL.setForeground(Color.RED);
                mainPageJL.add(player1JL);
        }
        mainPageJL.add(backgroundJL);
    }


    public void backBtnAction() {
        mainPageJL.removeAll();
        mainPageJL.add(playNowBtn);
        newGameBtnTimer = new Timer(1, e -> Utils.moveVerticalComponent(mainPageJL, newGameBtn, 361, null));
        newGameBtnTimer.start();
        playNowBtnAction();
    }

    public void startGameBtnAction() {
        //mp3Player.stop();
        players = new ArrayList<>();
        //must written a method to check information be correct.not just space and ...
        switch (playerCount) {
            case 4:
                if (player4Name.getText().equals("") || player4Name.getText() == null)
                    players.add(new Player("player4", RiskGame.Logic.Color.CYAN));
                else players.add(new Player(player4Name.getText(), RiskGame.Logic.Color.CYAN));
            case 3:
                if (player3Name.getText().equals("") || player3Name.getText() == null)
                    players.add(new Player("player3", RiskGame.Logic.Color.GREEN));
                else players.add(new Player(player3Name.getText(), RiskGame.Logic.Color.GREEN));
            case 2:
                if (player2Name.getText().equals("") || player2Name.getText() == null)
                    players.add(new Player("player2", RiskGame.Logic.Color.BLUE));
                else players.add(new Player(player2Name.getText(), RiskGame.Logic.Color.BLUE));
                if (player1Name.getText().equals("") || player1Name.getText() == null)
                    players.add(new Player("player1", RiskGame.Logic.Color.RED));
                else players.add(new Player(player1Name.getText(), RiskGame.Logic.Color.RED));
        }
        Collections.reverse(players);
        gamePage = new GamePage(players, owner, this);

        Utils.changePanel(owner, gamePage);
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
        if (e.getSource() == playNowBtn) {
            playNowBtn.setIcon(playNowImageIc2);
        } else if (e.getSource() == optionBtn) {
            optionBtn.setIcon(optionImageIc2);
        } else if (e.getSource() == exitBtn) {
            exitBtn.setIcon(exitImageIc2);
        } else if (e.getSource() == rulesBtn) {
            rulesBtn.setIcon(rulesImageIc2);
        } else if (e.getSource() == newGameBtn) {
            newGameBtn.setIcon(newGameImageIc2);
        } else if (e.getSource() == loadGameBtn) {
            loadGameBtn.setIcon(loadGameIc2);
        } else if (e.getSource() == menuBtn) {
            menuBtn.setIcon(menuImageIc2);
        } else if (e.getSource() == backBtn) {
            backBtn.setIcon(backImageIc2);
        } /*else if (e.getSource() == confirmBtn) {
             confirmBtn.setIcon(confirmImageIc2);
        }*/ else if (e.getSource() == startGameBtn) {
            startGameBtn.setIcon(startBtnImageIcon2);
        } else if (((JButton) e.getSource()).getName().equals("optionBackButton")) {
            ((JButton) e.getSource()).setIcon(new ImageIcon("resource\\images\\icon\\button\\back2.jpg"));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == playNowBtn) {
            playNowBtn.setIcon(playNowImageIc1);
        } else if (e.getSource() == optionBtn) {
            optionBtn.setIcon(optionImageIc1);
        } else if (e.getSource() == exitBtn) {
            exitBtn.setIcon(exitImageIc1);
        } else if (e.getSource() == rulesBtn) {
            rulesBtn.setIcon(rulesImageIc1);
        } else if (e.getSource() == newGameBtn) {
            newGameBtn.setIcon(newGameImageIc1);
        } else if (e.getSource() == loadGameBtn) {
            loadGameBtn.setIcon(loadGameIc1);
        } else if (e.getSource() == menuBtn) {
            menuBtn.setIcon(menuImageIc1);
        } else if (e.getSource() == backBtn) {
            backBtn.setIcon(backImageIc1);
        } /*else if (e.getSource() == confirmBtn) {
            confirmBtn.setIcon(confirmImageIc1);
        }*/ else if (e.getSource() == startGameBtn) {
            startGameBtn.setIcon(startBtnImageIcon1);
        } else if (((JButton) e.getSource()).getName().equals("optionBackButton")) {
            ((JButton) e.getSource()).setIcon(new ImageIcon("resource\\images\\icon\\button\\back1.jpg"));
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionEventStr = e.getActionCommand();
        if (e.getSource() == playNowBtn) {
            playNowBtnAction();
        } else if (e.getSource() == newGameBtn) {
            newGameAction();
        } else if (e.getSource() == exitBtn) {
            exitBtnAction();
        } else if (e.getSource() == menuBtn) {
            Utils.changePanel(owner, new MainPage(owner));
        } else if (e.getSource() == backBtn) {
            backBtnAction();
        } else if (e.getSource() == loadGameBtn) {
        } else if (e.getSource() == startGameBtn) {
            startGameBtnAction();
        } else if (e.getSource() == playerCountJRB1) {
            showTextFieldPlayersName(2);
        } else if (e.getSource() == playerCountJRB2) {
            showTextFieldPlayersName(3);
        } else if (e.getSource() == playerCountJRB3) {
            showTextFieldPlayersName(4);
        } else if (e.getSource() == optionBtn) {
            optionBtnAction();
            System.out.println("option button clicked");
        } else if (actionEventStr.equals("rulesBtn")) {
            rulesBtnAction();
        }
    }
}


