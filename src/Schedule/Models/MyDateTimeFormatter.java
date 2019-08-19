package Schedule.Models;

import java.time.format.DateTimeFormatter;

public class MyDateTimeFormatter {
    DateTimeFormatter dateTimeFormatter;

    public MyDateTimeFormatter() {
        this.dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}
