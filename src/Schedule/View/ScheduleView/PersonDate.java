package Schedule.View.ScheduleView;

import Schedule.Models.MyDateTimeFormatter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class PersonDate {
    private SimpleStringProperty name;
    private SimpleObjectProperty<LocalDateTime> dateTime;
    private final MyDateTimeFormatter dateTimeFormatter= new MyDateTimeFormatter();

    public PersonDate(String name, LocalDateTime dateTime) {
        this.name = new SimpleStringProperty(name);
        this.dateTime = new SimpleObjectProperty<>(dateTime);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime.get();
    }

    public SimpleObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }
}
