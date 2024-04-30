package launcher.framework.controls.jinput;

import launcher.framework.controls.state.AxisState;
import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.controls.state.ButtonState;
import net.java.games.input.Component;

public class JoystickState {

    public static final JoystickState ERROR = new JoystickState(true, 0D, 0D, 0D);
    public final double headingRadians;
    public final double magnitudePercentage;

    public final double deadZonePercentage;
    public final boolean isError;

    String description = null;

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

    public static final JoystickState axisState(AxisState xAxisState, AxisState yAxisState) {
        final boolean isError = xAxisState== AxisState.ERROR || yAxisState==AxisState.ERROR;
        final float dx = (xAxisState==AxisState.POSITIVE || xAxisState==AxisState.NEGATIVE) ? 1 : 0F;
        final float dy = (yAxisState==AxisState.POSITIVE || yAxisState==AxisState.NEGATIVE) ? 1 : 0F;
        final double headingRadians = Math.atan2(dy, dx);
        final double vectorMag = Math.sqrt((dx*dx) + (dy*dy));
        final double deadZoneMag = 0.25;

        final JoystickState joystickState = new JoystickState(isError, headingRadians, vectorMag, deadZoneMag);
        joystickState.description ="create(xAxisState=" + xAxisState + ", yAxisState=" + yAxisState + ")";

        return joystickState;
    }

    public static final JoystickState componentState(final Component xAxis, final Component yAxis) {
        final boolean isError = !AxisState.isAxisComponent(xAxis) || !AxisState.isAxisComponent(yAxis);
        if (isError) {
            return ERROR;
        } else {
            final float dx = (Math.abs(xAxis.getPollData()) <= xAxis.getDeadZone()) ? 0 : xAxis.getPollData();
            final float dy = (Math.abs(yAxis.getPollData()) <= yAxis.getDeadZone()) ? 0 : yAxis.getPollData();
            final double headingRadians = Math.atan2(dy, dx);
            final double vectorMag = Math.sqrt((dx * dx) + (dy * dy));
            final double deadZoneMag = 0.25;

            JoystickState joystickState = new JoystickState(isError, headingRadians, vectorMag, deadZoneMag);
            return joystickState;
        }
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

    @Override
    public String toString() {
        return (description==null)
            ? String.format(
                    "%s {headingRadians: %3f, magnitudePercentage: %3f, deadZonePercentage: %3f}",
                    isError,
                    headingRadians,
                    magnitudePercentage,
                    deadZonePercentage
                )
            :   description;
    }

    public static boolean isSimilar(JoystickState a, JoystickState b) {
        return ButtonJoystickState.isSame(a.getClosestButtonState(), b.getClosestButtonState());
    }


}
