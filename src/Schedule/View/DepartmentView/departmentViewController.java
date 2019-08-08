package Schedule.View.DepartmentView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Schedule.Database.DoctorDBHandler;
import Schedule.Models.Department;
import Schedule.Models.Section;
import Schedule.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class departmentViewController {

    private Main main;
    private DoctorDBHandler dbHandler = new DoctorDBHandler(); //A Database handler
    private ObservableList<Department> departments = FXCollections.observableArrayList(); //A lost of the available departments from the DB
    private ObservableList<Section> sections = FXCollections.observableArrayList(); //A list that is updated based on the sections related to the department


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Department> TableView_department;

    @FXML
    private TableColumn<Department, Integer> col_dep_id;

    @FXML
    private TableColumn<Department, String> col_dep_name;

    @FXML
    private TableColumn<Department, String> col_head_of_department;

    @FXML
    private TextField textFieldFName;

    @FXML
    private TextField textFieldLastName;

    @FXML
    private TextField textFieldTitle;

    @FXML
    private TableView<Section> tableView_sections;

    @FXML
    private TableColumn<Section, String> col_section;

    @FXML
    private TableColumn<Section, String> col_head_of_section;

    @FXML
    void initialize() {
        initColumns();
        loadData();
    }

    private void initColumns() {
        //Department Columns
        col_dep_id.setCellValueFactory(new PropertyValueFactory<Department, Integer>("department_id"));
        col_dep_name.setCellValueFactory(new PropertyValueFactory<Department, String>("department_name"));
        col_head_of_department.setCellValueFactory(new PropertyValueFactory<Department, String>("head_of_dep_name"));
        TableView_department.setItems(departments);

        //Section Columns
        col_section.setCellValueFactory(new PropertyValueFactory<Section, String>("section_name"));
        col_head_of_section.setCellValueFactory(new PropertyValueFactory<Section, String>("head_of_section_name"));
        tableView_sections.setItems(sections);
    }

    private void loadData() {
        departments.clear();
        sections.clear();

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

        TableView_department.refresh();
        TableView_department.getSelectionModel().selectFirst();
        TableView_department.requestFocus();
    }

    @FXML
    void goToSectionView(ActionEvent event) throws IOException {
        main.showSections();
    }


    public void openAddDepartmentWindow(ActionEvent event) {

    }

    public void deleteSelectedDepartment(ActionEvent event) {
    }

    public void loadHeadAndSectionsKey(KeyEvent keyEvent) {
       if(!TableView_department.getSelectionModel().isEmpty()) {
           loadHeadAndSections();
       }
    }
    public void loadHeadAndSectionsMouse(MouseEvent mouseEvent) {
        if(!TableView_department.getSelectionModel().isEmpty()) {
            loadHeadAndSections();
        }
    }
    public void loadHeadAndSections(){

        sections.clear();
        textFieldFName.clear();
        textFieldLastName.clear();
        textFieldTitle.clear();


        //Load head doctor information
        String sql = "SELECT departments.department_id, doctors.doctor_id, doctors.fname, doctors.lname, doctors.title, titles.title_id, titles.title_name, departments.headofdepartment_id FROM departments " +
                "INNER JOIN doctors ON doctors.doctor_id=departments.headofdepartment_id " +
                "INNER JOIN titles ON doctors.title=titles.title_id " +
                "WHERE department_id="+Integer.toString(TableView_department.getSelectionModel().getSelectedItem().getDepartment_id());
        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            textFieldFName.setText(rs.getString("fname"));
            textFieldLastName.setText(rs.getString("lname"));
            textFieldTitle.setText(rs.getString("title_name"));

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


        //Load department table information
        String SQLquery="SELECT sections.section_id, sections.section_name, sections.head_of_section, sections.main_section, doctors.doctor_id, doctors.fname, doctors.lname, sections.department_id FROM sections " +
                "INNER JOIN doctors ON doctors.doctor_id=sections.head_of_section "+
                "WHERE department_id="+Integer.toString(TableView_department.getSelectionModel().getSelectedItem().getDepartment_id());
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
        tableView_sections.refresh();

    }
}
