package launcher.framework.controls.virtual;

import launcher.framework.controls.jinput.JInputListener;
import launcher.framework.controls.state.VirtualKey;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class ControllersConfig<ControllerId, ButtonId, JoystickId> implements KeyListener {

    private final Set<VirtualKey> pressedKeys = new HashSet<>();
    private Map<ControllerId, ControllerConfig<ButtonId, JoystickId>> configMap = new HashMap<>();
    public Set<ControllerId> getBoundControllers() {
        return configMap.keySet();
    }

    public final JInputListener jInputListener;

    public Optional<ControllerConfig<ButtonId, JoystickId>> getControllerConfig(ControllerId controllerId) {
        return Optional.ofNullable(configMap.get(controllerId));
    }

    public ControllersConfig() {
        this(10);   // Set default polling time.
    }
    public ControllersConfig(final long pollMs) {
        this.jInputListener = new JInputListener(pollMs, this);
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

}
