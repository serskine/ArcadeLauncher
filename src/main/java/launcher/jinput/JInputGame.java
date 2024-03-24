package launcher.jinput;

import launcher.GameId;
import launcher.framework.controls.jinput.JInputListener;
import launcher.framework.controls.virtual.ControllersConfig;
import launcher.framework.Game;
import launcher.framework.draw.Draw;
import launcher.framework.util.Text;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static launcher.framework.util.Text.indent;

public class JInputGame extends Game<String, String, String, String> {

    private ControllersConfig<String, String, String> controllersConfig;
    private JInputListener<String, String, String> listener;
    public JInputGame() {
        super(GameId.JINPUT);
    }

    @Override
    protected String createInitialGameState() {
        final ControllerEnvironment controllerEnvironment = ControllerEnvironment.getDefaultEnvironment();

        return describe(controllerEnvironment);
    }

    @Override
    protected ControllersConfig<String, String, String> getControlsConfig() {
        if (this.controllersConfig==null) {
            this.controllersConfig = new ControllersConfig<>();
            this.listener = new JInputListener<>(10, controllersConfig);
        }
        return controllersConfig;
    }


    @Override
    public void onRender(Graphics2D g) {
            final ControllerEnvironment controllerEnvironment = ControllerEnvironment.getDefaultEnvironment();
            final String description = describe(controllerEnvironment);
            final Dimension screenSize = getScreenSize();

            g.fillRect(0, 0, screenSize.width, screenSize.height);
            Draw.renderRetroText(g, new Point(50, 50), 20, Color.RED, Color.YELLOW, description);
    }

    @Override
    protected void onTick() {
        listener.poll();
    }

    @Override
    protected boolean isGameOver() {
        return false;
    }

    public static final String describe(ControllerEnvironment ce) {
        final List<String> lines = new ArrayList<>();
        lines.add("isSupported: " + ce.isSupported());
        lines.add("controllers: " + describeControllers(ce.getControllers()));

        return indent(Text.describeList(lines, false));
    }

    public static final String describeControllers(Controller[] controllers) {
        final List<String> lines = new ArrayList<>();
        for(Controller controller : controllers) {
            if (Controller.Type.GAMEPAD.equals(controller.getType())) {
                lines.add(describeController(controller));
            }

        }
        return Text.describeList(lines, false);
    }

    public static String describeController(Controller controller) {
        final List<String> lines = new ArrayList<>();
//        lines.add("name: " + controller.getName());
        lines.add("type: " + controller.getType());
//        lines.add("class: " + controller.getClass().getName());
        lines.add("components: " + describeComponents(controller.getComponents()));
        return Text.describeList(lines, false);
    }

    public static String describeComponents(Component[] components) {
        final List<String> lines = new ArrayList<>();
        for(Component component : components) {
//            lines.add("name: " + component.getName());
//            lines.add("pollData: " + component.getPollData());
//            lines.add("deadZome: " + component.getDeadZone());
//            lines.add("isRelative: " + component.isRelative());
//            lines.add("isAnalog: " + component.isAnalog());
//            lines.add("identifier: " + component.getIdentifier());
            lines.add("" + component.getName() + " => " + component.getIdentifier().getName());
        }
        return Text.describeList(lines, false);
    }
}
