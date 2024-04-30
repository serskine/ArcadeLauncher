package launcher.framework.controls.machine.pm;

import launcher.framework.controls.GameController;
import launcher.framework.controls.jinput.JoystickConfig;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PmController extends GameController<PmButtonId, PmAxisId, PmJoystickId> {

    public PmController(Controller controller) {
        super(controller, PmButtonId.values(), PmAxisId.values(), PmJoystickId.values());
    }

    @Override
    public List<Component.Identifier> getBoundAxisIdentifiers(PmAxisId pmAxisId) {
        switch(pmAxisId) {
            case AXIS_X:
                return Arrays.asList(Component.Identifier.Axis.X);
            case AXIS_Y:
                return Arrays.asList(Component.Identifier.Axis.Y);
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public List<Component.Identifier> getBoundButtonIdentifiers(PmButtonId buttonId) {
        switch(buttonId) {

            case START_ONE_PLAYER:
                return Arrays.asList(
                    Component.Identifier.Button.START,
                    Component.Identifier.Button._0,
                    Component.Identifier.Key._1
                );
            case START_TWO_PLAYERS:
                return Arrays.asList(
                        Component.Identifier.Button.SELECT,
                        Component.Identifier.Button._1,
                        Component.Identifier.Key._2
                );
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public JoystickConfig<PmAxisId> getBoundJoystickConfig(PmJoystickId pmJoystickId) {
        switch(pmJoystickId) {
            case JOYSTICK:
            default:
                return new JoystickConfig<>(PmAxisId.AXIS_X, PmAxisId.AXIS_Y);
        }
    }
}
