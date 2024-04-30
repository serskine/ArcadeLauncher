package launcher.framework.controls.machine.sf2;

import launcher.framework.controls.GameController;
import launcher.framework.controls.GameControls;
import launcher.framework.controls.jinput.RawJinputListener;
import net.java.games.input.Controller;

import java.util.HashMap;
import java.util.Map;

public class Sf2GameControls extends GameControls<Sf2ControllerId, Sf2ButtonId, Sf2AxisId, Sf2JoystickId> {

    private Map<Sf2ControllerId, Integer> indexMap = new HashMap<>();
    public Sf2GameControls(RawJinputListener rawJinputListener) {
        super(rawJinputListener, Sf2ControllerId.values(), Sf2ButtonId.values(), Sf2AxisId.values(), Sf2JoystickId.values());
    }

    @Override
    public int getSupportedControllerIndex(Sf2ControllerId sf2ControllerId) {
        return sf2ControllerId.ordinal();
    }

    @Override
    public GameController<Sf2ButtonId, Sf2AxisId, Sf2JoystickId> createGameController(Controller controller) {
        return new Sf2Controller(controller);
    }
}