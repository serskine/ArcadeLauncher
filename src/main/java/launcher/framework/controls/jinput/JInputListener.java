package launcher.framework.controls.jinput;

import launcher.framework.controls.ArcadeControls;
import launcher.framework.controls.state.ButtonState;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.*;

public class JInputListener<ControllerId, ButtonId, AxisId, JoystickId> implements ArcadeControls {

    public final RawJinputListener rawJinputListener;

    public JInputListener() {
        this(new RawJinputListener());
    }
    public JInputListener(final RawJinputListener rawJinputListener) {
        this.rawJinputListener = rawJinputListener;
    }

    protected final Map<ControllerId, String> controllerMap = new HashMap<>();

    protected final Map<JoystickId, JoystickConfig<AxisId>> joystickConfigMap = new HashMap<>();

    protected final Map<ButtonId, Component.Identifier> buttonMap = new HashMap<>();

    protected final Map<AxisId, Component.Identifier.Axis> axisMap = new HashMap<>();


    public final void bindController(ControllerId controllerId, final String name) {
        controllerMap.put(controllerId, name);
    }

    public final void bindJoystick(JoystickId joystickId, AxisId axisX, AxisId axisY) {
        final JoystickConfig joystickConfig = new JoystickConfig<AxisId>(axisX, axisY);
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
            return rawJinputListener.getButtonState(controllerName, buttonIdentifier);
        }
    }

    public final JoystickState getJoystickState(ControllerId controllerId, JoystickId joystickId) {
        final String controllerName = controllerMap.get(controllerId);
        if (controllerName == null) {
            return JoystickState.ERROR;
        }

        final JoystickConfig<AxisId> joystickConfig = joystickConfigMap.get(joystickId);
        if (joystickConfig==null) {
            return JoystickState.ERROR;
        } else {
            final Component.Identifier.Axis xAxisId = axisMap.getOrDefault(joystickConfig.xAxis, Component.Identifier.Axis.X);
            final Component.Identifier.Axis yAxisId = axisMap.getOrDefault(joystickConfig.xAxis, Component.Identifier.Axis.Y);
            return rawJinputListener.getJoystickState(controllerName, xAxisId, yAxisId);
        }
    }

    @Override
    public RawJinputListener getListener() {
        return this.rawJinputListener;
    }

    @Override
    public void poll() {
        getListener().poll();
    }
}
