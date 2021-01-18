package RiskGame.UI;

import javax.swing.*;
import java.awt.*;

import static RiskGame.UI.Utils.*;

public class LoadingPage extends JPanel {

    private int i = 0; //for iterate into progress bar.
    private JProgressBar loadingProgressBar;
    private JLabel loadingImageJL;
    private ImageIcon loadingImageIc;
    private int valueOfProgressBar;

    public LoadingPage() {
        setBounds(0, 0, WIDTH_SCREEN, HEIGHT_SCREEN);
        setBackground(Color.black);

        //set image loading page
        loadingImageIc = new ImageIcon("resource\\images\\LoadingImage3.jpg");
        loadingImageJL = new JLabel();
        loadingImageJL.setIcon(loadingImageIc);
        add(loadingImageJL);

        //progress bar
        loadingProgressBar = new JProgressBar(0, 100);
        loadingProgressBar.setBounds((WIDTH_SCREEN / 2) - 250,950, 500, 70);
        loadingProgressBar.setValue(0);
        loadingProgressBar.setBackground(Color.PINK);
        loadingProgressBar.setForeground(Color.DARK_GRAY);
        loadingProgressBar.setStringPainted(true);
        loadingImageJL.add(loadingProgressBar);


    }

    public void iterate(){
        while (i <= 2000) {
            loadingProgressBar.setValue(i);
            i+=1;
            try {

                Thread.sleep(randomInteger(4, 300)); //get random integer for sleep.
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (loadingProgressBar.getValue() == 100)
                break;
        }
    }

    public int getValueOfProgressBar() {
        return loadingProgressBar.getValue();
    }
}
