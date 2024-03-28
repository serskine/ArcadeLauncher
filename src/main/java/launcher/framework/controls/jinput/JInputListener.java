package launcher.framework.controls.jinput;

import launcher.framework.controls.ArcadeControls;
import launcher.framework.controls.state.ButtonState;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.*;

public class JInputListener<ControllerId, ButtonId, JoystickId> extends RawJinputListener implements ArcadeControls<ControllerId, ButtonId, JoystickId> {

    protected final Map<ControllerId, String> controllerMap = new HashMap<>();

    protected final Map<JoystickId, JoystickConfig> joystickConfigMap = new HashMap<>();

    protected final Map<ButtonId, Component.Identifier> buttonMap = new HashMap<>();


    public final void bingController(ControllerId controllerId, final String name) {
        controllerMap.put(controllerId, name);
    }

    public final void bindJoystick(JoystickId joystickId, Component.Identifier.Axis axisX, Component.Identifier.Axis axisY) {
        final JoystickConfig joystickConfig = new JoystickConfig(axisX, axisY);
        joystickConfigMap.put(joystickId, joystickConfig);
    }

    public final void bindButton(ButtonId buttonId, Component.Identifier buttonIdentifier) {
        if (    buttonIdentifier instanceof Component.Identifier.Button
            ||  buttonIdentifier instanceof Component.Identifier.Key
        ) {
            buttonMap.put(buttonId, buttonIdentifier);
        }
    }

    public final ButtonState getButtonState(ControllerId controllerId, ButtonId buttonId) {
        final String controllerName = controllerMap.get(controllerId);
        if (controllerName == null) {
            return ButtonState.ERROR;
        }

        final Component.Identifier buttonIdentifier = buttonMap.get(buttonId);
        if (buttonIdentifier==null) {
            return ButtonState.ERROR;
        } else {
            return getButtonState(controllerName, buttonIdentifier);
        }
    }

    public final JoystickState getJoystickState(ControllerId controllerId, JoystickId joystickId) {
        final String controllerName = controllerMap.get(controllerId);
        if (controllerName == null) {
            return JoystickState.ERROR;
        }

        final JoystickConfig joystickConfig = joystickConfigMap.get(joystickId);
        if (joystickConfig==null) {
            return JoystickState.ERROR;
        } else {
            return super.getJoystickState(controllerName, joystickConfig.xAxis, joystickConfig.yAxis);
        }
    }

    @Override
    public Set<ControllerId> getBoundControllers() {
        return controllerMap.keySet();
    }

    @Override
    public boolean isQuit() {
        for(Controller controller : super.getControllerEnvironment().getControllers()) {
            final ButtonState escapeButtonState = super.getButtonState(controller.getName(), Component.Identifier.Key.ESCAPE);
            if (escapeButtonState==ButtonState.PRESSED) {
                return true;    // Stop if any controller hits the panic button. :)
            }
        }
        return false;
    }

}
