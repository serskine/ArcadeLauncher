package launcher.framework.controls.machine.dummy;

import launcher.framework.controls.GameController;
import launcher.framework.controls.jinput.JoystickConfig;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DummyGameController extends GameController<Object, Object, Object> {
    public DummyGameController(Controller controller) {
        super(controller, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    @Override
    public List<Component.Identifier> getBoundAxisIdentifiers(Object o) {
        return new ArrayList<>();
    }

    @Override
    public List<Component.Identifier> getBoundButtonIdentifiers(Object o) {
        return new ArrayList<>();
    }

    @Override
    public JoystickConfig<Object> getBoundJoystickConfig(Object o) {
        return null;
    }
}
