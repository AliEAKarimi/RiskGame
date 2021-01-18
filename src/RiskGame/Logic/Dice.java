package RiskGame.Logic;

import RiskGame.UI.Utils;

public class Dice {
    public static int getValue() {
        return Utils.randomInteger(1, 6);
    }
}
