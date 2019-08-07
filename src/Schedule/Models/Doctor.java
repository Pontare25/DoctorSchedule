package Schedule.Models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Doctor {

   private SimpleIntegerProperty id;
   private SimpleStringProperty fname;
   private SimpleStringProperty lname;
   private SimpleStringProperty title;
   private SimpleStringProperty mainSection;
   private SimpleBooleanProperty available;
   private SimpleBooleanProperty onCall;
   private SimpleStringProperty fullName;

    public Doctor(int id, String fname, String lname, String title, String mainSection, boolean available, boolean onCall) {
        this.id = new SimpleIntegerProperty(id);
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.title = new SimpleStringProperty(title);
        this.mainSection = new SimpleStringProperty(mainSection);
        this.available = new SimpleBooleanProperty(available);
        this.onCall = new SimpleBooleanProperty(onCall);
        this.fullName = new SimpleStringProperty(fname + ", " + lname);
    }

    public Doctor(int id, String fname, String lname, String title, String mainSection) {
        this.id = new SimpleIntegerProperty(id);
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.title = new SimpleStringProperty(title);
        this.mainSection = new SimpleStringProperty(mainSection);
        this.available = new SimpleBooleanProperty(true);
        this.onCall = new SimpleBooleanProperty(false);
        this.fullName = new SimpleStringProperty(fname + ", " + lname);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getFname() {
        return fname.get();
    }

    public SimpleStringProperty fnameProperty() {
        return fname;
    }

    public String getLname() {
        return lname.get();
    }

    public SimpleStringProperty lnameProperty() {
        return lname;
    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getMainSection() {
        return mainSection.get();
    }

    public SimpleStringProperty mainSectionProperty() {
        return mainSection;
    }

    public boolean isAvailable() {
        return available.get();
    }

    public SimpleBooleanProperty availableProperty() {
        return available;
    }

    public boolean isOnCall() {
        return onCall.get();
    }

    public SimpleBooleanProperty onCallProperty() {
        return onCall;
    }
}
