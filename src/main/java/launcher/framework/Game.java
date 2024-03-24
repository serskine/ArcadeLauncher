package launcher.framework;

import launcher.GameId;
import launcher.framework.controls.state.VirtualKey;
import launcher.framework.controls.virtual.ControllersConfig;

import javax.swing.*;
import java.awt.*;

public abstract class Game<GameState, ControllerId, ButtonId, JoystickId> implements Runnable {

    public final GameId gameId;
    private JFrame frame;
    private DrawingPane drawingPane;

    private GameState gameState;

    final void pause(final long delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            // Do nothing. Just continue
        }
    }

    public Game(final GameId gameId) {
        this.gameId = gameId;
    }

    protected GameState getGameState() {
        return this.gameState;
    }

    protected abstract GameState createInitialGameState();

    protected abstract ControllersConfig<ControllerId, ButtonId, JoystickId> getControlsConfig();

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

        final ControllersConfig<ControllerId, ButtonId, JoystickId> controlsConfig = getControlsConfig();

        frame.add(drawingPane);
        frame.setVisible(true);

        frame.addKeyListener(controlsConfig);   // May no longer be needed now that I am using JInput
        frame.setFocusable(true);

        this.gameState = createInitialGameState();
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

    protected abstract void onTick();

    private final boolean isQuit() {
        return getControlsConfig().isKeyPressed(VirtualKey.VK_ESCAPE);
    }

    protected abstract boolean isGameOver();
    protected final Dimension getScreenSize() {
        return new Dimension(drawingPane.getSize());
    }

    protected final void rePaint() {
        drawingPane.repaint();
    }

}
