package RiskGame.UI;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    private static Random random;
    private static JProgressBar loadingProgressBar;

    public static final int WIDTH_SCREEN = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int HEIGHT_SCREEN = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static int randomInteger(int min, int max) {
        random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }

    public static void setOffBtn(JButton button) {
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    public static void main(String[] args) {
        progressSleep();
    }
    public static void progressSleep() {
        int i = 0;
        loadingProgressBar = new JProgressBar(0, 100);
        loadingProgressBar.setBounds((WIDTH_SCREEN / 2) - 250,950, 500, 70);
        loadingProgressBar.setValue(0);
        loadingProgressBar.setBackground(Color.PINK);
        loadingProgressBar.setForeground(Color.DARK_GRAY);
        loadingProgressBar.setStringPainted(true);
        while (i <= 2000) {
            loadingProgressBar.setValue(i);
            i+=1;
            try {

                Thread.sleep(randomInteger(4, 6));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(loadingProgressBar.getValue());
            if (loadingProgressBar.getValue() == 100)
                break;
        }
    }

    public static void setOnBtn(JButton button) {
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
    }

    public static void changePanel(JFrame frame, JPanel newPanel) {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.add(newPanel);
        frame.setVisible(true);
    }

    public static void moveVerticalComponent(Component component1, Component component2, int destination, Timer timer) {
        if (component2.getY() < destination) {
            component2.setLocation(component2.getX(), component2.getY() + 1);
        } else {
            component2.setLocation(component2.getX(), component2.getY() - 1);
        }
        component1.invalidate();
        component1.repaint();
        if (component2.getY() == destination) {
            if(timer!=null)
                timer.stop();
        }
    }
}

class PTextField extends JTextField {
    public PTextField(final String promptText) {
        addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if(getText().isEmpty() || getText().equals("")) {
                    setText(promptText);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if(getText().equals(promptText)) {
                    setText("");
                }
            }
        });

    }

}

class JTextFieldLimit extends PlainDocument {
    private int limit;
    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    JTextFieldLimit(int limit, boolean upper) {
        super();
        this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}

class CircleButton extends JButton {

    private boolean mouseOver = false;
    private boolean mousePressed = false;

    public CircleButton(String text){
        super(text);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);

        MouseAdapter mouseListener = new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent me){
                if(contains(me.getX(), me.getY())){
                    mousePressed = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent me){
                mousePressed = false;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me){
                mouseOver = false;
                mousePressed = false;
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent me){
                mouseOver = contains(me.getX(), me.getY());
                repaint();
            }
        };

        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    private int getDiameter(){
        int diameter = Math.min(getWidth(), getHeight());
        return diameter;
    }

    @Override
    public Dimension getPreferredSize(){
        FontMetrics metrics = getGraphics().getFontMetrics(getFont());
        int minDiameter = 10 + Math.max(metrics.stringWidth(getText()), metrics.getHeight());
        return new Dimension(minDiameter, minDiameter);
    }

    @Override
    public boolean contains(int x, int y){
        int radius = getDiameter()/2;
        return Point2D.distance(x, y, getWidth()/2, getHeight()/2) < radius;
    }

    @Override
    public void paintComponent(Graphics g){

        int diameter = getDiameter();
        int radius = diameter/2;

        if(mousePressed){
            g.setColor(Color.LIGHT_GRAY);
        }
        else{
            g.setColor(Color.WHITE);
        }
        g.fillOval(getWidth()/2 - radius, getHeight()/2 - radius, diameter, diameter);

        if(mouseOver){
            g.setColor(Color.BLUE);
        }
        else{
            g.setColor(Color.BLACK);
        }
        g.drawOval(getWidth()/2 - radius, getHeight()/2 - radius, diameter, diameter);

        g.setColor(Color.BLACK);
        g.setFont(getFont());
        FontMetrics metrics = g.getFontMetrics(getFont());
        int stringWidth = metrics.stringWidth(getText());
        int stringHeight = metrics.getHeight();
        g.drawString(getText(), getWidth()/2 - stringWidth/2, getHeight()/2 + stringHeight/4);
    }
}
