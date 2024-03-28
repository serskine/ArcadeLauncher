package launcher.framework.controls.state;

import launcher.framework.controls.jinput.JoystickState;

import static launcher.framework.controls.state.ButtonState.PRESSED;

public enum ButtonJoystickState {
    ERROR(AxisState.ERROR, AxisState.ERROR),
    NEUTRAL(AxisState.NEUTRAL, AxisState.NEUTRAL),
    N(AxisState.NEUTRAL, AxisState.POSITIVE),
    NE(AxisState.POSITIVE, AxisState.POSITIVE),
    E(AxisState.POSITIVE, AxisState.NEUTRAL),
    SE(AxisState.POSITIVE, AxisState.NEGATIVE),
    S(AxisState.NEUTRAL, AxisState.NEGATIVE),
    SW(AxisState.NEGATIVE, AxisState.NEGATIVE),
    W(AxisState.NEGATIVE, AxisState.ERROR),
    NW(AxisState.NEGATIVE, AxisState.POSITIVE);

    public final AxisState xAxis, yAxis;
    public final JoystickState joystickState;

    private ButtonJoystickState(AxisState xAxis, AxisState yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;

        this.joystickState = JoystickState.create(xAxis, yAxis);
    }

    private static boolean isErrorState(ButtonState buttonState) {
        return (buttonState==null || buttonState==ButtonState.ERROR);
    }
    public static ButtonJoystickState getState(final ButtonState n, final ButtonState e, final ButtonState s, final ButtonState w) {
        if (isErrorState(n) || isErrorState(e) || isErrorState(s) || isErrorState(w)) {
            return ERROR;
        } else if (n==PRESSED && s==PRESSED) {
            return ERROR;
        } else if (w==PRESSED && e==PRESSED) {
            return ERROR;
        } else if (n==PRESSED) {
            if (w==PRESSED) {
                return NW;
            } else if (e==PRESSED) {
                return NE;
            } else {
                return N;
            }
        } else if (s==PRESSED) {
            if (w==PRESSED) {
                return SW;
            } else if (e==PRESSED) {
                return SE;
            } else {
                return S;
            }
        } else if (w==PRESSED) {
            return W;
        } else if (e==PRESSED) {
            return E;
        } else {
            return NEUTRAL;
        }
    }

}
