package RiskGame.Logic;

public class Ocean {
    private String name;
    private int ID;

    public Ocean(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getID() {
        return ID;
    }
}
