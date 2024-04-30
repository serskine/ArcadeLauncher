package launcher.framework.controls.virtual;

import launcher.framework.controls.ArcadeControls;
import launcher.framework.controls.jinput.JInputListener;
import launcher.framework.controls.jinput.JoystickState;
import launcher.framework.controls.jinput.RawJinputListener;
import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.controls.state.ButtonState;
import launcher.framework.controls.state.VirtualKey;
import net.java.games.input.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class ControllersConfig<ControllerId, ButtonId, AxisId, JoystickId> implements KeyListener, ArcadeControls {

    private final Set<VirtualKey> pressedKeys = new HashSet<>();

    private final RawJinputListener jinputListener = new RawJinputListener();
    private Map<ControllerId, ControllerConfig<ButtonId, JoystickId>> configMap = new HashMap<>();
    public Set<ControllerId> getBoundControllers() {
        return configMap.keySet();
    }
    @Override
    public boolean isQuit() {
        return pressedKeys.contains(VirtualKey.VK_ESCAPE);
    }

    @Override
    public RawJinputListener getListener() {
        return jinputListener;
    }

    @Override
    public void poll() {
        jinputListener.poll();
    }


    public final JInputListener<ControllerId, ButtonId, AxisId, JoystickId> jInputListener = new JInputListener<>();

    public Optional<ControllerConfig<ButtonId, JoystickId>> getControllerConfig(ControllerId controllerId) {
        return Optional.ofNullable(configMap.get(controllerId));
    }

    /**
     * This will add a new controller config
     * @param controllerId
     * @return a reference to the newly created ControllerConfig
     */
    public final ControllerConfig<ButtonId, JoystickId> addController(ControllerId controllerId) {
        configMap.putIfAbsent(controllerId, new ControllerConfig<>(pressedKeys));
        return configMap.get(controllerId);
    }

    public final void removeController(ControllerId controllerId) {
        configMap.remove(controllerId);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        final Optional<VirtualKey> keyOpt = VirtualKey.parse(e.getKeyCode());
        if (keyOpt.isPresent()) {
            final VirtualKey virtualKey = keyOpt.get();
            pressKey(virtualKey);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        final Optional<VirtualKey> keyOpt = VirtualKey.parse(e.getKeyCode());
        if (keyOpt.isPresent()) {
            final VirtualKey virtualKey = keyOpt.get();
            releaseKey(virtualKey);
        }
    }

    public final void pressKey(VirtualKey virtualKey) {
        if (!pressedKeys.contains(virtualKey)) {
            pressedKeys.add(virtualKey);
        }
    }

    public final void releaseKey(VirtualKey virtualKey) {
        if (pressedKeys.contains(virtualKey)) {
            pressedKeys.remove(virtualKey);
        }
    }

    public final boolean isKeyPressed(VirtualKey virtualKey) {
        return pressedKeys.contains(virtualKey);
    }

    public ButtonState getButtonState(ControllerId controllerId, ButtonId buttonId) {
        final  ControllerConfig<ButtonId, JoystickId> controllerConfig = configMap.get(controllerId);
        if (controllerConfig==null) {
            return ButtonState.ERROR;
        } else {
            return controllerConfig.buttonsConfig.getButtonState(buttonId);
        }

    }

    public JoystickState getJoystickState(ControllerId controllerId, JoystickId joystickId) {
        final  ControllerConfig<ButtonId, JoystickId> controllerConfig = configMap.get(controllerId);
        if (controllerConfig==null) {
            return JoystickState.ERROR;
        } else {
            final ButtonJoystickState buttonJoystickState = controllerConfig.joysticksConfig.getJoystickState(joystickId);
            return buttonJoystickState.joystickState;
        }
    }

}
