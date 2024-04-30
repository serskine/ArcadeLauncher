package launcher.framework.controls.machine.pm;

import launcher.framework.controls.GameController;
import launcher.framework.controls.GameControls;
import launcher.framework.controls.jinput.RawJinputListener;
import net.java.games.input.Controller;

public class PmGameControls extends GameControls<PmControllerId, PmButtonId, PmAxisId, PmJoystickId> {
    protected PmGameControls(RawJinputListener rawJinputListener) {
        super(rawJinputListener, PmControllerId.values(), PmButtonId.values(), PmAxisId.values(), PmJoystickId.values());
    }
    @Override
    public int getSupportedControllerIndex(PmControllerId pmControllerId) {
        return pmControllerId.ordinal();
    }

    @Override
    public GameController<PmButtonId, PmAxisId, PmJoystickId> createGameController(Controller controller) {
        return new PmController(controller);
    }
}
