package Schedule.View.SectionView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Schedule.Database.DoctorDBHandler;
import Schedule.Models.Competency;
import Schedule.Models.Department;
import Schedule.Models.Section;
import Schedule.Models.CompetencyReq;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class sectionViewController {

    private ObservableList<Department> departments = FXCollections.observableArrayList();
    private ObservableList<Section> sections = FXCollections.observableArrayList();
   // private ObservableList<TreeItem> subsections = FXCollections.observableArrayList();

    private DoctorDBHandler dbHandler = new DoctorDBHandler();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Department> departmentCombobox;

    @FXML
    private TableView<Section> tableViewSections;

    @FXML
    private TableColumn<Section, Integer> col_sectionID;

    @FXML
    private TableColumn<Section, String> col_sectionName;

    @FXML
    private TableColumn<Section, String> col_HeadOfSection;

    @FXML
    private TreeTableView<CompetencyReq> treeTableCompetency;

    @FXML
    private TreeTableColumn<CompetencyReq, String> tree_col_comp;

    @FXML
    private TreeTableColumn<CompetencyReq, Boolean> tree_col_assigned;

    TreeItem<CompetencyReq> comp1 = new TreeItem<>(new CompetencyReq(1, 2, "Comp1", true));
    TreeItem<CompetencyReq> comp2 = new TreeItem<>(new CompetencyReq(1, 4, "comp2", false));
    TreeItem<CompetencyReq> comp3 = new TreeItem<>(new CompetencyReq(2, 5, "comp3", false));
    TreeItem<CompetencyReq> comp4 = new TreeItem<>(new CompetencyReq(2, 6, "comp4", true));


    TreeItem<CompetencyReq> root = new TreeItem<>(new CompetencyReq(1, 2, "Sections", true));

    TreeItem<CompetencyReq> section1 = new TreeItem<>(new CompetencyReq(1, 2, "Section 1", true));

    TreeItem<CompetencyReq> section2 = new TreeItem<>(new CompetencyReq(2, 2, "Section 2", false));

    @FXML
    void initialize() {
        initColumns();
        loadDepartments();
        loadCompetencyReqTreeTable();

    }

    private void initColumns() {
        //Sections table
        col_sectionID.setCellValueFactory(new PropertyValueFactory<Section, Integer>("section_id"));
        col_sectionName.setCellValueFactory(new PropertyValueFactory<Section, String>("section_name"));
        col_HeadOfSection.setCellValueFactory(new PropertyValueFactory<Section, String>("head_of_section_name"));
        tableViewSections.setItems(sections);

        //Competency Table
        tree_col_comp.setCellValueFactory(param -> param.getValue().getValue().compNameProperty());
        tree_col_assigned.setCellValueFactory(param -> param.getValue().getValue().assignedProperty());

    }

    //Loads the departments from the database
    private void loadDepartments() {
        departments.clear();

        String SQLquery="SELECT departments.department_id, departments.department_name, doctors.doctor_id, doctors.fname, doctors.lname, departments.headofdepartment_id FROM departments " +
                "INNER JOIN doctors ON doctors.doctor_id=departments.headofdepartment_id";
        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                try {
                    //Department(int department_id, String department_name, int head_of_dep, String head_of_dep_name)
                    departments.add(new Department(rs.getInt("department_id"), rs.getString("department_name"), rs.getInt("headofdepartment_id"), (rs.getString("fname") + ", " + rs.getString("lname"))));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        departmentCombobox.setItems(departments);
        departmentCombobox.getSelectionModel().selectFirst();
        loadSections(new ActionEvent());
    }


    //loads the sections table based on what department is selected
    @FXML
    void loadSections(ActionEvent event) {
        sections.clear();

        String SQLquery="SELECT sections.section_id, sections.section_name, sections.head_of_section, sections.main_section, doctors.doctor_id, doctors.fname, doctors.lname, sections.department_id FROM sections " +
                "INNER JOIN doctors ON doctors.doctor_id=sections.head_of_section "+
                "WHERE department_id="+Integer.toString(departmentCombobox.getSelectionModel().getSelectedItem().getDepartment_id());
        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                try {
                    //Section(int section_id, String section_name, int head_of_section, String head_of_section_name, int main_section, int department_id) {
                        sections.add(new Section(rs.getInt("section_id"), rs.getString("section_name"), rs.getInt("head_of_section"), (rs.getString("fname") +", " + rs.getString("lname")), rs.getInt("main_section"), rs.getInt("department_id")));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        tableViewSections.refresh();
        tableViewSections.requestFocus();
        tableViewSections.getSelectionModel().selectFirst();
        loadSectionInfo();
    }

    private void loadSectionInfo(){

    }

    //Should load the different subsections in a treetable with the competencies required and if they are assigned.
    private void loadCompetencyReqTreeTable() {
        root.getChildren().setAll(section1, section2);
        section1.getChildren().setAll(comp1, comp4);
        section2.getChildren().setAll(comp3, comp4);
        treeTableCompetency.setRoot(root);
        treeTableCompetency.setShowRoot(false); //Removes the ugly top section necessary
    }


    public void openAddSection(ActionEvent event) {

    }

    public void deleteSection(ActionEvent event) {

    }

    public void editReqComp(ActionEvent event) {

    }
}
