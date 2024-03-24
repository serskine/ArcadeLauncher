package launcher.framework.controls.virtual;

import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.controls.state.ButtonState;
import launcher.framework.controls.state.VirtualKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class JoysticksConfig<JoystickId> {

    public final ButtonsConfig<?> buttonsConfig;

    private Map<JoystickId, ButtonJoystickConfig<VirtualKey>> joysticksConfig = new HashMap<>();
    public JoysticksConfig(ButtonsConfig<?> buttonsConfig) {
        this.buttonsConfig = buttonsConfig;
    }

    public final void bindStick(final JoystickId joystickId, final VirtualKey north, final VirtualKey east, final VirtualKey south, final VirtualKey west) {
        bindStick(joystickId, new ButtonJoystickConfig<VirtualKey>(north, east, south, west));
    }

    /**
     * This is a very common binding of a joystick to use the arrow keys
     * @param joystickId is the expected binding
     */
    public final void bindStickToArrowKeys(final JoystickId joystickId) {
        bindStick(joystickId, VirtualKey.VK_UP, VirtualKey.VK_RIGHT, VirtualKey.VK_DOWN, VirtualKey.VK_LEFT);
    }
    private void bindStick(final JoystickId joystickId, final ButtonJoystickConfig<VirtualKey> config) {
        joysticksConfig.put(joystickId, config);
    }



    public ButtonJoystickState getJoystickState(final JoystickId joystickId) {
        final ButtonJoystickConfig<VirtualKey> config = joysticksConfig.get(joystickId);
        if (config==null) {
            return ButtonJoystickState.ERROR;
        } else {
            final ButtonState northState = buttonsConfig.getKeyState(config.north);
            final ButtonState eastState = buttonsConfig.getKeyState(config.east);
            final ButtonState southState = buttonsConfig.getKeyState(config.south);
            final ButtonState westState = buttonsConfig.getKeyState(config.west);

            return ButtonJoystickState.getState(northState, eastState, southState, westState);
        }
    }

    public Set<JoystickId> getBoundJoysticks() {
        return joysticksConfig.keySet();
    }

    public boolean isBound(JoystickId joystickId) {
        return joysticksConfig.containsKey(joystickId);
    }

    public Optional<ButtonJoystickConfig<VirtualKey>> getBoundKeys(final JoystickId joystickId) {
        return Optional.ofNullable(joysticksConfig.get(joystickId));
    }
}


