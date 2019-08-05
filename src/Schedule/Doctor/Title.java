package Schedule.Doctor;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Title {
    private SimpleIntegerProperty title_id;
    private SimpleStringProperty title_name;

    public Title(int title_id, String title_name) {
        this.title_id = new SimpleIntegerProperty(title_id);
        this.title_name = new SimpleStringProperty(title_name);
    }

    public int getTitle_id() {
        return title_id.get();
    }

    public SimpleIntegerProperty title_idProperty() {
        return title_id;
    }

    public String getTitle_name() {
        return title_name.get();
    }

    public SimpleStringProperty title_nameProperty() {
        return title_name;
    }

    @Override
    public String toString(){
        return title_name.get();
    }
}
