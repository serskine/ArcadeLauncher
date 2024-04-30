package launcher.framework.controls;

import launcher.framework.controls.jinput.JoystickState;
import launcher.framework.controls.state.AxisState;
import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.controls.state.ButtonState;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.*;

public class GameControllerChangeDetector {
    final Map<Object, ButtonState> prevButtonStates = new HashMap<>();
    final Map<Object, AxisState> prevAxisStates = new HashMap<>();
    final Map<Object, JoystickState> prevJoystickStates = new HashMap<>();

    final Map<Object, ButtonJoystickState> prevButtonJoystickStates = new HashMap<>();

    final Map<Component.Identifier, Object> prevComponentState = new HashMap<>();

    public final List<Listener> listeners = new ArrayList<>();
    public interface Listener {
        void onButtonChange(final String controllerName, final Object buttonId, final ButtonState prevState, final ButtonState currentState);
        void onAxisChange(final String controllerName, final Object axisId, final AxisState prevState, final AxisState currentState);
        void onJoystickChange(final String controllerName, final Object joystickId, final JoystickState prevState, final JoystickState currentState);
        void onButtonJoystickChange(final String controllerName, final Object joystickId, final ButtonJoystickState prevState, final ButtonJoystickState currentState);

        void onComponentChange(final String controllerName, final Component.Identifier identifier, final Object prevPollDate, final Object currentPollData);
    }
    public void update(GameController gameController) {
        updateComponentStates(gameController.getController());
        updateButtonStates(gameController);
        updateAxisStates(gameController);
        updateJoystickStates(gameController);
    }

    void updateJoystickStates(final GameController gameController) {
        for(Object joystickId : gameController.getPossibleJoysticks()) {
            final JoystickState prevState = prevJoystickStates.getOrDefault(joystickId, JoystickState.ERROR);
            final JoystickState currentState = gameController.getJoystickState(joystickId);
            prevJoystickStates.put(joystickId, currentState);
            if (!JoystickState.isSimilar(prevState, currentState)) {
                announceJoystickChangedEvent(gameController.getName(), joystickId, prevState, currentState);
            }

            final ButtonJoystickState prevButtonJoystickState = prevButtonJoystickStates.getOrDefault(joystickId, ButtonJoystickState.ERROR);
            final ButtonJoystickState currentButtonJoystickState = currentState.getClosestButtonState();
            prevButtonJoystickStates.put(joystickId, currentButtonJoystickState);
            if (!ButtonJoystickState.isSame(prevButtonJoystickState , currentButtonJoystickState)) {
                announceButtonJoystickChangedEvent(gameController.getName(), joystickId, prevButtonJoystickState, currentButtonJoystickState);
            }
        }
    }
    void updateAxisStates(final GameController gameController) {
        for(Object axisId : gameController.getPossibleAxis()) {
            final AxisState prevState = prevAxisStates.getOrDefault(axisId, AxisState.ERROR);
            final AxisState currentState = gameController.getAxisState(axisId);
            prevAxisStates.put(axisId, currentState);
            if (!Objects.equals(prevState, currentState)) {
                announceAxisChangedEvent(gameController.getName(), axisId, prevState, currentState);
            }
        }
    }
    void updateButtonStates(final GameController gameController) {
        for(Object buttonId : gameController.getPossibleButtons()) {
            final ButtonState prevState = prevButtonStates.getOrDefault(buttonId, ButtonState.ERROR);
            final ButtonState currentState = gameController.getButtonState(buttonId);
            prevButtonStates.put(buttonId, currentState);
            if (!Objects.equals(prevState, currentState)) {
                announceButtonChangedEvent(gameController.getName(), buttonId, prevState, currentState);
            }
        }
    }

    void updateComponentStates(final Controller controller) {
        for(Component component : controller.getComponents()) {
            if (component != null) {
                final Component.Identifier identifier = component.getIdentifier();
                if (ButtonState.isButton(identifier)) {
                    final ButtonState prevState = (ButtonState) prevComponentState.getOrDefault(identifier, ButtonState.ERROR);
                    final ButtonState currentState = ButtonState.getComponentState(component);

                    if (!ButtonState.isSame(prevState, currentState)) {
                        prevComponentState.put(identifier, currentState);
                        announceComponentChangeEvent(component.getName(), identifier, prevState, currentState);
                    }
                } else if (AxisState.isAxis(identifier)) {
                    final AxisState prevState = (AxisState) prevComponentState.getOrDefault(identifier, AxisState.ERROR);
                    final AxisState currentState = AxisState.getComponentState(component);

                    if (!AxisState.isSame(prevState, currentState)) {
                        prevComponentState.put(identifier, currentState);
                        announceComponentChangeEvent(component.getName(), identifier, prevState, currentState);
                    }
                } else {
                    // Do nothing.
                }

            }
        }
    }


    private void announceButtonChangedEvent(final String controllerName, final Object buttonId, final ButtonState prevState, final ButtonState currentState) {
        for(Listener listener : listeners) {
            listener.onButtonChange(controllerName, buttonId, prevState, currentState);
        }
    }
    private void announceJoystickChangedEvent(final String controllerName, final Object joystickId, final JoystickState prevState, final JoystickState currentState) {
        for(Listener listener : listeners) {
            listener.onJoystickChange(controllerName, joystickId, prevState, currentState);
        }
    }

    private void announceButtonJoystickChangedEvent(final String controllerName, final Object joystickId, final ButtonJoystickState prevState, final ButtonJoystickState currentState) {
        for(Listener listener : listeners) {
            listener.onButtonJoystickChange(controllerName, joystickId, prevState, currentState);
        }
    }
    private void announceAxisChangedEvent(final String controllerName, final Object axisId, final AxisState prevState, final AxisState currentState) {
        for(Listener listener : listeners) {
            listener.onAxisChange(controllerName, axisId, prevState, currentState);
        }
    }
    private void announceComponentChangeEvent(final String controllerName, final Component.Identifier identifier, final Object prevData, final Object currentData) {
        for(Listener listener : listeners) {
            listener.onComponentChange(controllerName, identifier, prevData, currentData);
        }
    }
}
