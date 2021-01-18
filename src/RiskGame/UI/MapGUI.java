package RiskGame.UI;

import RiskGame.Logic.Land;
import RiskGame.Logic.Map;
import RiskGame.Logic.Ocean;
import sun.util.resources.da.LocaleNames_da;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static RiskGame.UI.Utils.*;

public class MapGUI extends Map {

    private JPanel mapGUIJP;
    private String[][] addressMapIcons1;
    private String[][] addressMapIcons2;
    private JButton[][] lands;
    private String[][] landsName;

    //for Default map
    public MapGUI() {
        drawDefaultMap();
    }

    public void drawDefaultMap() {
        mapGUIJP = new JPanel();

        addressMapIcons1 = new String[][]{
                {"resource\\images\\gamePage\\Lands\\continent\\american\\canada1.png", "resource\\images\\gamePage\\Lands\\continent\\american\\unitedState1.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\denmarkMap1.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\polandMap1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\chinaMap1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\northKoreaMap1.png"},
                {"resource\\images\\gamePage\\Lands\\continent\\american\\mexico1.png", "resource\\images\\gamePage\\Lands\\continent\\american\\cuba1.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\belgiumMap1.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\germanyMap1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\turkeyMap1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\iranMap1.png"},
                {"resource\\images\\gamePage\\Lands\\continent\\american\\colombia1.png", "resource\\images\\gamePage\\Lands\\sea\\sea11.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\franceMap1.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\italyMap1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\iraqMap1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\pakistanMap1.png"},
                {"resource\\images\\gamePage\\Lands\\continent\\american\\peru1.png", "resource\\images\\gamePage\\Lands\\sea\\sea21.png", "resource\\images\\gamePage\\Lands\\sea\\sea22.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\egyptMap1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\indiaMap1.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\japanMap1.png"},
                {"resource\\images\\gamePage\\Lands\\continent\\american\\brazil1.png", "resource\\images\\gamePage\\Lands\\sea\\sea31.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\maliMap1.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\nigerMap1.png", "resource\\images\\gamePage\\Lands\\sea\\sea34.png", "resource\\images\\gamePage\\Lands\\sea\\sea35.png"},
                {"resource\\images\\gamePage\\Lands\\continent\\american\\chile1.png", "resource\\images\\gamePage\\Lands\\continent\\american\\argentina1.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\sudanMap1.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\kenyaMap1.png", "resource\\images\\gamePage\\Lands\\sea\\sea44.png", "resource\\images\\gamePage\\Lands\\sea\\sea55.png"},
                {"resource\\images\\gamePage\\Lands\\sea\\sea50.png", "resource\\images\\gamePage\\Lands\\sea\\sea51.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\zambiaMap1.png", "resource\\images\\gamePage\\Lands\\sea\\sea53.png", "resource\\images\\gamePage\\Lands\\sea\\sea55.png", "resource\\images\\gamePage\\Lands\\sea\\sea55.png"}
        };
        addressMapIcons2 = new String[][]{
                {"resource\\images\\gamePage\\Lands\\continent\\american\\canada2.png", "resource\\images\\gamePage\\Lands\\continent\\american\\unitedState2.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\denmarkMap2.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\polandMap2.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\chinaMap2.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\northKoreaMap2.png"},
                {"resource\\images\\gamePage\\Lands\\continent\\american\\mexico2.png", "resource\\images\\gamePage\\Lands\\continent\\american\\cuba2.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\belgiumMap2.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\germanyMap2.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\turkeyMap2.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\iranMap2.png"},
                {"resource\\images\\gamePage\\Lands\\continent\\american\\colombia2.png", "resource\\images\\gamePage\\Lands\\sea\\sea11.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\franceMap2.png", "resource\\images\\gamePage\\Lands\\continent\\europe\\italyMap2.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\iraqMap2.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\pakistanMap2.png"},
                {"resource\\images\\gamePage\\Lands\\continent\\american\\peru2.png", "resource\\images\\gamePage\\Lands\\sea\\sea21.png", "resource\\images\\gamePage\\Lands\\sea\\sea22.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\egyptMap2.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\indiaMap2.png", "resource\\images\\gamePage\\Lands\\continent\\asia\\japanMap2.png"},
                {"resource\\images\\gamePage\\Lands\\continent\\american\\brazil2.png", "resource\\images\\gamePage\\Lands\\sea\\sea31.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\maliMap2.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\nigerMap2.png", "resource\\images\\gamePage\\Lands\\sea\\sea34.png", "resource\\images\\gamePage\\Lands\\sea\\sea35.png"},
                {"resource\\images\\gamePage\\Lands\\continent\\american\\chile2.png", "resource\\images\\gamePage\\Lands\\continent\\american\\argentina2.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\sudanMap2.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\kenyaMap2.png", "resource\\images\\gamePage\\Lands\\sea\\sea44.png", "resource\\images\\gamePage\\Lands\\sea\\sea55.png"},
                {"resource\\images\\gamePage\\Lands\\sea\\sea50.png", "resource\\images\\gamePage\\Lands\\sea\\sea51.png", "resource\\images\\gamePage\\Lands\\continent\\africa\\zambiaMap2.png", "resource\\images\\gamePage\\Lands\\sea\\sea53.png", "resource\\images\\gamePage\\Lands\\sea\\sea55.png", "resource\\images\\gamePage\\Lands\\sea\\sea55.png"}
        };
        mapGUIJP.setBounds(WIDTH_SCREEN / 2 - 450, HEIGHT_SCREEN / 2 - 465, 900, 910);
        lands = new JButton[7][6];
        for (int i = 0; i < lands.length; i++) {
            for (int j = 0; j < lands[i].length; j++) {
                lands[i][j] = new JButton();
                String toolTipText = "";
                Object object = getSortedMap().get(i * 6 + j);
                if (object instanceof Land) {
                    toolTipText = ((Land) object).getName();
                } else if (object instanceof Ocean) {
                    toolTipText = ((Ocean) object).getName();
                }
                lands[i][j].setToolTipText(toolTipText);
                lands[i][j].setSize(150, 130);
                lands[i][j].setActionCommand(toolTipText);
                lands[i][j].setIcon(new ImageIcon(addressMapIcons1[i][j]));
                int finalI = i;
                int finalJ = j;
                lands[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseExited(e);
                        lands[finalI][finalJ].setIcon(new ImageIcon(addressMapIcons2[finalI][finalJ]));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseEntered(e);
                        lands[finalI][finalJ].setIcon(new ImageIcon((addressMapIcons1[finalI][finalJ])));
                    }

                });
                Utils.setOffBtn(lands[i][j]);
                lands[i][j].setVisible(true);
                mapGUIJP.add(lands[i][j]);
            }
        }
        mapGUIJP.setLayout(new GridLayout(7, 6));
    }

    public JPanel getMapGUIJP() {
        return mapGUIJP;
    }

    public JButton[][] getLandsBtn() {
        return lands;
    }

    public void landsMouseListener(MouseListener evt) {
        for (JButton[] landRow : lands) {
            for (JButton landColumn : landRow) {
                landColumn.addMouseListener(evt);
            }
        }
    }

    public void landsActionListener(ActionListener evt) {
        for (JButton[] landRow : lands) {
            for (JButton landColumn : landRow) {
                landColumn.addActionListener(evt);
            }
        }
    }
}
