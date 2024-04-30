package launcher.framework.controls;

import launcher.framework.controls.jinput.JoystickConfig;
import launcher.framework.controls.jinput.JoystickState;
import launcher.framework.controls.state.AxisState;
import launcher.framework.controls.state.ButtonState;
import launcher.framework.util.Logger;
import launcher.framework.util.Text;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.*;
import java.util.stream.Collectors;

import static launcher.framework.util.Logger.info;

public abstract class GameController<ButtonId, AxisId, JoystickId> {

    private Set<ButtonId> possibleButtons;
    private Set<AxisId> possibleAxis;
    private Set<JoystickId> possibleJoysticks;

    public Controller controller;

    public GameController(
            final Controller controller,
            final ButtonId[] possibleButtons,
            final AxisId[] possibleAxis,
            final JoystickId[] possibleJoysticks
    ) {
        this(
            controller,
            Arrays.stream(possibleButtons).collect(Collectors.toSet()),
            Arrays.stream(possibleAxis).collect(Collectors.toSet()),
            Arrays.stream(possibleJoysticks).collect(Collectors.toSet())
        );
    }
    public GameController(
            final Controller controller,
            final Set<ButtonId> possibleButtons,
            final Set<AxisId> possibleAxis,
            final Set<JoystickId> possibleJoysticks
    ) {
        this.controller = controller;
        this.possibleButtons = possibleButtons;
        this.possibleAxis = possibleAxis;
        this.possibleJoysticks = possibleJoysticks;
    }



    public final Set<ButtonId> getPossibleButtons() {
        return possibleButtons;
    }
    public final Set<AxisId> getPossibleAxis() {
        return possibleAxis;
    }
    public final Set<JoystickId> getPossibleJoysticks() {
        return possibleJoysticks;
    }
    public final Controller getController() {
        return this.controller;
    }
    public final void poll() {
        getController().poll();
    }

    public abstract List<Component.Identifier> getBoundAxisIdentifiers(AxisId axisId);
    public abstract List<Component.Identifier> getBoundButtonIdentifiers(ButtonId buttonId);
    public abstract JoystickConfig<AxisId> getBoundJoystickConfig(JoystickId joystickId);

    public final List<Component> getAxisComponents(AxisId axisId) {
        if (getPossibleAxis().contains(axisId)) {
            final List<Component.Identifier> identifiers = getBoundAxisIdentifiers(axisId);
            final List<Component> components = identifiers.stream()
                    .map(id -> getController().getComponent(id))
                    .filter(c -> c != null)
                    .collect(Collectors.toList());
            return components;
        } else {
            return new ArrayList<>();   // Return an empty list
        }
    }

    public AxisState getAxisState(AxisId axisId) {
        poll();
        final AxisState axisState = getAxisComponents(axisId).stream()
                .map(c -> AxisState.getComponentState(c))
                .filter(s -> AxisState.ERROR != s)
                .findFirst()
                .orElse(AxisState.ERROR);
        return axisState;
    }

    public final List<Component> getButtonComponents(ButtonId buttonId) {
        if (getPossibleButtons().contains(buttonId)) {
            final List<Component.Identifier> identifiers = getBoundButtonIdentifiers(buttonId);
            final List<Component> components = identifiers.stream()
                    .map(id -> getController().getComponent(id))
                    .filter(c -> c != null)
                    .collect(Collectors.toList());
            return components;
        } else {
            return new ArrayList<>();   // Empty list
        }
    }

    public final Set<Component.Identifier> getRequiredComponents() {
        final Set<Component.Identifier> requiredComponents = new HashSet<>();
        for(ButtonId buttonId : getPossibleButtons()) {
            requiredComponents.addAll(getBoundButtonIdentifiers(buttonId));
        }
        for(AxisId axisId : getPossibleAxis()) {
            requiredComponents.addAll(getBoundAxisIdentifiers(axisId));
        }
        return requiredComponents;
    }

