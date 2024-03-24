package launcher.framework.controls.jinput;

import launcher.framework.controls.state.VirtualKey;
import launcher.framework.controls.state.AxisState;
import launcher.framework.controls.state.ButtonState;
import launcher.framework.controls.virtual.ButtonJoystickConfig;
import launcher.framework.controls.virtual.ControllerConfig;
import launcher.framework.controls.virtual.ControllersConfig;
import launcher.framework.util.Func;
import launcher.framework.util.Logger;
import launcher.framework.util.Text;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.*;

public class JInputListener<ControllerId, ButtonId, JoystickId> implements Runnable {
    private final long pollMs;
    private final ControllersConfig<ControllerId, ButtonId, JoystickId> config;
    private final Map<String, ControllerId> controllerIdMap = new HashMap<>();
    private final Map<String, Set<JoystickId>> controllerJoysticks = new HashMap<>();

    private boolean isRunning = false;
    public JInputListener(final long pollMs, ControllersConfig<ControllerId, ButtonId, JoystickId> config) {
        this.config = config;
        this.pollMs = pollMs;
        setRunning(true);
    }

    @Override
    public void run() {
        try {
            while(isRunning()) {
                poll();
                Func.pause(pollMs);
            }
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
        }
    }

    public final void bindController(final String controllerName, final ControllerId controllerId) {
        this.controllerIdMap.put(controllerName, controllerId);
    }

    public final void bindControllerStick(final String controllerName, final JoystickId joystickId) {
        this.controllerJoysticks.putIfAbsent(controllerName, new HashSet<>());
        final Set<JoystickId> joysticks = controllerJoysticks.get(controllerName);
        joysticks.add(joystickId);
    }

    public void poll() {
        final ControllerEnvironment env = ControllerEnvironment.getDefaultEnvironment();
        for(Controller controller : env.getControllers()) {
            final boolean polled = controller.poll();

            if (polled) {
                final Optional<ControllerId> controllerIdOpt = getControllerId(controller.getName());
                if (controllerIdOpt.isPresent()) {
                    final ControllerId controllerId = controllerIdOpt.get();
                    ControllerConfig<ButtonId, JoystickId> controllerConfig = config.getControllerConfig(controllerId).orElse(null);

                    // Press all the matching keys
                    for (Component component : controller.getComponents()) {
                        final Component.Identifier componentId = component.getIdentifier();

                        if (componentId instanceof Component.Identifier.Key) {
                            final Component.Identifier.Key componentKeyId = (Component.Identifier.Key) componentId;
                            if (!Component.Identifier.Key.UNKNOWN.equals(componentKeyId)) {
                                final ButtonState desiredState = getButtonState(component);
                                final Set<VirtualKey> matchingKeys = VirtualKey.parse(componentKeyId);
                                if (matchingKeys.size() > 1) {
                                    Logger.warning("Multiple virtual keys mapped to " + componentKeyId + ". " + Text.describeSet(matchingKeys));
                                } else {
                                    for (VirtualKey virtualKey : matchingKeys) {
                                        switch (desiredState) {
                                            case PRESSED:
                                                config.pressKey(virtualKey);
                                                break;
                                            case RELEASED:
                                                config.releaseKey(virtualKey);
                                                break;
                                            case ERROR:
                                                // do nothing. We can't parse the input
                                                break;
                                        }
                                    }
                                }
                            }
                        } else if (componentId instanceof Component.Identifier.Axis) {
                            final Component.Identifier.Axis componentAxisId = (Component.Identifier.Axis) componentId;
                            if (controllerConfig != null) {

                                // Retrieve the set of all joysticks that this controller impacts with it's axis values
                                final Set<JoystickId> joysticks = controllerJoysticks.getOrDefault(controllerId, new HashSet<>());

                                for (JoystickId joystickId : joysticks) {
                                    final Optional<ButtonJoystickConfig<VirtualKey>> joystickConfigOpt = controllerConfig.joysticksConfig.getBoundKeys(joystickId);
                                    if (joystickConfigOpt.isPresent()) {
                                        final ButtonJoystickConfig<VirtualKey> joystickKeyConfig = joystickConfigOpt.get();

                                        if (Component.Identifier.Axis.X.equals(componentAxisId)) {
                                            // Perform x axis operations
                                            final AxisState axisState = getAxisState(component);
                                            switch (axisState) {
                                                case POSITIVE:
                                                    config.pressKey(joystickKeyConfig.east);
                                                    config.releaseKey(joystickKeyConfig.west);
                                                    break;
                                                case NEGATIVE:
                                                    config.releaseKey(joystickKeyConfig.east);
                                                    config.pressKey(joystickKeyConfig.west);
                                                    break;
                                                case NEUTRAL:
                                                    config.releaseKey(joystickKeyConfig.east);
                                                    config.releaseKey(joystickKeyConfig.west);
                                                    break;
                                                case ERROR:
                                                    // Do nothing
                                                    break;
                                            }

                                        } else if (Component.Identifier.Axis.Y.equals(componentAxisId)) {
                                            // Perform y axis operations
                                            final AxisState axisState = getAxisState(component);
                                            switch (axisState) {
                                                case POSITIVE:
                                                    config.pressKey(joystickKeyConfig.north);
                                                    config.releaseKey(joystickKeyConfig.south);
                                                    break;
                                                case NEGATIVE:
                                                    config.releaseKey(joystickKeyConfig.north);
                                                    config.pressKey(joystickKeyConfig.south);
                                                    break;
                                                case NEUTRAL:
                                                    config.releaseKey(joystickKeyConfig.north);
                                                    config.releaseKey(joystickKeyConfig.south);
                                                    break;
                                                case ERROR:
                                                    // Do nothing
                                                    break;
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private Optional<ControllerId> getControllerId(String controllerName) {
        return Optional.ofNullable(controllerIdMap.get(controllerName));
    }

    private static AxisState getAxisState(final Component component) {
        try {
            final float pollData = component.getPollData();
            final float deadZone = component.getDeadZone();
            if (component.isRelative()) {
                throw new RuntimeException("Component " + component.getName() + " contains poll data relative to the previous data. We can't determine an axis state from that.");
            }
            if (component.isAnalog()) {
                if (Math.abs(pollData) < deadZone) {
                    return AxisState.NEUTRAL;
                } else if (pollData < 0D) {
                    return AxisState.NEGATIVE;
                } else if (pollData > 0D) {
                    return AxisState.POSITIVE;
                } else {
                    return AxisState.NEUTRAL;
                }
            } else {
                if (pollData < 0D) {
                    return AxisState.NEGATIVE;
                } else if (pollData > 0D) {
                    return AxisState.POSITIVE;
                } else {
                    return AxisState.NEUTRAL;
                }
            }
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
            return AxisState.ERROR;
        }
    }
    private static ButtonState getButtonState(final Component component) {
        try {
            if (component.isRelative()) {
                throw new RuntimeException("Component " + component.getName() + " contains poll data relative to the previous data. We can't determine a button state from that.");
            }
            if (component.isAnalog()) {
                if (Math.abs(component.getPollData()) < component.getDeadZone()) {
                    return ButtonState.RELEASED;
                } else {
                    return ButtonState.PRESSED;
                }
            } else {
                if (component.getPollData() != 0D) {
                    return ButtonState.PRESSED;
                } else {
                    return ButtonState.RELEASED;
                }
            }
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
            return ButtonState.ERROR;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        if (running && !this.isRunning) {
            this.isRunning = running;
            final Thread thread = new Thread(this);
            thread.start();
        }
    }
}
