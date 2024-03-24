package launcher.framework;

import javax.swing.*;
import java.awt.*;

public class DrawingPane extends JPanel {

    private Game<?, ?, ?, ?> game;
    public DrawingPane(final Game game) {
        this.game = game;
//        this.addKeyListener(game.getControlsConfig());
    }

    public DrawingPane() {
        setPreferredSize(new Dimension(getScreenWidth(), getScreenHeight()));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.onRender((Graphics2D) g);
    }

    private int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    private int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

}