    public final ButtonState getButtonState(ButtonId buttonId) {
        poll();

        final List<Component> components = getButtonComponents(buttonId);
        final List<ButtonState> states = components.stream().map(c -> ButtonState.getComponentState(c)).collect(Collectors.toList());;
        final List<ButtonState> valid = states.stream().filter(s -> !ButtonState.ERROR.equals(s)).collect(Collectors.toList());
        final Optional<ButtonState> found = valid.stream().findFirst();
        final ButtonState result = found.orElse(ButtonState.ERROR);
        return result;
    }

    public final JoystickState getJoystickState(JoystickId joystickId) {
        JoystickConfig<AxisId> joystickConfig = getBoundJoystickConfig(joystickId);
        try {
            final AxisId xAxisId = joystickConfig.xAxis;
            final AxisId yAxisId = joystickConfig.yAxis;

            final AxisState axisStateX = getAxisState(xAxisId);
            final AxisState axisStateY = getAxisState(yAxisId);

            final JoystickState joystickState = JoystickState.axisState(axisStateX, axisStateY);

            return joystickState;
        } catch (Exception e) {
            Logger.warning(e.getMessage(), e);
            return JoystickState.ERROR;
        }
    }

    public String getName() {
        if (controller==null) {
            return "null controller";
        } else {
            return controller.getName();
        }
    }

    public boolean isMouse() {
        return getName().toLowerCase(Locale.ROOT).contains("mouse");
    }
    public boolean isKeyboard() {
        return getName().toLowerCase(Locale.ROOT).contains("keyboard");
    }
    public boolean isTouchPad() {
        return getName().toLowerCase(Locale.ROOT).contains("touchpad");
    }

    public boolean isGameController() {
        return "8Bitdo SN30 Pro".equals(getName());
    }

    List<String> verifyAllButtonsSupported() {
        final List<String> errors = new ArrayList<>();
        for(ButtonId buttonId : possibleButtons) {
            errors.addAll(verifyButtonSupported(buttonId));
        }
        return errors;
    }
    List<String> verifyButtonSupported(ButtonId buttonId) {
        final List<Component.Identifier> possibleComponents = getBoundButtonIdentifiers(buttonId);
        final List<String> errors = new ArrayList<>();

        for(Component.Identifier identifier : possibleComponents) {
            if (controller.getComponent(identifier)!=null) {
                return errors;
            }
        }

        errors.add("Controller does not support button " + buttonId + ". Missing all possible components " + Text.describeList(possibleComponents));
        return errors;
    }

    List<String> verifyAxisSupported(final AxisId axisId) {
        final List<String> errors = new ArrayList<>();
        final List<Component.Identifier> possibleComponents = getBoundAxisIdentifiers(axisId);

        for(Component.Identifier identifier : possibleComponents) {
            if (controller.getComponent(identifier)!=null) {
                return errors;
            }
        }
        errors.add("Controller does not support axis " + axisId + ". Missing all possible components " + Text.describeList(possibleComponents));

        return errors;
    }

    List<String> verifyAllAxisSupported() {
        final List<String> errors = new ArrayList<>();

        for(AxisId axisId : possibleAxis) {
            errors.addAll(verifyAxisSupported(axisId));
        }

        return errors;
    }

    List<String> verifyJoystickSupported(final JoystickId joystickId) {
        final JoystickConfig<AxisId> config = getBoundJoystickConfig(joystickId);
        final List<String> errors = new ArrayList<>();

        errors.addAll(verifyAxisSupported(config.xAxis));
        errors.addAll(verifyAxisSupported(config.yAxis));

        return errors;
    }

    List<String> verifyAllJoystickSupported() {
        final List<String> errors = new ArrayList<>();

        for(JoystickId joystickId : getPossibleJoysticks()) {
            errors.addAll(verifyJoystickSupported(joystickId));
        }

        return errors;
    }

    List<String> verifyGameControllorIsSupported() {
        final List<String> errors = new ArrayList<>();

        errors.addAll(verifyAllButtonsSupported());
        errors.addAll(verifyAllAxisSupported());
        errors.addAll(verifyAllJoystickSupported());

        return errors;
    }

}
