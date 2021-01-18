package RiskGame.UI;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private LoadingPage loadingPage;
    private MainPage mainPage;

    public GUI(){
        setTitle("Risk Game");
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        addLoadingPage();
        changeCursorDemo();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void changeCursorDemo() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("resource\\images\\21747.png");
        Cursor cursor = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), "img");
        this.setCursor(cursor);
    }
    private void addLoadingPage() {
        loadingPage = new LoadingPage();
        setLayout(null);
        add(loadingPage);
        setVisible(true);
        loadingPage.iterate();
        if(loadingPage.getValueOfProgressBar() == 100) {
            mainPage = new MainPage(this);
            Utils.changePanel(this, mainPage);
        }
    }
}

