package launcher.framework.controls;

import launcher.framework.controls.jinput.JoystickConfig;
import launcher.framework.controls.jinput.JoystickState;
import launcher.framework.controls.state.ButtonState;
import net.java.games.input.Component;

import java.util.Set;

public interface ArcadeControls<ControllerId, ButtonId, JoystickId> {

    ButtonState getButtonState(ControllerId controllerId, ButtonId buttonId);

    JoystickState getJoystickState(ControllerId controllerId, JoystickId joystickId);

    Set<ControllerId> getBoundControllers();

    boolean isQuit();
}
