package launcher.framework.controls.jinput;

import launcher.framework.controls.state.ButtonJoystickState;

public class JoystickState {
    public final double headingRadians;
    public final double magnitudePercentage;

    public final boolean isError;

    public JoystickState(final boolean isError, final double headingRadians, final double magnitudePercentage) {
        this.isError = isError;
        this.headingRadians = headingRadians;
        this.magnitudePercentage = magnitudePercentage;
    }

    public ButtonJoystickState getClosestButtonState(double deadZoneMagnitude) {
        if (isError) {
            return ButtonJoystickState.ERROR;
        }
        if (magnitudePercentage > deadZoneMagnitude) {
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
