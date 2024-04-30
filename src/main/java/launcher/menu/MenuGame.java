package launcher.menu;

import launcher.GameId;
import launcher.debugger.DebuggerGame;
import launcher.framework.Game;
import launcher.framework.controls.ArcadeControls;
import launcher.framework.controls.jinput.RawJinputListener;
import launcher.framework.controls.machine.dummy.DummyGameControls;
import launcher.framework.controls.state.AxisState;
import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.controls.state.ButtonState;
import launcher.framework.draw.widget.CircleListWigit;
import launcher.framework.draw.widget.TextWidget;
import launcher.jinput.JInputGame;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import java.util.List;
import java.awt.*;
import java.awt.geom.Point2D;

public class MenuGame extends Game {

    public static final int TEXT_SIZE = 50;
    final MenuGameState gameState = new MenuGameState();
    public MenuGameState getGameState() {
        return this.gameState;
    }

    public CircleListWigit circleListWigit = new CircleListWigit(this);

    public MenuGame() {
        super(GameId.MENU);
        for(GameId gameId : GameId.values()) {
            if (gameId != GameId.MENU) {
                final TextWidget textWidget = new TextWidget(circleListWigit);
                textWidget.setText(gameId.name);
                circleListWigit.widgetList.add(textWidget);
            }
        }
        circleListWigit.setSelectedIndex(0);
    }


    @Override
    public ArcadeControls getControls() {
        return new DummyGameControls(new RawJinputListener());
    }

    @Override
    public void onRender(Graphics2D g) {
        final Dimension screenSize = getScreenSize();

        final Point2D prevPos = new Point2D.Double(TEXT_SIZE, screenSize.height/2);

    }

    @Override
    public void onTick() {
        final MenuGameState gameState = getGameState();

        if (!gameState.isCommit()) {

            final ArcadeControls arcadeControls = getControls();

            final RawJinputListener listener = arcadeControls.getListener();
            final List<Controller> controllers = listener.getControllersWithComponents(
                Component.Identifier.Axis.X,
                Component.Identifier.Axis.Y,
                Component.Identifier.Button._1,
                Component.Identifier.Button._2
            );


            final Controller player1 = (controllers.size()>0) ? controllers.get(0) : null;

            final ButtonJoystickState joystickState = listener.getButtonJoystickState(player1.getName(), Component.Identifier.Axis.X, Component.Identifier.Axis.Y);
            final AxisState vAxisState = joystickState.yAxis;
            final ButtonState selectButtonState = listener.getButtonState(player1.getName(), Component.Identifier.Button._0);
            final ButtonState backButtonState = listener.getButtonState(player1.getName(), Component.Identifier.Button._1);

            if (vAxisState == AxisState.NEUTRAL && gameState.isJoystickToggled()) {
                gameState.setJoystickToggled(false);
            } else if (!gameState.isJoystickToggled()) {
                if (vAxisState == AxisState.NEGATIVE) {
                    gameState.selectPrev();
                    gameState.setJoystickToggled(true);
                } else if (vAxisState == AxisState.POSITIVE) {
                    gameState.selectNext();
                    gameState.setJoystickToggled(true);
                }
            }

            if (backButtonState == ButtonState.PRESSED) {
                gameState.setQuit(true);
            } else if (selectButtonState == ButtonState.PRESSED) {
                gameState.setCommit(true);
                final GameId selectedGame = gameState.getSelectedGameId();
                final Game menuGame = createGame(selectedGame);
                if (menuGame != null) {
                    menuGame.run();
                }
                gameState.setCommit(false);
                gameState.setJoystickToggled(true);
            }
        }
    }

    private Game createGame(final GameId gameId) {
        Game game = null;
        switch(gameId) {
            case DEBUGGER:
                game = new DebuggerGame();
                break;
            case JINPUT:
                game = new JInputGame();
                break;
            case MENU:
            default:
                game = null;
                break;
        }
        return game;
    }



    @Override
    protected boolean isGameOver() {
        return getGameState().isQuit();
    }


}
