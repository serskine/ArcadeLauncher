package launcher.framework.controls.jinput;

import launcher.framework.controls.state.AxisState;
import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.controls.state.ButtonState;
import launcher.framework.util.Logger;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RawJinputListener {

    protected final ControllerEnvironment env;

    public RawJinputListener() {
        env = ControllerEnvironment.getDefaultEnvironment();
    }

    public ControllerEnvironment getControllerEnvironment() {
        return env;
    }

    public static final ButtonState getButtonState(Component component) {
        if (component==null) {
            return ButtonState.ERROR;
        } else if (component.getIdentifier() instanceof Component.Identifier.Axis) {
            return ButtonState.ERROR;   // Not a button or key
        } else if (component.isAnalog()) {
            return getAnalogButtonState(component.getPollData(), component.getDeadZone());
        } else {
            return getDigitalButtonState(component.getPollData());
        }
    }

    public static final AxisState getAxisState(Component component) {
        if (component==null) {
            return AxisState.ERROR;
        } else if (!(component.getIdentifier() instanceof Component.Identifier.Axis)) {
            return AxisState.ERROR;
        } else if (component.isAnalog()) {
            return getAnalogAxisState(component.getPollData(), component.getDeadZone());
        } else {
            return getDigitalAxisState(component.getPollData());
        }
    }
    public static final boolean isInDeadZone(final float value, final float deadZone) {
        return (Math.abs(value) <= deadZone);
    }

    public static final ButtonState getAnalogButtonState(final float value, final float deadZone) {
        if (isInDeadZone(value, deadZone)) {
            return ButtonState.RELEASED;
        } else {
            return ButtonState.PRESSED;
        }
    }

    public static final ButtonState getDigitalButtonState(final float value) {
        if (value > 0F) {
            return ButtonState.PRESSED;
        } else if (value < 0F) {
            return ButtonState.ERROR;
        } else {
            return ButtonState.RELEASED;
        }
    }

    public static final AxisState getAnalogAxisState(final float value, final float axis) {
        if (isInDeadZone(value, axis)) {
            return AxisState.NEUTRAL;
        } else if (value<0F) {
            return AxisState.NEGATIVE;
        } else {
            return AxisState.POSITIVE;
        }
    }

    public static final AxisState getDigitalAxisState(final float value) {
        if (value<0F) {
            return AxisState.NEGATIVE;
        } else if (value > 0F) {
            return AxisState.POSITIVE;
        } else {
            return AxisState.NEUTRAL;
        }
    }

    public final ButtonState getButtonState(final String controllerName, final Component.Identifier buttonId) {
        if (    (buttonId instanceof Component.Identifier.Button)
            ||  (buttonId instanceof Component.Identifier.Key)
        ) {
            return getButtonState(getComponent(controllerName, buttonId).orElse(null));
        } else {
            return ButtonState.ERROR;
        }
    }

    public final AxisState getAxisState(final String controllerName, final Component.Identifier.Axis axisId) {
        return getAxisState(getComponent(controllerName, axisId).orElse(null));
    }

    public final Optional<Controller> getController(final String name) {
        return Arrays.stream(getControllerEnvironment().getControllers()).filter(c -> Objects.equals(name, c.getName())).findFirst();
    }

    public final Optional<Component> getComponent(final String controllerName, final Component.Identifier componentId) {
        final Optional<Controller> controllerOpt = getController(controllerName);
        if (controllerOpt.isPresent()) {
            final Controller controller = controllerOpt.get();
            controller.poll();  // Ensures the data is updated
            return Optional.ofNullable(controller.getComponent(componentId));
        } else {
            return Optional.empty();
        }
    }


    public final ButtonJoystickState getButtonJoystickState(
            final String controllerName,
            final Component.Identifier.Axis xAxisId,
            final Component.Identifier.Axis yAxisId
    ) {
        JoystickState joystickState = getJoystickState(controllerName, xAxisId, yAxisId);
        if (joystickState==null || joystickState.isError) {
            return ButtonJoystickState.ERROR;
        } else {
            return joystickState.getClosestButtonState();
        }
    }
    public final JoystickState getJoystickState(
            final String controllerName,
            final Component.Identifier.Axis xAxisId,
            final Component.Identifier.Axis yAxisId
    ) {
        final Optional<Controller> controllerOpt = getController(controllerName);
        if (controllerOpt.isPresent()) {
            final Controller controller = controllerOpt.get();
            controller.poll();

            final Component xAxisComponent = controller.getComponent(xAxisId);
            final Component yAxisComponent = controller.getComponent(yAxisId);

            final AxisState xAxisState = getAxisState(xAxisComponent);
            final AxisState yAxisState = getAxisState(yAxisComponent);

            final boolean isError = xAxisState==AxisState.ERROR || yAxisState==AxisState.ERROR;
            final float dx = (xAxisState==AxisState.POSITIVE || xAxisState==AxisState.NEGATIVE) ? xAxisComponent.getPollData() : 0F;
            final float dy = (yAxisState==AxisState.POSITIVE || yAxisState==AxisState.NEGATIVE) ? yAxisComponent.getPollData() : 0F;
            final float deadX = xAxisComponent.getDeadZone();
            final float deadY = yAxisComponent.getDeadZone();

            final double headingRadians = Math.atan2(dy, dx);
            final double vectorMag = Math.sqrt((dx*dx) + (dy*dy));
            final double deadZoneMag = Math.sqrt((deadX*deadX) + (deadY*deadY));

            return new JoystickState(isError, headingRadians, vectorMag, deadZoneMag);
        } else {
            return new JoystickState(true, 0f, 0f, 0f);
        }
    }

    public static final Map<String, Controller> getControllers(final ControllerEnvironment controllerEnvironment) {
        final Map<String, Controller> controllerMap = new TreeMap<>();
        for(Controller controller : controllerEnvironment.getControllers()) {
            controllerMap.put(controller.getName(), controller);
        }
        return controllerMap;
    }

    /**
     * This map isn't really necessary because the controller itself has a method to retrieve the component
     * for an identifier. Where this is useful is that it also provided a map onyl of supported Identifiers
     * @param controller we want components from
     * @return a {@link Map<Component.Identifier, Component}
     */
    public static final Map<Component.Identifier, Component> getComponents(final Controller controller) {
        final Map<Component.Identifier, Component> componentMap = new TreeMap<>();
        controller.poll();  // Ensure we have the latest data
        for(Component component : controller.getComponents()) {
            componentMap.put(component.getIdentifier(), component);
        }
        return componentMap;
    }

    public void poll() {
        for(Controller controller : getControllerEnvironment().getControllers()) {
            controller.poll();
        }
    }
    public Map<String, Map<Component.Identifier, Object>> getComponentValues() {
        return getComponentValues((controller) -> true, (component) -> true);
    }

    public Map<String, Map<Component.Identifier, Object>> getComponentValues(
            Predicate<Controller> controllerPredicate,
            Predicate<Component> componentPredicate
    ) {
        final Map<String, Map<Component.Identifier, Object>> map = new HashMap<>();

        final Map<String,Controller> filteredControllerMap = getControllers(controllerPredicate);
        for(Controller controller : filteredControllerMap.values()) {
            controller.poll();
            final Map<Component.Identifier, Object> valueMap = new HashMap<>();
            map.put(controller.getName(), valueMap);

            final List<Component> filteredComponents = Arrays.stream(controller.getComponents())
                    .filter(c -> componentPredicate.test(c))
                    .collect(Collectors.toList());

            for(Component component : filteredComponents) {
                final Component.Identifier id = component.getIdentifier();
                if (id instanceof Component.Identifier.Axis) {
                    final AxisState axisState = getAxisState(component);
                    valueMap.put(id, axisState);
                } else if (id instanceof Component.Identifier.Button) {
                    final ButtonState buttonState = getButtonState(component);
                    valueMap.put(id, buttonState);
                } else if (id instanceof Component.Identifier.Key) {
                    final ButtonState buttonState = getButtonState(component);
                    valueMap.put(id, buttonState);
                } else {
                    final String value = "Raw data: " + component.getPollData();
                    valueMap.put(id, value);
                }
            }
        }
        return map;
    }

    public Map<String, Controller> getControllers() {
        return getControllers(c -> true);  // Get all controller ids
    }

    public Map<String, Controller>  getControllers(final Predicate<Controller> controllerPredicate) {
        final Map<String, Controller> resultMap = new HashMap<>();
        final Map<String, Controller> allControllers = getControllers(getControllerEnvironment());
        allControllers.entrySet().stream()
                .filter(e -> controllerPredicate.test(e.getValue()))
                .forEach(e -> resultMap.put(e.getKey(), e.getValue()));
        return resultMap;
    }

}
