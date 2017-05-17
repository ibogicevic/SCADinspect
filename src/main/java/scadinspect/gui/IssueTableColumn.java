package scadinspect.gui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TableColumn;
import javafx.stage.Screen;

/**
 * CustomTableColumn to hold the custom and calculated percentWidth property.
 * @author Tim Walter
 */
public class IssueTableColumn<S,T> extends TableColumn<S, T> {
    private final SimpleDoubleProperty percentWidth = new SimpleDoubleProperty();
    private final Double screenWidth = Screen.getPrimary().getVisualBounds().getWidth() * 0.9;

    public IssueTableColumn(String columnName){
        super(columnName);
    }

    public SimpleDoubleProperty percentWidth() {
        return percentWidth;
    }

    public double getPercentWidth() {
        return percentWidth.get();
    }

    public void setPercentWidth(double percent) {;
        this.percentWidth.set(screenWidth * percent);
    }
}