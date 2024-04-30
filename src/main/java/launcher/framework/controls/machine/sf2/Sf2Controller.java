package launcher.framework.controls.machine.sf2;

import launcher.framework.controls.GameController;
import launcher.framework.controls.jinput.JoystickConfig;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sf2Controller extends GameController<Sf2ButtonId, Sf2AxisId, Sf2JoystickId> {
    public Sf2Controller(Controller controller) {
        super(controller, Sf2ButtonId.values(), Sf2AxisId.values(), Sf2JoystickId.values());
    }

    @Override
    public List<Component.Identifier> getBoundAxisIdentifiers(Sf2AxisId sf2AxisId) {
        switch(sf2AxisId) {
            case X_AXIS:
                return Arrays.asList(Component.Identifier.Axis.X);
            case Y_AXIS:
                return Arrays.asList(Component.Identifier.Axis.Y);
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public List<Component.Identifier> getBoundButtonIdentifiers(Sf2ButtonId sf2ButtonId) {
        switch(sf2ButtonId) {
            case PLAYER_COIN:
                return Arrays.asList(
                    Component.Identifier.Button.SELECT,
                    Component.Identifier.Button.EXTRA_1,
                    Component.Identifier.Key._1,
                    Component.Identifier.Button._1
                );
            case PLAYER_START:
                return Arrays.asList(
                    Component.Identifier.Button.START,
                    Component.Identifier.Button.EXTRA_2,
                    Component.Identifier.Key._2,
                    Component.Identifier.Button._2
                );
            case PUNCH_1:
                return Arrays.asList(
                        Component.Identifier.Button._0,
                        Component.Identifier.Button.Y,
                        Component.Identifier.Key.T,
                        Component.Identifier.Key.INSERT
                );
            case PUNCH_2:
                return Arrays.asList(
                        Component.Identifier.Button._1,
                        Component.Identifier.Button.X,
                        Component.Identifier.Key.Y,
                        Component.Identifier.Key.HOME
                );
            case PUNCH_3:
                return Arrays.asList(
                        Component.Identifier.Button._3,
                        Component.Identifier.Button.Z,
                        Component.Identifier.Key.U,
                        Component.Identifier.Key.PAGEUP
                );
            case KICK_1:
                return Arrays.asList(
                        Component.Identifier.Button._4,
                        Component.Identifier.Button.B,
                        Component.Identifier.Key.G,
                        Component.Identifier.Key.DELETE
                );
            case KICK_2:
                return Arrays.asList(
                        Component.Identifier.Button._5,
                        Component.Identifier.Button.A,
                        Component.Identifier.Key.H,
                        Component.Identifier.Key.END
                );
            case KICK_3:
                return Arrays.asList(
                        Component.Identifier.Button._6,
                        Component.Identifier.Button.C,
                        Component.Identifier.Key.J,
                        Component.Identifier.Key.PAGEDOWN
                );
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public JoystickConfig<Sf2AxisId> getBoundJoystickConfig(Sf2JoystickId sf2JoystickId) {
        switch(sf2JoystickId) {

            case PLAYER_JOYSTICK:
            default:
                return new JoystickConfig<>(Sf2AxisId.X_AXIS, Sf2AxisId.Y_AXIS);
        }
    }
}
