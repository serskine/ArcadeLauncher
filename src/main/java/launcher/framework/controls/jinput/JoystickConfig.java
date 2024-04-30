package launcher.framework.controls.jinput;

import net.java.games.input.Component;

public class JoystickConfig<AxisId> {
    public final AxisId xAxis, yAxis;

    public JoystickConfig(final AxisId xAxis, final AxisId yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
}
