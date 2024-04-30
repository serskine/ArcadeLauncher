package launcher.framework.controls;

import launcher.framework.controls.jinput.JInputListener;
import launcher.framework.controls.jinput.JoystickConfig;
import launcher.framework.controls.jinput.JoystickState;
import launcher.framework.controls.jinput.RawJinputListener;
import launcher.framework.controls.state.ButtonState;
import net.java.games.input.Component;

import java.util.Set;

public interface ArcadeControls {
    default boolean isQuit() {
        final RawJinputListener listener = getListener();
        if (listener != null && listener.isButtonStateOnAnyController(Component.Identifier.Key.ESCAPE, ButtonState.PRESSED)) {
            return true;
        } else {
            return false;
        }
    }
    RawJinputListener getListener();

    void poll();
}
