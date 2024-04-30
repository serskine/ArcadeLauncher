package launcher.framework.draw.widget;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwitchWidget extends Widget {

    public final List<Widget> widgetList = new ArrayList<>();

    private Integer selectedIndex;

    public SwitchWidget(Widget parent) {
        super(parent);
    }

    public Integer getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(Integer selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public Widget getSelectedWidget() {
        if (selectedIndex!=null && selectedIndex>=0 && selectedIndex < widgetList.size()) {
            return widgetList.get(selectedIndex);
        } else {
            return null;
        }
    }

    @Override
    public void onRender(Graphics2D g) {
        final Widget selectedWidget = getSelectedWidget();
        if (selectedWidget!=null) {
            selectedWidget.onRender(g);
        }
    }



    @Override
    public void onTick() {
        final Widget selectedWidget = getSelectedWidget();
        if (selectedWidget!=null) {
            selectedWidget.onTick();
        }
    }
}
