package launcher.menu;

import launcher.framework.controls.state.VirtualKey;
import launcher.framework.controls.virtual.ControllersConfig;
import launcher.framework.controls.virtual.ControllerConfig;

public class MenuControllersConfig extends ControllersConfig<MenuControllerId, MenuButtonId, MenuJoystickId> {


    MenuControllersConfig() {
        final ControllerConfig controllerConfig = addController(MenuControllerId.MAIN_CONTROLLER);

        controllerConfig.joysticksConfig.bindStickToArrowKeys(MenuJoystickId.JOYSTICK_ID);
        controllerConfig.buttonsConfig.bindButton(VirtualKey.VK_ENTER, MenuButtonId.SELECT);
        controllerConfig.buttonsConfig.bindButton(VirtualKey.VK_ESCAPE, MenuButtonId.BACK);

    }
}
