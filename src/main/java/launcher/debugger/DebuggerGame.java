package launcher.debugger;

import launcher.GameId;
import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.controls.state.VirtualKey;
import launcher.framework.controls.virtual.ControllersConfig;
import launcher.framework.Game;
import launcher.framework.controls.state.ButtonState;
import launcher.framework.controls.machine.sf2.Sf2ButtonId;
import launcher.framework.controls.machine.sf2.Sf2ControllerId;
import launcher.framework.controls.machine.sf2.Sf2ControlsConfig;
import launcher.framework.controls.machine.sf2.Sf2JoystickId;
import launcher.framework.controls.virtual.ControllerConfig;
import launcher.framework.draw.Draw;
import launcher.framework.util.Text;

import java.awt.*;
import java.util.Arrays;

public class DebuggerGame extends Game<Void, Sf2ControllerId, Sf2ButtonId, Sf2JoystickId> {


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

    public DebuggerGame() {
        super(GameId.DEBUGGER);
    }

    private Sf2ControlsConfig controlsConfig;

    @Override
    protected Void createInitialGameState() {
        return null;
    }

    @Override
    protected ControllersConfig<Sf2ControllerId, Sf2ButtonId, Sf2JoystickId> getControlsConfig() {
        if (controlsConfig==null) {
            this.controlsConfig = new Sf2ControlsConfig();
        }
        return this.controlsConfig;
    }

    @Override
    public void onRender(Graphics2D g) {
        final Dimension screenSize = getScreenSize();

        final int width = screenSize.width;
        final int height = screenSize.height;

        g.setColor(COLOR_BACKGROUND);
        g.fillRect(0, 0, width, height);

        final String title = "Arcade Debugger";
        final Dimension textSize = Draw.getTextSize(g, TEXT_HEIGHT_TITLE, title);
        final int tX = (width-textSize.width)/2;
        final int tY = TEXT_HEIGHT_TITLE;

        final Point pTitle = new Point(tX, tY);
        Draw.renderRetroText(g, pTitle, TEXT_HEIGHT_TITLE, COLOR_ACCENT, COLOR_FOREGROUND, title);

        final int controlsWidth = MONITOR_AREA_WIDTH + UNIT + CONTROLS_AREA_WIDTH;
        final int hpad = (width - controlsWidth) / 2;
        final int vpad = pTitle.y + textSize.height + UNIT;

        final Point pMonitor = new Point(hpad, vpad);
        final Point pControls = new Point(pMonitor.x + MONITOR_AREA_WIDTH + UNIT, pMonitor.y);
        final Point pBindings = new Point(pMonitor.x, pControls.y + UNIT + CONTROLS_AREA_HEIGHT);

        final ControllersConfig<Sf2ControllerId, Sf2ButtonId, Sf2JoystickId> controlsConfig = getControlsConfig();
        renderMonitorState(g, pMonitor, screenSize);
        renderControlsState(g, pControls, controlsConfig);
        renderBindingsState(g, pBindings, controlsConfig);

    }

    private void renderMonitorState(Graphics2D g, Point p, Dimension screenSize) {
        int x = p.x;
        int y = p.y;
        int nW = MONITOR_AREA_WIDTH;
        int nH = MONITOR_AREA_HEIGHT;

        g.setColor(COLOR_ACTIVE);

        g.fillRect(x, y, nW, nH);

        g.setColor(COLOR_FOREGROUND);
        g.drawRect(x, y, nW, nH);

        final String wText = "" + screenSize.width;
        final String hText = "" + screenSize.height;
        final String opText = "x";


        Dimension wSize = Draw.getTextSize(g, TEXT_HEIGHT_MONITOR, wText);
        Dimension hSize = Draw.getTextSize(g, TEXT_HEIGHT_MONITOR, hText);
        Dimension opSize = Draw.getTextSize(g, TEXT_HEIGHT_MONITOR, opText);

        final int pWidthX = p.x + (MONITOR_AREA_WIDTH-wSize.width)/2;
        final int pOpX = p.x + (MONITOR_AREA_WIDTH-opSize.width)/2;
        final int pHeightX = p.x + (MONITOR_AREA_WIDTH-hSize.width)/2;

        final int innerHeight = MONITOR_AREA_HEIGHT - wSize.height - opSize.height - hSize.height - TEXT_HEIGHT_MONITOR - TEXT_HEIGHT_MONITOR;
        final int pWidthY = p.y + (MONITOR_AREA_HEIGHT - innerHeight) / 2;
        final int pOpY = pWidthY + wSize.height + TEXT_HEIGHT_MONITOR;
        final int pHeightY = pOpY + opSize.height + TEXT_HEIGHT_MONITOR;

        final Point pWidth = new Point(pWidthX, pWidthY);
        final Point pOp = new Point(pOpX, pOpY);
        final Point pHeight = new Point(pHeightX, pHeightY);

        Draw.renderRetroText(g, pWidth, TEXT_HEIGHT_MONITOR, COLOR_ACCENT, COLOR_FOREGROUND, wText);
        Draw.renderRetroText(g, pOp, TEXT_HEIGHT_MONITOR, COLOR_ACCENT, COLOR_FOREGROUND, opText);
        Draw.renderRetroText(g, pHeight, TEXT_HEIGHT_MONITOR, COLOR_ACCENT, COLOR_FOREGROUND, hText);

    }

