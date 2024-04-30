package launcher.framework;

import launcher.framework.util.Logger;

import javax.swing.*;
import java.awt.*;

public class DrawingPane extends JPanel {

    private Game game;
    public DrawingPane(final Game game) {
        this.game = game;
    }

    public DrawingPane() {
        setPreferredSize(new Dimension(getScreenWidth(), getScreenHeight()));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            game.onRender((Graphics2D) g);
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
        }
    }

    private int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    private int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

}
