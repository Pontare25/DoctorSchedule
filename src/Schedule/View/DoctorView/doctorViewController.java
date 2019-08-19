package Schedule.View.DoctorView;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import Schedule.Database.DoctorDBHandler;
import Schedule.Models.Doctor;
import Schedule.Main;
import Schedule.Models.Competency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class doctorViewController {
    private Main main;
    private DoctorDBHandler dbHandler = new DoctorDBHandler();
   private ObservableList<Doctor> doctors = FXCollections.observableArrayList();
   private ObservableList<Competency> competencies = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;


    //Doctor Tableview
    @FXML
    private TableView<Doctor> DoctorTableView;
    @FXML
    private TableColumn<Doctor, Integer> doctor_id_col;
    @FXML
    private TableColumn<Doctor, String> doctor_name_col;
    @FXML
    private TableColumn<Doctor, Boolean> doctor_avail_col;
    @FXML
    private TableColumn<Doctor, String> doctor_title_col;
    @FXML
    private TableColumn<Doctor, String> doctor_available_col;
    @FXML
    private TableColumn<Doctor, Boolean> doctor_onCall_col;
    @FXML
    private TableColumn<Doctor, String> doctor_mainSection_col;



    //Competency Tableview
    @FXML
    private TableView<Competency> CompetencyTableView;

    @FXML
    private TableColumn<Competency, Integer> comp_id_col;

    @FXML
    private TableColumn<Competency,String> comp_title_col;

    @FXML
    private TextArea comp_desc_textarea;

    @FXML
    void initialize() {
        initTables();
        loadData();
    }

    private void initTables() {
        //Doctor TableView
        doctor_id_col.setCellValueFactory(new PropertyValueFactory<Doctor, Integer>("id"));
        doctor_name_col.setCellValueFactory(new PropertyValueFactory<Doctor, String>("fullName"));
        doctor_avail_col.setCellValueFactory(new PropertyValueFactory<Doctor, Boolean>("available"));
        doctor_title_col.setCellValueFactory(new PropertyValueFactory<Doctor, String>("title"));
        doctor_onCall_col.setCellValueFactory(new PropertyValueFactory<Doctor, Boolean>("onCall"));
        doctor_mainSection_col.setCellValueFactory(new PropertyValueFactory<Doctor, String>("mainSection"));

        //Competency Tableview
        comp_id_col.setCellValueFactory(new PropertyValueFactory<Competency, Integer>("compID"));
        comp_title_col.setCellValueFactory(new PropertyValueFactory<Competency, String>("compTitle"));
    }

    //Loads all the doctors ftom the DB
    private void loadData() {
        doctors.clear();
        String SQLquery="SELECT doctors.doctor_id, doctors.fname, doctors.lname, doctors.title, doctors.oncall, doctors.available, doctors.mainsection, titles.title_id, titles.title_name, sections.section_id, sections.section_name FROM doctors " +
                "INNER JOIN sections ON sections.section_id=doctors.mainsection " +
                "INNER JOIN titles ON titles.title_id=doctors.title";

        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                try {
                    doctors.add(new Doctor(rs.getInt("doctor_id"), rs.getString("fname"), rs.getString("lname"), rs.getString("title_name"), rs.getString("section_name"), rs.getBoolean("available"), rs.getBoolean("oncall")));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        DoctorTableView.setItems(doctors);//sets the tableview data
        DoctorTableView.getSelectionModel().selectFirst();//makes the first item in the tableview focused
        DoctorTableView.requestFocus();
        try {
            loadCompData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Loads the competencies tied to the specific selected doctor
    public void loadCompKey(KeyEvent keyEvent) {
        if (!DoctorTableView.getSelectionModel().isEmpty()){
            loadCompData();
        }
    }
    public void loadCompMouse(MouseEvent mouseEvent) {
        if (!DoctorTableView.getSelectionModel().isEmpty()){
            loadCompData();
        }
    }
    public void loadCompData(){
        competencies.clear();

       String SQLquery="SELECT doctor_competencies.doctor_id, doctor_competencies.comp_id, competencies.comp_id, competencies.comp_name, competencies.comp_desc FROM doctor_competencies " +
        "INNER JOIN competencies ON competencies.comp_id=doctor_competencies.comp_id "+
        "WHERE doctor_competencies.doctor_id="+Integer.toString(DoctorTableView.getSelectionModel().getSelectedItem().getId());

        try {
            DoctorTableView.getSelectionModel().getSelectedItem().getId();
            try (Connection conn = dbHandler.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(SQLquery)){
                while(rs.next()){
                    try {
                        competencies.add(new Competency(rs.getInt("comp_id"), rs.getString("comp_name"), rs.getString("comp_desc")));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        CompetencyTableView.setItems(competencies);
    }

    public void loadCompDescKey(KeyEvent keyEvent) {
        if(!CompetencyTableView.getSelectionModel().isEmpty()){
            loadCompDesc();
        }
    }
    public void loadCompDescMouse(MouseEvent mouseEvent) {
        if(!CompetencyTableView.getSelectionModel().isEmpty()){
            loadCompDesc();
        }
    }
    public void loadCompDesc(){
       try {
           comp_desc_textarea.setText(CompetencyTableView.getSelectionModel().getSelectedItem().getCompDesc());
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @FXML
    void loadEmployeeData(ActionEvent event){
        loadData();
    }
    @FXML
    void deleteDoctor(ActionEvent event) {
        try {
            int id = DoctorTableView.getSelectionModel().getSelectedItem().getId();
            String sql = "DELETE FROM doctors WHERE doctor_id = ?";

            try (Connection conn = dbHandler.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setInt(1, id);
                // execute the delete statement
                pstmt.executeUpdate();
                System.out.println("Doctor successfully deleted");
                loadData();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void deleteSelected_Comp(ActionEvent event) {
        try {
            int doctor_id = DoctorTableView.getSelectionModel().getSelectedItem().getId();
            int comp_id = CompetencyTableView.getSelectionModel().getSelectedItem().getCompID();
            String sql = "DELETE FROM doctor_competencies WHERE doctor_id = ? AND comp_id=?";

            try (Connection conn = dbHandler.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setInt(1, doctor_id);
                pstmt.setInt(2, comp_id);
                // execute the delete statement
                pstmt.executeUpdate();
                System.out.println("Competency successfully deleted");
                loadCompData();
                CompetencyTableView.requestFocus();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void openAddComp(ActionEvent event) throws IOException{
        if(!DoctorTableView.getSelectionModel().isEmpty()) {
            try {
                //Passing the id of the doctor to the next view the information of where to add the competencies and what competencies are already added
                main.showAddCompetencyWindow(DoctorTableView.getSelectionModel().getSelectedItem().getId());
            }catch (Exception e){
                e.printStackTrace();
            }
        }else System.out.println("Select a doctor to add competencies to");
    }
    @FXML
    void openAddDoctor(ActionEvent event) throws IOException {
        main.showAddDoctorWindow();
        loadData();
        DoctorTableView.getSelectionModel().selectLast();
    }

}
