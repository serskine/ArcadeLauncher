package launcher.framework.controls.state;

import launcher.framework.util.Logger;
import net.java.games.input.Component;

public enum ButtonState {
    PRESSED,
    RELEASED,
    ERROR;

    public static ButtonState getComponentState(final Component component) {
        try {
            if (component.isAnalog()) {
                return getAnalogState(component.getPollData(), component.getDeadZone());
            } else {
                return getDigitalState(component.getPollData());
            }
        } catch (Exception e) {
            Logger.warning(e.getMessage(), e);
            return ButtonState.ERROR;
        }
    }

    public static ButtonState getDigitalState(final float pollData) {
        return getAnalogState(pollData, 0F);
    }

    public static ButtonState getAnalogState(final float pollData, final float deadZone) {
        if (deadZone<0) {
            return ERROR;
        } else if (pollData <= deadZone) {
            return RELEASED;
        } else {
            return PRESSED;
        }
    }

    public static boolean isButton(Component.Identifier identifier) {
        return  (   (identifier instanceof Component.Identifier.Key)
                ||  (identifier instanceof Component.Identifier.Button)
                );
    }

    public static boolean isSame(ButtonState a, ButtonState b) {
        return (a==b);
    }
}
