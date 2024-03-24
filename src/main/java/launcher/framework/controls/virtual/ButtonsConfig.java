package launcher.framework.controls.virtual;

import launcher.framework.controls.state.ButtonState;
import launcher.framework.controls.state.VirtualKey;

import java.util.*;

public class ButtonsConfig<ButtonId> {

    private final Map<ButtonId, VirtualKey> buttonMapping = new HashMap<>();

    private final Set<VirtualKey> pressed;

    public ButtonsConfig(final Set<VirtualKey> pressed) {
        this.pressed = pressed;
    }
    public void bindButton(VirtualKey key, ButtonId buttonId) {
        buttonMapping.put(buttonId, key);
    }

    public ButtonState getKeyState(VirtualKey key) {
        return (key==null) ? ButtonState.ERROR : (pressed.contains(key) ? ButtonState.PRESSED : ButtonState.RELEASED);
    }

    public ButtonState getButtonState(ButtonId buttonId) {
        final VirtualKey key = buttonMapping.get(buttonId);
        return getKeyState(key);
    }

    public Set<ButtonId> getBoundButtons() {
        return buttonMapping.keySet();
    }
    public boolean isBound(ButtonId buttonId) {
        return getBoundButtons().contains(buttonId);
    }
    public Optional<VirtualKey> getBoundKey(final ButtonId buttonId) {
        return Optional.ofNullable(buttonMapping.get(buttonId));
    }
}
