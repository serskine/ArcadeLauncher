package launcher.framework.controls.machine.dummy;

import launcher.framework.controls.GameController;
import launcher.framework.controls.GameControls;
import launcher.framework.controls.jinput.RawJinputListener;
import net.java.games.input.Controller;

import java.util.HashSet;

public class DummyGameControls extends GameControls<Object, Object, Object, Object> {

    public DummyGameControls() {
        this(new RawJinputListener());
    }
    public DummyGameControls(RawJinputListener rawJinputListener) {
        super(rawJinputListener, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    @Override
    public int getSupportedControllerIndex(Object o) {
        return -1;
    }

    @Override
    public GameController<Object, Object, Object> createGameController(Controller controller) {
        return new DummyGameController(controller);
    }
}
