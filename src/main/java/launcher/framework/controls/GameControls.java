package launcher.framework.controls;

import launcher.framework.controls.jinput.RawJinputListener;
import launcher.framework.controls.machine.sf2.Sf2Controller;
import launcher.framework.util.Logger;
import launcher.framework.util.Text;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.*;
import java.util.stream.Collectors;

import static launcher.framework.util.Logger.*;

public abstract class GameControls<ControllerId, ButtonId, AxisId, JoystickId> implements ArcadeControls {

    private final RawJinputListener rawJinputListener;
    private final Set<ControllerId> possibleControllers;
    private final Set<ButtonId> possibleButtons;
    private final Set<AxisId> possibleAxis;
    private final Set<JoystickId> possibleJoysticks;

    private List<GameController<ButtonId, AxisId, JoystickId>> allControllers;
    private List<GameController<ButtonId, AxisId, JoystickId>> supportedControllers;

    public GameControls(
            final RawJinputListener rawJinputListener,
            final ControllerId[] possibleControllers,
            final ButtonId[] possibleButtons,
            final AxisId[] possibleAxis,
            final JoystickId[] possibleJoysticks
    ) {
        this(
            rawJinputListener,
            Arrays.stream(possibleControllers).collect(Collectors.toSet()),
            Arrays.stream(possibleButtons).collect(Collectors.toSet()),
            Arrays.stream(possibleAxis).collect(Collectors.toSet()),
            Arrays.stream(possibleJoysticks).collect(Collectors.toSet())
        );
    }
    protected GameControls(
            final RawJinputListener rawJinputListener,
            final Set<ControllerId> possibleControllers,
            final Set<ButtonId> possibleButtons,
            final Set<AxisId> possibleAxis,
            final Set<JoystickId> possibleJoysticks
    ) {
        this.rawJinputListener = rawJinputListener;
        this.possibleControllers = possibleControllers;
        this.possibleButtons = possibleButtons;
        this.possibleAxis = possibleAxis;
        this.possibleJoysticks = possibleJoysticks;

        // Create a game controller for each actual controller
        allControllers = rawJinputListener.getControllers().stream()
                .map(c -> createGameController(c))
                .collect(Collectors.toList());

        supportedControllers = new ArrayList<>();
        for(GameController gameController : allControllers) {
            try {
                final List<String> errors = gameController.verifyGameControllorIsSupported();
                if (!errors.isEmpty()) {
                    throw new RuntimeException("Controller " + gameController.getName() + " is not supported\n" + Text.describeList(errors, false));
                }
                supportedControllers.add(gameController);
                Logger.info("Supported game controller #1 (" + gameController.getName() + ") found.");
            } catch (Exception e) {
                Logger.warning(e.getMessage());
            }
        }
    }


    @Override
    public final RawJinputListener getListener() {
        return this.rawJinputListener;
    }

    final Set<ControllerId> getPossibleControllers() {
        return this.possibleControllers;
    }

    final List<GameController<ButtonId, AxisId, JoystickId>> getControllers() {
        return allControllers;
    }

    public final GameController<ButtonId, AxisId, JoystickId> getController(final int index) {
        return getControllers().get(index);
    }

    public final GameController<ButtonId, AxisId, JoystickId> getSupportedController(final int index) {
        return getSupportedControllers().get(index);
    }

    public final GameController<ButtonId, AxisId, JoystickId> getController(final ControllerId controllerId) {
        return getController(getSupportedControllerIndex(controllerId));
    }

    /**
     * This represents the index amongst supported controllers to use
     * @param controllerId identifies a game controller to check for input
     * @return -1 if there is not bound controller for the controller id
     */
    public abstract int getSupportedControllerIndex(ControllerId controllerId);

    public abstract GameController<ButtonId, AxisId, JoystickId> createGameController(final Controller controller);

    public final GameController<ButtonId, AxisId, JoystickId> getGameController(final ControllerId controllerId) {
        if (possibleControllers.contains(controllerId)) {
            final int controllerIndex = getSupportedControllerIndex(controllerId);
            return getSupportedController(controllerIndex);
        } else {
            return null;
        }
    }

    /**
     * This will trigger a poll on ALL game controllers
     */
    public final void poll() {
        for(GameController<ButtonId, AxisId, JoystickId> gameController : getControllers()) {
            gameController.poll();
        }
    }
    public final List<GameController<ButtonId, AxisId, JoystickId>> getSupportedControllers() {
        return this.supportedControllers;
    }

}