    private void renderControlsState(Graphics2D g, Point p, ControllersConfig<Sf2ControllerId, Sf2ButtonId, Sf2JoystickId> controllersConfig) {
        final Point p1 = new Point(p.x, p.y);
        final Point p2 = new Point(p.x, p.y + UNIT + CONTROLLER_HEIGHT);

        ControllerConfig<Sf2ButtonId, Sf2JoystickId> player1Config = controllersConfig.getControllerConfig(Sf2ControllerId.PLAYER_1).get();

        renderPlayerControls(g,
                p1,
                player1Config.joysticksConfig.getJoystickState(Sf2JoystickId.PLAYER_JOYSTICK),
                player1Config.buttonsConfig.getButtonState(Sf2ButtonId.PUNCH_1),
                player1Config.buttonsConfig.getButtonState(Sf2ButtonId.PUNCH_2),
                player1Config.buttonsConfig.getButtonState(Sf2ButtonId.PUNCH_3),
                player1Config.buttonsConfig.getButtonState(Sf2ButtonId.KICK_1),
                player1Config.buttonsConfig.getButtonState(Sf2ButtonId.KICK_2),
                player1Config.buttonsConfig.getButtonState(Sf2ButtonId.KICK_3),
                player1Config.buttonsConfig.getButtonState(Sf2ButtonId.PLAYER_START),
                player1Config.buttonsConfig.getButtonState(Sf2ButtonId.PLAYER_COIN),
                ButtonState.ERROR
        );

        ControllerConfig<Sf2ButtonId, Sf2JoystickId> player2Config = controllersConfig.getControllerConfig(Sf2ControllerId.PLAYER_2).get();

        renderPlayerControls(g,
                p2,
                player2Config.joysticksConfig.getJoystickState(Sf2JoystickId.PLAYER_JOYSTICK),
                player2Config.buttonsConfig.getButtonState(Sf2ButtonId.PUNCH_1),
                player2Config.buttonsConfig.getButtonState(Sf2ButtonId.PUNCH_2),
                player2Config.buttonsConfig.getButtonState(Sf2ButtonId.PUNCH_3),
                player2Config.buttonsConfig.getButtonState(Sf2ButtonId.KICK_1),
                player2Config.buttonsConfig.getButtonState(Sf2ButtonId.KICK_2),
                player2Config.buttonsConfig.getButtonState(Sf2ButtonId.KICK_3),
                player2Config.buttonsConfig.getButtonState(Sf2ButtonId.PLAYER_START),
                player2Config.buttonsConfig.getButtonState(Sf2ButtonId.PLAYER_COIN),
                ButtonState.ERROR
        );

        final int padFlipper = UNIT;

        int flipperY = p2.y + UNIT + CONTROLLER_HEIGHT;

        final Point pLeftFlipper = new Point(p.x + padFlipper, flipperY);
        final Point pRightFlipper = new Point(p.x + CONTROLLER_WIDTH - padFlipper - FLIPPER_WIDTH, flipperY);

        renderFlipper(g, pLeftFlipper, ButtonState.ERROR);
        renderFlipper(g, pRightFlipper, ButtonState.ERROR);
    }

