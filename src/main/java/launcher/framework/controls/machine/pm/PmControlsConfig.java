package launcher.framework.controls.machine.pm;

import launcher.framework.controls.state.VirtualKey;
import launcher.framework.controls.virtual.ControllersConfig;
import launcher.framework.controls.virtual.JoysticksConfig;
import launcher.framework.controls.virtual.ButtonsConfig;
import launcher.framework.controls.virtual.ControllerConfig;

public class PmControlsConfig extends ControllersConfig<PmControllerId, PmButtonId, PmJoystickId> {
    public PmControlsConfig() {
        super();

        // there is only one controller to bind
        final ControllerConfig<PmButtonId, PmJoystickId> controllerConfig = addController(PmControllerId.MAIN_CONTROLLER);

        final ButtonsConfig<PmButtonId> buttonsConfig = controllerConfig.buttonsConfig;
        buttonsConfig.bindButton(VirtualKey.VK_UP, PmButtonId.UP);
        buttonsConfig.bindButton(VirtualKey.VK_RIGHT, PmButtonId.RIGHT);
        buttonsConfig.bindButton(VirtualKey.VK_DOWN, PmButtonId.DOWN);
        buttonsConfig.bindButton(VirtualKey.VK_LEFT, PmButtonId.LEFT);

        final JoysticksConfig<PmJoystickId> joysticksConfig = controllerConfig.joysticksConfig;

        // There is only one joystick to bind.
        joysticksConfig.bindStick(PmJoystickId.JOYSTICK, VirtualKey.VK_UP, VirtualKey.VK_RIGHT, VirtualKey.VK_DOWN, VirtualKey.VK_LEFT);
    }
}
