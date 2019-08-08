package Schedule.Models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class CompetencyReq {
    private SimpleIntegerProperty mainSectionId;
    private SimpleIntegerProperty compId;
    private SimpleStringProperty compName;
    private SimpleBooleanProperty assigned;

    public CompetencyReq(int mainSectionId, int compId, String compName, boolean assigned) {
        this.mainSectionId = new SimpleIntegerProperty(mainSectionId);
        this.compId = new SimpleIntegerProperty(compId);
        this.compName = new SimpleStringProperty(compName);
        this.assigned = new SimpleBooleanProperty(assigned);
    }

    public int getMainSectionId() {
        return mainSectionId.get();
    }

    public SimpleIntegerProperty mainSectionIdProperty() {
        return mainSectionId;
    }

    public int getCompId() {
        return compId.get();
    }

    public SimpleIntegerProperty compIdProperty() {
        return compId;
    }

    public String getCompName() {
        return compName.get();
    }

    public SimpleStringProperty compNameProperty() {
        return compName;
    }

    public boolean isAssigned() {
        return assigned.get();
    }

    public SimpleBooleanProperty assignedProperty() {
        return assigned;
    }

    @Override
    public String toString(){
        return compName.get();
    }
}