    public void renderPlayerControls(
            Graphics2D g,
            Point p,
            ButtonJoystickState buttonJoystickState,
            ButtonState topLeft,
            ButtonState topMiddle,
            ButtonState topRight,
            ButtonState bottomLeft,
            ButtonState bottomMiddle,
            ButtonState bottomRight,
            ButtonState startButton,
            ButtonState extraLeft,
            ButtonState extraRight
    ) {
        final int innerWidth = CONTROLLER_WIDTH - UNIT - UNIT;
        final int innerHeight = CONTROLLER_HEIGHT - UNIT - UNIT;
        final Point pInnerOrigin = new Point(p.x + UNIT, p.y + UNIT);
        final int joyPad = Math.min(innerHeight/2 - JOYSTICK_RADIUS, innerWidth/2 - JOYSTICK_RADIUS);
        final Point pJoy = new Point(pInnerOrigin.x + joyPad, pInnerOrigin.y + joyPad);

        g.setColor(COLOR_BACKGROUND);
        g.fillRect(p.x, p.y, CONTROLLER_WIDTH, CONTROLLER_HEIGHT);

        g.setColor(COLOR_FOREGROUND);
        g.drawRect(p.x, p.y, CONTROLLER_WIDTH, CONTROLLER_HEIGHT);

        final Point pStartButton = new Point(pInnerOrigin.x + JOYSTICK_DIAM + UNIT, pInnerOrigin.y + JOYSTICK_RADIUS - BUTTON_RADIUS);
        final Point pExtraLeft = new Point(pStartButton.x + COIN_SLOT_WIDTH + UNIT, pStartButton.y);
        final Point pExtraRight = new Point(pExtraLeft.x + BUTTON_DIAM + UNIT, pExtraLeft.y);

        final Point pTopLeftButton = new Point(pExtraRight.x + BUTTON_DIAM + UNIT, pInnerOrigin.y);
        final Point pTopMiddleButton = new Point(pTopLeftButton.x + BUTTON_DIAM + UNIT, pTopLeftButton.y);
        final Point pTopRightButton = new Point(pTopMiddleButton.x + BUTTON_DIAM + UNIT, pTopMiddleButton.y);

        final Point pBottomLeftButton = new Point(pTopLeftButton.x + UNIT, pTopLeftButton.y + BUTTON_DIAM + UNIT);
        final Point pBottomMiddleButton = new Point(pBottomLeftButton.x + BUTTON_DIAM + UNIT, pBottomLeftButton.y);
        final Point pBottomRightButton = new Point(pBottomMiddleButton.x + BUTTON_DIAM + UNIT, pBottomMiddleButton.y);

        renderJoystick(g, pJoy, buttonJoystickState);

        renderCoinSlot(g, pStartButton, startButton);

        renderButton(g, pExtraLeft, extraLeft);
        renderButton(g, pExtraRight, extraRight);

        renderButton(g, pTopLeftButton, topLeft);
        renderButton(g, pTopMiddleButton, topMiddle);
        renderButton(g, pTopRightButton, topRight);
        renderButton(g, pBottomLeftButton, bottomLeft);
        renderButton(g, pBottomMiddleButton, bottomMiddle);
        renderButton(g, pBottomRightButton, bottomRight);

    }

    private void renderButton(Graphics2D g, Point p, ButtonState buttonState) {
        g.setColor(buttonColor(buttonState));
        g.fillOval(p.x, p.y, BUTTON_DIAM, BUTTON_DIAM);

        g.setColor(COLOR_BACKGROUND);
        g.drawOval(p.x, p.y, BUTTON_DIAM, BUTTON_DIAM);
    }

    private Color buttonColor(ButtonState buttonState) {
        switch (buttonState) {
            case ERROR:
                return COLOR_ERROR;
            case RELEASED:
                return COLOR_FOREGROUND;
            case PRESSED:
                return COLOR_ACTIVE;
            default:
                throw new IllegalStateException("Unexpected value: " + buttonState);
        }
    }

    private void renderCoinSlot(Graphics2D g, Point p, ButtonState buttonState) {

        g.setColor(buttonColor(buttonState));
        g.fillRect(p.x, p.y, COIN_SLOT_WIDTH, COIN_SLOT_HEIGHT);

        final int innerPadding = COIN_SLOT_WIDTH/4;
        final int innerWidth = COIN_SLOT_WIDTH/6;
        final int innerHeight = COIN_SLOT_HEIGHT - (innerPadding * 2);
        final int innerX = p.x + COIN_SLOT_WIDTH - innerPadding - innerWidth;
        final int innerY = p.y + innerPadding;

        g.setColor(COLOR_BACKGROUND);
        g.fillRect(innerX, innerY, innerWidth, innerHeight);
    }

    private void renderJoystick(Graphics2D g, Point p, ButtonJoystickState buttonJoystickState) {

        final int innerRadius = JOYSTICK_RADIUS/4;
        final int innerDiam = innerRadius * 2;
        final int innerX = p.x + JOYSTICK_RADIUS - innerRadius;
        final int innerY = p.y + JOYSTICK_RADIUS - innerRadius;


        switch(buttonJoystickState) {
            case ERROR:
                g.setColor(Color.RED);
                break;
            case NEUTRAL:
                g.setColor(Color.YELLOW);
                break;
            default:
                g.setColor(Color.GREEN);
                break;
        }


        g.fillOval(p.x, p.y, JOYSTICK_DIAM, JOYSTICK_DIAM);

        g.setColor(COLOR_BACKGROUND);
        g.drawOval(p.x, p.y, JOYSTICK_DIAM, JOYSTICK_DIAM);


        final int unit = JOYSTICK_RADIUS;
        final int diag = (int) (unit * Math.sqrt(2D) / 2D);
        final int none = 0;

        final int N_START = 12;

        final Point p1 = new Point(p.x + JOYSTICK_RADIUS, p.y + JOYSTICK_RADIUS);

        g.setColor(COLOR_BACKGROUND);
        g.fillOval(innerX, innerY, innerDiam, innerDiam);

        final Point p2 = getJoystickEnd(p1, buttonJoystickState);
        final Stroke prevStroke = g.getStroke();
        final Stroke thickStroke = new BasicStroke(JOYSTICK_THICKNESS);
        g.setStroke(thickStroke);

        g.drawLine(p1.x, p1.y, p2.x, p2.y);

        g.setStroke(prevStroke);
    }

