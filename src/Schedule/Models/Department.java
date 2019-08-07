package Schedule.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Department {
    private SimpleIntegerProperty department_id;
    private SimpleStringProperty department_name;
    private SimpleIntegerProperty head_of_dep;
    private SimpleStringProperty head_of_dep_name;

    public Department(int department_id, String department_name, int head_of_dep, String head_of_dep_name) {
        this.department_id = new SimpleIntegerProperty(department_id);
        this.department_name = new SimpleStringProperty(department_name);
        this.head_of_dep = new SimpleIntegerProperty(head_of_dep);
        this.head_of_dep_name = new SimpleStringProperty(head_of_dep_name);
    }

    public int getDepartment_id() {
        return department_id.get();
    }

    public SimpleIntegerProperty department_idProperty() {
        return department_id;
    }

    public String getDepartment_name() {
        return department_name.get();
    }

    public SimpleStringProperty department_nameProperty() {
        return department_name;
    }

    public int getHead_of_dep() {
        return head_of_dep.get();
    }

    public SimpleIntegerProperty head_of_depProperty() {
        return head_of_dep;
    }

    public String getHead_of_dep_name() {
        return head_of_dep_name.get();
    }

    public SimpleStringProperty head_of_dep_nameProperty() {
        return head_of_dep_name;
    }

    @Override
    public String toString(){
        return department_name.get();
    }
}
