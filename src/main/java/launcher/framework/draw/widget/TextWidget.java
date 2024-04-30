package launcher.framework.draw.widget;

import launcher.framework.draw.Draw;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TextWidget extends DrawWidget {

    private int textHeight = 0;
    private String text;


    public TextWidget(Widget parent) {
        super(parent);
    }

    @Override
    public void onRender(Graphics2D g) {
        if (text!=null) {
            Draw.renderRetroText(
                g,
                getAbsoluteLocation(),
                getTextHeight(),
                getLineColor(),
                getFillColor(),
                getText()
            );
        }
    }

    public int getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(int textHeight) {
        Math.max(0, this.textHeight = textHeight);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void appendText(String text) {
        this.text += text;
    }

    @Override
    public void onTick() {

    }

    public final Dimension getTextSize() {
        return Draw.getTextSize(getTextHeight(), getText());
    }

    public Rectangle2D getRelativeTextBounds() {
        final Dimension size = getTextSize();
        final Rectangle2D r = new Rectangle2D.Double(getRelativeLocation().getX(), getRelativeLocation().getY(), size.getWidth(), size.getHeight());
        return r;
    }
}
