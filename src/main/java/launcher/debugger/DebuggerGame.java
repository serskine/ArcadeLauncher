package launcher.debugger;

import launcher.GameId;
import launcher.framework.Game;
import launcher.framework.controls.ArcadeControls;
import launcher.framework.controls.GameController;
import launcher.framework.controls.GameControllerChangeDetector;
import launcher.framework.controls.GameControls;
import launcher.framework.controls.jinput.JoystickConfig;
import launcher.framework.controls.jinput.JoystickState;
import launcher.framework.controls.jinput.RawJinputListener;
import launcher.framework.controls.machine.sf2.*;
import launcher.framework.controls.state.AxisState;
import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.controls.state.ButtonState;
import launcher.framework.draw.widget.TextWidget;
import launcher.framework.draw.widget.common.ConsoleWidget;
import launcher.framework.draw.widget.common.ControllerWidget;
import launcher.framework.draw.widget.common.MonitorWidget;
import launcher.framework.util.Geom;
import launcher.framework.util.Logger;
import launcher.framework.util.VectorBuilder;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DebuggerGame extends Game {


    public static final Color COLOR_ERROR = Color.RED;
    public static final Color COLOR_ACTIVE = Color.GREEN;
    public static final Color COLOR_FOREGROUND = Color.YELLOW;
    public static final Color COLOR_BACKGROUND = Color.BLACK;
    public static final Color COLOR_ACCENT = Color.WHITE;


    public static final int UNIT = 18;

    public static final int FLIPPER_WIDTH = UNIT * 12;
    public static final int FLIPPER_HEIGHT = UNIT;
    public static final int JOYSTICK_RADIUS = UNIT*3;
    public static final int JOYSTICK_DIAM = JOYSTICK_RADIUS * 2;
    public static final int BUTTON_RADIUS = UNIT;
    public static final int BUTTON_DIAM = BUTTON_RADIUS * 2;
    public static final int COIN_SLOT_WIDTH = BUTTON_DIAM;
    public static final int COIN_SLOT_HEIGHT = COIN_SLOT_WIDTH * 3 / 2;
    public static final int CONTROLLER_HEIGHT = (2 * UNIT) + Math.max(
            JOYSTICK_DIAM,
            (2 * BUTTON_DIAM) + BUTTON_RADIUS + UNIT + FLIPPER_HEIGHT
    );
    public static final int CONTROLLER_WIDTH = UNIT + JOYSTICK_DIAM + (UNIT + BUTTON_DIAM) * 6 + UNIT + COIN_SLOT_WIDTH;

    public static final int CONTROLS_AREA_WIDTH = CONTROLLER_WIDTH;
    public static final int CONTROLS_AREA_HEIGHT = CONTROLLER_HEIGHT*2 + UNIT + FLIPPER_HEIGHT + UNIT;

    public static final float JOYSTICK_THICKNESS = 10f;
    public static final int MONITOR_AREA_HEIGHT = CONTROLS_AREA_HEIGHT;
    public static final int MONITOR_AREA_WIDTH = 3 * MONITOR_AREA_HEIGHT / 4;
    public static final int BINDINGS_AREA_WIDTH = MONITOR_AREA_WIDTH + UNIT + CONTROLS_AREA_WIDTH;

    public static final int BINDING_WIDTH = BINDINGS_AREA_WIDTH /2;
    public static final int BINDING_HEIGHT = UNIT*2;

    public static final int TEXT_HEIGHT_TITLE = UNIT*2;
    public static final int TEXT_HEIGHT_MONITOR = TEXT_HEIGHT_TITLE;
    public static final int TEXT_HEIGHT_BINDING = UNIT;


    final List<ControllerWidget> pWidgets = new ArrayList<>();
    public Sf2GameControls gameControls = new Sf2GameControls(new RawJinputListener());

    final MonitorWidget monitorWidget;

    final ConsoleWidget consoleWidget;

    final TextWidget titleWidget;

    private GameControllerChangeDetector changeDetector;


    public DebuggerGame() {
        super(GameId.DEBUGGER);

        titleWidget = new TextWidget(this);
        titleWidget.setText("Arcade Debugger");
        titleWidget.setTextHeight(TEXT_HEIGHT_TITLE);
        titleWidget.setFillColor(COLOR_FOREGROUND);
        titleWidget.setLineColor(COLOR_ACCENT);

        monitorWidget = new MonitorWidget(this);

        consoleWidget = new ConsoleWidget(this);
        consoleWidget.textWidget.setTextHeight(UNIT);
        consoleWidget.setMaxLines(15);

        changeDetector = new GameControllerChangeDetector();
        changeDetector.listeners.add(new GameControllerChangeDetector.Listener() {
            @Override
            public void onButtonChange(String controllerName, Object buttonId, ButtonState prevState, ButtonState currentState) {
//                logToConsole("" + buttonId + ": " + prevState + " => " + currentState);
            }

            @Override
            public void onAxisChange(String controllerName, Object axisId, AxisState prevState, AxisState currentState) {
//                logToConsole("" + axisId + ": " + prevState + " => " + currentState);
            }

            @Override
            public void onJoystickChange(String controllerName, Object joystickId, JoystickState prevState, JoystickState currentState) {
//                logToConsole("" + joystickId + ": " + prevState + " => " + currentState);
            }

            @Override
            public void onButtonJoystickChange(String controllerName, Object joystickId, ButtonJoystickState prevState, ButtonJoystickState currentState) {
//                logToConsole("" + joystickId + ": " + prevState + " => " + currentState);
            }

            @Override
            public void onComponentChange(String controllerName, Component.Identifier identifier, Object prevState, Object currentState) {
                logToConsole("" + identifier.getName() + ": " + prevState + " => " + currentState);
            }
        });
    }

    @Override
    public ArcadeControls getControls() {
        return this.gameControls;
    }

    @Override
    public void onRender(Graphics2D g) {
        final Dimension screenSize = getScreenSize();

        final int width = screenSize.width;
        final int height = screenSize.height;

        g.setColor(COLOR_BACKGROUND);
        g.fillRect(0, 0, width, height);

        titleWidget.onRender(g);
        monitorWidget.onRender(g);

        final ArcadeControls controlsConfig = getControls();
        final GameControls<Sf2ControllerId, Sf2ButtonId, Sf2AxisId, Sf2JoystickId> sf2GameControls = (Sf2GameControls) controlsConfig;


        for(ControllerWidget pWidget : pWidgets) {
            pWidget.onRender(g);
        }

        consoleWidget.onRender(g);

    }
    @Override
    public void onTick() {
        final Dimension screenSize = getScreenSize();
        final int contentWidth = MONITOR_AREA_WIDTH + UNIT + CONTROLLER_WIDTH;
        final int titleEdge = Geom.snap((screenSize.getWidth() - titleWidget.getTextSize().width) / 2d);
        final int westEdge = Geom.snap((screenSize.getWidth() - contentWidth) / 2d);

        monitorWidget.setMonitorSize(screenSize);

        // Determine the location of all the widgets

        titleWidget.setRelativeLocation(
                new VectorBuilder()
                        .translate(titleEdge, UNIT)
                        .buildPoint2D()
        );
        Rectangle2D r = titleWidget.getRelativeTextBounds();

        monitorWidget.setRelativeLocation(
                new VectorBuilder()
                        .translate(westEdge, r.getY())
                        .translate(0D, r.getHeight() + UNIT)
                        .buildPoint2D()
        );

        pWidgets.clear();

        final ArcadeControls controlsConfig = getControls();
        final GameControls<Sf2ControllerId, Sf2ButtonId, Sf2AxisId, Sf2JoystickId> sf2GameControls = (Sf2GameControls) controlsConfig;

        Point2D startLocation = new VectorBuilder()
                .translate(monitorWidget.getRelativeLocation().getX(), monitorWidget.getRelativeLocation().getY())
                .translate(MONITOR_AREA_WIDTH + UNIT, 0D)
                .buildPoint2D();

        List<GameController<Sf2ButtonId, Sf2AxisId, Sf2JoystickId>> supportedControllers = sf2GameControls.getSupportedControllers();
        for (int i = 0; i < supportedControllers.size(); i++) {
            GameController<Sf2ButtonId, Sf2AxisId, Sf2JoystickId> gameController = supportedControllers.get(i);

            ControllerWidget pWidget = new ControllerWidget(this);
            pWidget.setRelativeLocation(
                    new VectorBuilder()
                            .translate(startLocation.getX(), startLocation.getY())
                            .translate(0D, (UNIT + CONTROLLER_HEIGHT) * i)
                            .buildPoint2D()
            );

            if (i < Sf2ControllerId.values().length) {
                pWidget.setController(gameController);
            }

            pWidgets.add(pWidget);

        }

        consoleWidget.setRelativeLocation(new Point2D.Double(westEdge, screenSize.height - UNIT));
        consoleWidget.setRelativeLocation(
                new VectorBuilder()
                        .translate(monitorWidget.getRelativeLocation())
                        .translate(0D, MONITOR_AREA_HEIGHT + 2*UNIT)
                        .buildPoint2D()
        );

        final int consoleHeight = (int) (screenSize.height - consoleWidget.getRelativeLocation().getY() - UNIT);
        final int numLines = consoleHeight / UNIT;

        consoleWidget.setMaxLines(numLines);

        logControlChange(supportedControllers);
    }

    private void logControlChange(List<GameController<Sf2ButtonId, Sf2AxisId, Sf2JoystickId>> supportedControllers) {
        if (supportedControllers.isEmpty()) {
            logToConsole("No supported controllers");
        } else {
            final GameController<Sf2ButtonId, Sf2AxisId, Sf2JoystickId> controller0 = supportedControllers.get(0);

            changeDetector.update(controller0);
        }
    }


    @Override
    protected boolean isGameOver() {
        return false;
    }

    private void logToConsole(final String text) {
        if (consoleWidget!=null) {
            consoleWidget.append(text);
            Logger.info(text);
        }
    }
}
