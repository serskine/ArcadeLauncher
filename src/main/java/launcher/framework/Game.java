package launcher.framework;

import launcher.GameId;
import launcher.framework.controls.ArcadeControls;
import launcher.framework.controls.jinput.JInputListener;
import launcher.framework.draw.widget.Widget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public abstract class Game extends Widget implements Runnable, Sprite, Actor {

    public final GameId gameId;
    private JFrame frame;
    private DrawingPane drawingPane;
    
    final void pause(final long delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            // Do nothing. Just continue
        }
    }

    public Game(final GameId gameId) {
        this.gameId = gameId;
        this.game = this;
    }

    public abstract ArcadeControls getControls();

    private void onStart() {
        frame = new JFrame(gameId.name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();

        if (gameId.windowSize==null) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            final Dimension windowSize = gameId.windowSize;
            frame.setSize(gameId.windowSize);
            frame.setLocation((screenWidth - windowSize.width) / 2, (screenHeight - windowSize.height) / 2);
        }

        this.drawingPane = new DrawingPane(this);

        final ArcadeControls controlsConfig = getControls();

        frame.add(drawingPane);
        frame.setVisible(true);

        if (controlsConfig instanceof KeyListener) {
            frame.addKeyListener((KeyListener) controlsConfig);
        }
        frame.setFocusable(true);
    }

    @Override
    public final void run() {
        onStart();
        while(!isQuit() && !isGameOver()) {
            onTick();
            pause(100L);
            drawingPane.repaint();
        }
        onFinish();
    }

    private void onFinish() {
        frame.setVisible(false);
        frame.dispose();
        frame = null;
    }

    public abstract void onRender(Graphics2D g);

    public abstract void onTick();

    private boolean isQuit() {
        return getControls().isQuit();
    }

    protected abstract boolean isGameOver();
    @Override
    public final Dimension getScreenSize() {
        return new Dimension(drawingPane.getSize());
    }

    protected final void rePaint() {
        drawingPane.repaint();
    }

}
