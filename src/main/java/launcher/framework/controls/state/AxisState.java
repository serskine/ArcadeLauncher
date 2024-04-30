package launcher.framework.controls.state;

import launcher.framework.util.Logger;
import net.java.games.input.Component;

public enum AxisState {
    POSITIVE,
    NEGATIVE,
    NEUTRAL,
    ERROR;

    public static AxisState getComponentState(final Component component) {
        try {
            if (component.isAnalog()) {
                return getAnalogState(component.getPollData(), component.getDeadZone());
            } else {
                return getDigitalState(component.getPollData());
            }
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
            return AxisState.ERROR;
        }
    }

    public static AxisState getDigitalState(final float pollData) {
        return getAnalogState(pollData, 0F);
    }

    public static AxisState getAnalogState(final float pollData, final float deadZone) {
        if (deadZone<0) {
            return ERROR;
        } else if (Math.abs(pollData) <= deadZone) {
            return NEUTRAL;
        } else if (pollData < 0F) {
            return NEGATIVE;
        } else {
            return POSITIVE;
        }
    }

    public static boolean isAxis(Component.Identifier identifier) {
        return (identifier instanceof Component.Identifier.Axis);
    }

    public static boolean isAxisComponent(Component component) {
        return (component==null) ? false : isAxis(component.getIdentifier());
    }

    public static boolean isSame(AxisState a, AxisState b) {
        return (a==b);
    }
}