    Point getJoystickEnd(Point p1, ButtonJoystickState buttonJoystickState) {
        final int unit = JOYSTICK_RADIUS;
        final int diag = (int) (unit * Math.sqrt(2D) / 2D);
        final int none = 0;

        switch (buttonJoystickState) {
            case ERROR:     return p1;
            case NEUTRAL:   return p1;
            case N:         return new Point(p1.x + none, p1.y - unit);
            case NE:        return new Point(p1.x + diag, p1.y - diag);
            case E:         return new Point(p1.x + unit, p1.y - none);
            case SE:        return new Point(p1.x + diag, p1.y + diag);
            case S:         return new Point(p1.x + none, p1.y + unit);
            case SW:        return new Point(p1.x - diag, p1.y + diag);
            case W:         return new Point(p1.x - unit, p1.y + none);
            case NW:        return new Point(p1.x - diag, p1.y - diag);
            default:        return p1;
        }
    }

    private void renderFlipper(Graphics2D g, Point p, ButtonState leftFlipper) {

        switch(leftFlipper) {
            case RELEASED:
                g.setColor(COLOR_FOREGROUND);
                break;
            case PRESSED:
                g.setColor(COLOR_ACTIVE);
                break;
            case ERROR:
                g.setColor(COLOR_ERROR);
                break;
        }
        g.fillRect(p.x, p.y, FLIPPER_WIDTH, FLIPPER_HEIGHT);
    }

    private void renderBindingsState(Graphics2D g, Point p, ControllersConfig<Sf2ControllerId, Sf2ButtonId, Sf2JoystickId> controlsConfig) {
        final int numButtons = Sf2ControllerId.values().length * Sf2ButtonId.values().length;
        final int div = numButtons / 2;
        final int colLeftX = p.x;
        final int colRightX = p.x + BINDINGS_AREA_WIDTH/2;
        final int fieldWidth = 20;

        int slot = 0;
        for(Sf2ControllerId controllerId : Sf2ControllerId.values()) {
            for(Sf2ButtonId buttonId : Sf2ButtonId.values()) {
                final int bindingY = p.y + ((slot >= div)
                        ? (BINDING_HEIGHT * (slot - div))
                        : BINDING_HEIGHT * slot
                );
                Point pBinding;
                pBinding = (slot < div)
                        ? new Point(colLeftX, bindingY)
                        : new Point(colRightX, bindingY);

                final ControllerConfig<Sf2ButtonId, Sf2JoystickId> controllerConfig = controlsConfig.getControllerConfig(controllerId).get();
                final ButtonState buttonState = controllerConfig.buttonsConfig.getButtonState(buttonId);
                final String buttonName = Text.fString("" + controllerConfig.buttonsConfig.getBoundKey(buttonId).get(), fieldWidth);

                renderBinding(g, pBinding, buttonId, buttonName, buttonState);
                slot++;
            }
        }
    }
    private void renderBinding(Graphics2D g, Point p, Sf2ButtonId button, String boundKey, ButtonState buttonState) {
        final Color color = (boundKey == null || buttonState==ButtonState.ERROR)
                ?   COLOR_ERROR
                :   (buttonState == ButtonState.PRESSED)
                    ?   COLOR_ACTIVE
                    :   COLOR_FOREGROUND;
        final int bTextWidth = Arrays.stream(Sf2ButtonId.values()).sequential().map(b -> b.toString().length()).reduce(0, (l, r) -> Math.max(l,r));

        g.setColor(color);

        g.drawRect(p.x, p.y, BINDING_WIDTH, BINDING_HEIGHT);

        final String buttonText = button.toString();

        final String codeText = boundKey;

        final int pad = (BINDING_HEIGHT - TEXT_HEIGHT_BINDING) / 2;
        final Point pButton = new Point(p.x + pad, p.y + pad);

        final String outputText = Text.fString(Text.fString(buttonText, bTextWidth) + " : " + codeText, bTextWidth);
        Draw.renderRetroText(g, pButton, TEXT_HEIGHT_BINDING, null, color, outputText);
    }

    public static String describeBoundKey(VirtualKey virtualKey) {
        if (virtualKey == null) {
            return "<None>";
        }
        return virtualKey.toString();
    }



    @Override
    protected void onTick() {
    }

    @Override
    protected boolean isGameOver() {
        return false;
    }
}
