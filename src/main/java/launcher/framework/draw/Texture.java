package launcher.framework.draw;

import java.awt.*;

public class Texture {
    public Color frontLineColor;
    public Color backLineColor;
    public Color frontFillColor;
    public Color backFillColor;

    public Texture() {
        this(Color.BLACK, Color.WHITE, null, null);
    }

    public Texture(Color frontLineColor, Color frontFillColor, Color backLineColor, Color backFillColor) {
        this.frontLineColor = frontLineColor;
        this.backLineColor = backLineColor;
        this.frontFillColor = frontFillColor;
        this.backFillColor = backFillColor;
    }
}
