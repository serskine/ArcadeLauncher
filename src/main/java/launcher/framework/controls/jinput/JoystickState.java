package launcher.framework.controls.jinput;

import launcher.framework.controls.state.AxisState;
import launcher.framework.controls.state.ButtonJoystickState;

public class JoystickState {

    public static final JoystickState ERROR = new JoystickState(true, 0D, 0D, 0D);
    public final double headingRadians;
    public final double magnitudePercentage;

    public final double deadZonePercentage;
    public final boolean isError;

    public JoystickState(
            final boolean isError,
            final double headingRadians,
            final double magnitudePercentage,
            final double deadZonePercentage
    ) {
        this.isError = isError;
        this.headingRadians = headingRadians;
        this.magnitudePercentage = magnitudePercentage;
        this.deadZonePercentage = deadZonePercentage;
    }

    public static final JoystickState create(AxisState xAxisState, AxisState yAxisState) {
        final boolean isError = xAxisState== AxisState.ERROR || yAxisState==AxisState.ERROR;
        final float dx = (xAxisState==AxisState.POSITIVE || xAxisState==AxisState.NEGATIVE) ? 1 : 0F;
        final float dy = (yAxisState==AxisState.POSITIVE || yAxisState==AxisState.NEGATIVE) ? 1 : 0F;
        final double headingRadians = Math.atan2(dy, dx);
        final double vectorMag = Math.sqrt((dx*dx) + (dy*dy));
        final double deadZoneMag = 0.25;

        return new JoystickState(isError, headingRadians, vectorMag, deadZoneMag);
    }

    public ButtonJoystickState getClosestButtonState() {
        if (isError) {
            return ButtonJoystickState.ERROR;
        }
        if (magnitudePercentage > deadZonePercentage) {
            double step = Math.PI/8D;
            if (headingRadians <= step) {
                return ButtonJoystickState.N;
            }
            step += Math.PI/4;
            if (headingRadians <= step) {
                return ButtonJoystickState.NE;
            }
            step += Math.PI/4;
            if (headingRadians <= step) {
                return ButtonJoystickState.E;
            }
            step += Math.PI/4;
            if (headingRadians <= step) {
                return ButtonJoystickState.SE;
            }
            step += Math.PI/4;
            if (headingRadians <= step) {
                return ButtonJoystickState.S;
            }
            step += Math.PI/4;
            if (headingRadians <= step) {
                return ButtonJoystickState.SW;
            }
            step += Math.PI/4;
            if (headingRadians <= step) {
                return ButtonJoystickState.W;
            }
            step += Math.PI/4;
            if (headingRadians <= step) {
                return ButtonJoystickState.NW;
            }
            step += Math.PI/4;
            if (headingRadians <= step) {
                return ButtonJoystickState.N;
            }
            return ButtonJoystickState.NEUTRAL;
        } else {
            return ButtonJoystickState.NEUTRAL;
        }
    }
}
