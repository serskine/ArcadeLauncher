package launcher.framework.controls.virtual;

import launcher.framework.controls.state.VirtualKey;

import java.util.Set;

public class ControllerConfig<ButtonId, JoystickId> {

    public final ButtonsConfig<ButtonId> buttonsConfig;
    public final JoysticksConfig<JoystickId> joysticksConfig;

    public ControllerConfig(Set<VirtualKey> pressed) {
        buttonsConfig = new ButtonsConfig<>(pressed);
        joysticksConfig = new JoysticksConfig<>(buttonsConfig);
    }
}
