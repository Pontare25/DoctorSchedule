package Schedule.DepartmentsAndSections;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Section {

    private SimpleIntegerProperty section_id;
    private SimpleStringProperty section_name;
    private SimpleIntegerProperty head_of_section;
    private SimpleStringProperty head_of_section_name;
    private SimpleIntegerProperty main_section;
    private SimpleStringProperty main_section_name;
    private SimpleIntegerProperty department_id;
    private SimpleStringProperty department_name;

    public Section(int section_id, String section_name, int head_of_section, String head_of_section_name, int main_section, String main_section_name,  int department_id, String department_name) {
        this.section_id = new SimpleIntegerProperty(section_id);
        this.section_name = new SimpleStringProperty(section_name);
        this.head_of_section = new SimpleIntegerProperty(head_of_section);
        this.head_of_section_name = new SimpleStringProperty(head_of_section_name);
        this.main_section = new SimpleIntegerProperty(main_section);
        this.main_section_name = new SimpleStringProperty(main_section_name);
        this.department_id = new SimpleIntegerProperty(department_id);
        this.department_name = new SimpleStringProperty(department_name);
    }

    public Section(int section_id, String section_name, int head_of_section, int main_section, int department_id){
        this.section_id = new SimpleIntegerProperty(section_id);
        this.section_name = new SimpleStringProperty(section_name);
        this.head_of_section = new SimpleIntegerProperty(head_of_section);
        this.main_section = new SimpleIntegerProperty(main_section);
        this.department_id = new SimpleIntegerProperty(department_id);

        this.head_of_section_name = new SimpleStringProperty("UNASSIGNED");
        this.main_section_name = new SimpleStringProperty("UNASSIGNED");
        this.department_name = new SimpleStringProperty("UNASSIGNED");
    }

    public Section(int section_id, String section_name, int head_of_section, String head_of_section_name, int main_section, int department_id) {
        this.section_id = new SimpleIntegerProperty(section_id);
        this.section_name = new SimpleStringProperty(section_name);
        this.head_of_section = new SimpleIntegerProperty(head_of_section);
        this.main_section = new SimpleIntegerProperty(main_section);
        this.department_id = new SimpleIntegerProperty(department_id);
        this.head_of_section_name = new SimpleStringProperty(head_of_section_name);

        this.main_section_name = new SimpleStringProperty("UNASSIGNED");
        this.department_name = new SimpleStringProperty("UNASSIGNED");
    }



    public int getSection_id() {
        return section_id.get();
    }

    public SimpleIntegerProperty section_idProperty() {
        return section_id;
    }

    public String getSection_name() {
        return section_name.get();
    }

    public SimpleStringProperty section_nameProperty() {
        return section_name;
    }

    public int getHead_of_section() {
        return head_of_section.get();
    }

    public SimpleIntegerProperty head_of_sectionProperty() {
        return head_of_section;
    }

    public int getMain_section() {
        return main_section.get();
    }

    public SimpleIntegerProperty main_sectionProperty() {
        return main_section;
    }

    public int getDepartment_id() {
        return department_id.get();
    }

    public SimpleIntegerProperty department_idProperty() {
        return department_id;
    }

    public String getHead_of_section_name() {
        return head_of_section_name.get();
    }

    public SimpleStringProperty head_of_section_nameProperty() {
        return head_of_section_name;
    }

    public String getMain_section_name() {
        return main_section_name.get();
    }

    public SimpleStringProperty main_section_nameProperty() {
        return main_section_name;
    }

    public String getDepartment_name() {
        return department_name.get();
    }

    public SimpleStringProperty department_nameProperty() {
        return department_name;
    }

    @Override
    public String toString(){
        return section_name.get();
    }
}
