package launcher.menu;

import launcher.GameId;
import launcher.debugger.DebuggerGame;
import launcher.framework.Game;
import launcher.framework.controls.state.AxisState;
import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.controls.state.ButtonState;
import launcher.framework.controls.virtual.ControllersConfig;
import launcher.framework.draw.Draw;
import launcher.jinput.JInputGame;
import launcher.framework.controls.virtual.ControllerConfig;

import java.awt.*;
import java.awt.geom.Point2D;

public class MenuGame extends Game<MenuGameState, MenuControllerId, MenuButtonId, MenuJoystickId> {

    public static final int TEXT_SIZE = 50;
    ControllersConfig<MenuControllerId, MenuButtonId, MenuJoystickId> controllerConfig;

    public MenuGame() {
        super(GameId.MENU);
    }

    @Override
    protected MenuGameState createInitialGameState() {
        return new MenuGameState();
    }

    @Override
    protected ControllersConfig<MenuControllerId, MenuButtonId, MenuJoystickId> getControlsConfig() {
        if (this.controllerConfig==null) {
            this.controllerConfig = new MenuControllersConfig();
        }
        return this.controllerConfig;
    }

    @Override
    public void onRender(Graphics2D g) {
        final Dimension screenSize = getScreenSize();

        final GameId selectedGameId = getGameState().getSelectedGameId();
        final String prompt = "Select game";
        final String displayText = prompt + "\n" + selectedGameId.name;

        g.fillRect(0, 0, screenSize.width, screenSize.height);

        final Dimension displayTextSize = Draw.getTextSize(g, TEXT_SIZE, displayText);

        final int x = (screenSize.width - displayTextSize.width)/2;
        final int y = (screenSize.height - displayTextSize.height)/2;
        final Point2D location = new Point2D.Double(x, y);
        Draw.renderRetroText(g, location, TEXT_SIZE, Color.RED, Color.YELLOW, displayText);
    }

    @Override
    protected void onTick() {
        final MenuGameState gameState = getGameState();

        if (!gameState.isCommit()) {

            final ControllersConfig<MenuControllerId, MenuButtonId, MenuJoystickId> controllersConfig = getControlsConfig();
            final ControllerConfig<MenuButtonId, MenuJoystickId> controllerConfig = controllersConfig.getControllerConfig(MenuControllerId.MAIN_CONTROLLER).get();
            final ButtonJoystickState joystickState = controllerConfig.joysticksConfig.getJoystickState(MenuJoystickId.JOYSTICK_ID);
            final AxisState vAxisState = joystickState.yAxis;
            final ButtonState selectButtonState = controllerConfig.buttonsConfig.getButtonState(MenuButtonId.SELECT);
            final ButtonState backButtonState = controllerConfig.buttonsConfig.getButtonState(MenuButtonId.BACK);

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
