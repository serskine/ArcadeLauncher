package launcher.jinput;

import launcher.GameId;
import launcher.framework.Game;
import launcher.framework.controls.ArcadeControls;
import launcher.framework.controls.jinput.JoystickState;
import launcher.framework.controls.machine.dummy.DummyGameControls;
import launcher.framework.controls.state.AxisState;
import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.draw.Draw;
import launcher.framework.draw.widget.TextWidget;
import launcher.framework.util.Logger;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class JInputGame extends Game {

    public static final int TEXT_SIZE = 20;
    public static final Color COLOR_LINE = Color.RED;
    public static final Color COLOR_FILL = Color.YELLOW;

    public static final Color COLOR_BACKGROUND = Color.BLACK;

    private JInputGameState gameState = null;
    private TextWidget envWidget;
    private TextWidget controllerWidget;

    private TextWidget componentWidget;
            
    public JInputGame() {
        super(GameId.JINPUT);

        this.envWidget = new TextWidget(this);
        this.controllerWidget = new TextWidget(this);
        this.componentWidget = new TextWidget(this);

    }

    public JInputGameState getGameState() {
        if (gameState == null) {
            this.gameState = new JInputGameState(getControls().getListener().getControllerEnvironment());
        }
        return this.gameState;

    }

    @Override
    public ArcadeControls getControls() {
        return new DummyGameControls();
    }

    @Override
    public void onRender(Graphics2D g) {
        final Dimension screenSize = getScreenSize();
        Draw.fillRect(g, new Rectangle(0, 0, screenSize.width, screenSize.height), COLOR_BACKGROUND);

       
        envWidget.onRender(g);
        controllerWidget.onRender(g);
        componentWidget.onRender(g);
    }


    @Override
    public void onTick() {

        try {
            final JInputGameState gameState = getGameState();
            gameState.poll();

            final JoystickState joystickState = gameState.getGameJoystickState();
            final ButtonJoystickState buttonJoystickState = joystickState.getClosestButtonState();

            if (gameState.isStickToggle() == true && (buttonJoystickState == ButtonJoystickState.NEUTRAL || buttonJoystickState == ButtonJoystickState.ERROR)) {
                gameState.setStickToggle(false);
            } else {
                gameState.setStickToggle(true);
                AxisState yAxis = buttonJoystickState.yAxis;
                AxisState xAxis = buttonJoystickState.xAxis;

                switch (xAxis) {
                    case POSITIVE:
                        gameState.incrementController();
                        break;
                    case NEGATIVE:
                        gameState.decrementController();
                        break;
                    case NEUTRAL:
                        break;
                    case ERROR:
                        break;
                }

                switch (yAxis) {
                    case POSITIVE:
                        gameState.decrementComponent();
                        break;
                    case NEGATIVE:
                        gameState.incrementComponent();
                        break;
                    case NEUTRAL:
                        break;
                    case ERROR:
                        break;
                }
            }

        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
        }

        final JInputGameState gameState = getGameState();

        final String envDesc = gameState.getEnvironmentDescription();
        final Dimension envSize = Draw.getTextSize(TEXT_SIZE, envDesc);

        final String controllerDesc = gameState.getControllerDescription();
        final Dimension controllerSize = Draw.getTextSize(TEXT_SIZE, controllerDesc);

        final String componentDesc = gameState.getComponentDescription();
        final Dimension2D componentSize = Draw.getTextSize(TEXT_SIZE, componentDesc);

        final int x0 = TEXT_SIZE;
        final int x1 = x0 + envSize.width + TEXT_SIZE;
        final int x2 = x1 + controllerSize.width + TEXT_SIZE;

        final int y0 = TEXT_SIZE;
        final int y1 = TEXT_SIZE;
        final int y2 = TEXT_SIZE;

        envWidget.setText(envDesc);
        controllerWidget.setText(controllerDesc);
        componentWidget.setText(componentDesc);

        envWidget.setRelativeLocation(new Point(x0, y0));
        controllerWidget.setRelativeLocation(new Point(x1, y1));
        componentWidget.setRelativeLocation(new Point(x2, y2));



    }

    private void renderText(Graphics2D g, int x, int y, String text) {
        Draw.renderRetroText(g, new Point(x,y), TEXT_SIZE, COLOR_LINE, COLOR_FILL, text);
    }


    Point getTileLocation(int tx, int ty) {
        return new Point(tx*TEXT_SIZE, ty*TEXT_SIZE);
    }
    @Override
    protected boolean isGameOver() {
        return false;
    }
}
        
        
