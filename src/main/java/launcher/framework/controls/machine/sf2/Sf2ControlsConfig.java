package launcher.framework.controls.machine.sf2;

import launcher.framework.controls.state.VirtualKey;
import launcher.framework.controls.virtual.ControllersConfig;
import launcher.framework.controls.virtual.ControllerConfig;
import launcher.framework.util.Logger;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class Sf2ControlsConfig extends ControllersConfig<Sf2ControllerId, Sf2ButtonId, Sf2JoystickId> {

    public Sf2ControlsConfig() {
        super();

        // Add player one controls

        final ControllerConfig<Sf2ButtonId, Sf2JoystickId> playerConfig1 = addController(Sf2ControllerId.PLAYER_1);

        playerConfig1.buttonsConfig.bindButton(VirtualKey.VK_T, Sf2ButtonId.PUNCH_1);
        playerConfig1.buttonsConfig.bindButton(VirtualKey.VK_Y, Sf2ButtonId.PUNCH_2);
        playerConfig1.buttonsConfig.bindButton(VirtualKey.VK_U, Sf2ButtonId.PUNCH_3);
        playerConfig1.buttonsConfig.bindButton(VirtualKey.VK_G, Sf2ButtonId.KICK_1);
        playerConfig1.buttonsConfig.bindButton(VirtualKey.VK_H, Sf2ButtonId.KICK_2);
        playerConfig1.buttonsConfig.bindButton(VirtualKey.VK_J, Sf2ButtonId.KICK_3);
        playerConfig1.buttonsConfig.bindButton(VirtualKey.VK_1, Sf2ButtonId.PLAYER_COIN);
        playerConfig1.buttonsConfig.bindButton(VirtualKey.VK_1, Sf2ButtonId.PLAYER_START);

        playerConfig1.joysticksConfig.bindStick(
            Sf2JoystickId.PLAYER_JOYSTICK,
            VirtualKey.VK_W,
            VirtualKey.VK_D,
            VirtualKey.VK_S,
            VirtualKey.VK_A
        );

        // Add player two controls
        final ControllerConfig<Sf2ButtonId, Sf2JoystickId> playerConfig2 = addController(Sf2ControllerId.PLAYER_2);

        playerConfig2.buttonsConfig.bindButton(VirtualKey.VK_INSERT, Sf2ButtonId.PUNCH_1);
        playerConfig2.buttonsConfig.bindButton(VirtualKey.VK_HOME, Sf2ButtonId.PUNCH_2);
        playerConfig2.buttonsConfig.bindButton(VirtualKey.VK_PAGE_UP, Sf2ButtonId.PUNCH_3);
        playerConfig2.buttonsConfig.bindButton(VirtualKey.VK_DELETE, Sf2ButtonId.KICK_1);
        playerConfig2.buttonsConfig.bindButton(VirtualKey.VK_END, Sf2ButtonId.KICK_2);
        playerConfig2.buttonsConfig.bindButton(VirtualKey.VK_PAGE_DOWN, Sf2ButtonId.KICK_3);
        playerConfig2.buttonsConfig.bindButton(VirtualKey.VK_2, Sf2ButtonId.PLAYER_COIN);
        playerConfig2.buttonsConfig.bindButton(VirtualKey.VK_2, Sf2ButtonId.PLAYER_START);


        playerConfig2.joysticksConfig.bindStick(
                Sf2JoystickId.PLAYER_JOYSTICK,
                VirtualKey.VK_UP,
                VirtualKey.VK_RIGHT,
                VirtualKey.VK_DOWN,
                VirtualKey.VK_LEFT
        );

        // Add the jInputListener
        final ControllerEnvironment env = ControllerEnvironment.getDefaultEnvironment();
        if (env.getControllers().length<1) {
            Logger.warning("No controllers detected in the environment - no bindings performed.");
        } else {
            Controller player1 = env.getControllers()[0];
            Controller player2 = env.getControllers()[1];

            jInputListener.bindController(player1.getName(), Sf2ControllerId.PLAYER_1);
            jInputListener.bindController(player2.getName(), Sf2ControllerId.PLAYER_2);

            jInputListener.bindControllerStick(player1.getName(), Sf2JoystickId.PLAYER_JOYSTICK);
            jInputListener.bindControllerStick(player2.getName(), Sf2JoystickId.PLAYER_JOYSTICK);
        }
    }

}


