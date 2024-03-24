package launcher.framework.controls.jinput;

import net.java.games.input.Component;

public class JoystickConfig {
    public final Component.Identifier xAxis, yAxis;

    public JoystickConfig(final Component.Identifier xAxis, final Component.Identifier yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
}
