package launcher.framework.controls.virtual;

public class ButtonJoystickConfig<ButtonId> {

    public final ButtonId north, east, south, west;

    public ButtonJoystickConfig(final ButtonId north, final ButtonId east, final ButtonId south, final ButtonId west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }


}
