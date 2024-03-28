package launcher.framework.controls.jinput;

import net.java.games.input.Component;

public class JoystickConfig {
    public final Component.Identifier.Axis xAxis, yAxis;

    public JoystickConfig(final Component.Identifier.Axis xAxis, final Component.Identifier.Axis yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
}
