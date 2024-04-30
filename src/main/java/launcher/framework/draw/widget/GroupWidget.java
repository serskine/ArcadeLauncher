package launcher.framework.draw.widget;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GroupWidget extends Widget {

    public final List<Widget> widgetList = new ArrayList<>();

    public GroupWidget(Widget parent) {
        super(parent);
    }

    @Override
    public void onTick() {
        for(Widget widget : widgetList) {
            widget.onTick();
        }
    }

    @Override
    public void onRender(Graphics2D g) {
        for(Widget widget : widgetList) {
            widget.onRender(g);
        }
    }
}
