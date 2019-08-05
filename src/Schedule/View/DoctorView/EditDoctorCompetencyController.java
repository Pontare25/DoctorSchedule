package Schedule.View.DoctorView;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import Schedule.Database.DoctorDBHandler;
import Schedule.competency.Competency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditDoctorCompetencyController {
   private DoctorDBHandler dbHandler = new DoctorDBHandler();
    private ObservableList<Competency> availableCompetencies = FXCollections.observableArrayList();
    private ObservableList<Competency> toBeAddedCompetencies = FXCollections.observableArrayList();
    private ObservableList<Competency> toBeDeletedCompetencies = FXCollections.observableArrayList();
    private ObservableList<Competency> registeredCompetencies = FXCollections.observableArrayList();
    private int personId;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Competency> Comps_availableTableView;

    @FXML
    private TableColumn<Competency, String> Available_col;

    @FXML
    private TableView<Competency> to_be_added;

    @FXML
    private TableColumn<Competency, String> to_be_added_Col;

    @FXML
    private TableView<Competency> to_be_deleted_table;

    @FXML
    private TableColumn<Competency, String> to_be_deleted_col;

    @FXML
    private TableView<Competency> registered_table;

    @FXML
    private TableColumn<Competency, String> registered_col;

    @FXML
    private Label nameLabel;

    @FXML
   public void initialize() {
        initColumns();
        //loadData(); //Causes nullpointer exception if not initialized via main by passing the information about which doctor
    }


    public void setPersonId(int personId) {
        this.personId=personId;
    }

    private void initColumns() {
        Available_col.setCellValueFactory(new PropertyValueFactory<Competency, String>("compTitle"));
        to_be_added_Col.setCellValueFactory(new PropertyValueFactory<Competency, String>("compTitle"));
        to_be_deleted_col.setCellValueFactory(new PropertyValueFactory<Competency, String>("compTitle"));
        registered_col.setCellValueFactory(new PropertyValueFactory<Competency, String>("compTitle"));
    }

    public void loadData() {
        nameLabel.setText(null);
        availableCompetencies.clear();
        toBeAddedCompetencies.clear();
        toBeDeletedCompetencies.clear();
        registeredCompetencies.clear();

        loadName();
        loadRegisteredCompetencies();
        loadAvailableCompetencies();

        registered_table.setItems(registeredCompetencies);
        Comps_availableTableView.setItems(availableCompetencies);
        to_be_added.setItems(toBeAddedCompetencies);
        to_be_deleted_table.setItems(toBeDeletedCompetencies);
    }

    private void loadName() {
        String SQLquery = "SELECT doctors.fname, doctors.lname FROM doctors WHERE doctors.doctor_id="+Integer.toString(personId);
        try {
            try (Connection conn = dbHandler.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(SQLquery)){
                    try {
                        nameLabel.setText(rs.getString("fname")+ ", "+ rs.getString("lname"));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Loads only the competencies that are not already added to the doctor to prevent duplicates (See for-loop below)
    private void loadAvailableCompetencies() {
        String SQLquery = "SELECT * FROM competencies";

        try {
            try (Connection conn = dbHandler.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(SQLquery)){
                while(rs.next()){
                    try {
                        boolean unique = true; //Checks if the doctor already has that competency registered in order to prevent duplicates
                        for (int i = 0; i < registeredCompetencies.size();i++){
                            if (registeredCompetencies.get(i).getCompID()==rs.getInt("comp_id")){
                                unique=false;
                            }
                        }
                        if(unique){
                            availableCompetencies.add(new Competency(rs.getInt("comp_id"), rs.getString("comp_name"), rs.getString("comp_desc")));
                        }
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
        Comps_availableTableView.refresh();
    }

    //Loads all the competencies that are already registered to the doctor
    private void loadRegisteredCompetencies() {
        try {
            String SQLquery="SELECT doctor_competencies.doctor_id, doctor_competencies.comp_id, competencies.comp_id, competencies.comp_name, competencies.comp_desc FROM doctor_competencies " +
                    "INNER JOIN competencies ON competencies.comp_id=doctor_competencies.comp_id "+
                    "WHERE doctor_competencies.doctor_id="+Integer.toString(personId);
            try (Connection conn = dbHandler.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(SQLquery)){
                while(rs.next()){
                    try {
                        registeredCompetencies.add(new Competency(rs.getInt("comp_id"), rs.getString("comp_name"), rs.getString("comp_desc")));
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
        registered_table.refresh();
    }

    //Checks if there has been any change to the competencies registered to the doctor in question and in that case updates the DB accordingly
    @FXML
    void Commit_changes(ActionEvent event) {

        //Checks if there are any competencies to be deleted
        if(!toBeDeletedCompetencies.isEmpty()){
            String sql = "DELETE FROM doctor_competencies WHERE doctor_id = ? AND comp_id=?";

            for(int i = 0; i < toBeDeletedCompetencies.size(); i++){
                try (Connection conn = dbHandler.connect();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    // set the corresponding param
                    pstmt.setInt(1, personId);
                    pstmt.setInt(2, toBeDeletedCompetencies.get(i).getCompID());
                    // execute the delete statement
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }


        }
        //Checks if there are any competencies to be added
        if(!toBeAddedCompetencies.isEmpty()){
            for(int i =0; i < toBeAddedCompetencies.size(); i++){
                    String sql = "INSERT INTO doctor_competencies(doctor_id, comp_id) VALUES(?,?)";

                    try (Connection conn = dbHandler.connect();
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, personId);
                        pstmt.setInt(2, toBeAddedCompetencies.get(i).getCompID());
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
        }
    loadData();
    }

    //adds competencies to a temporary list which after being commited is updated in the DB (see Commit_changes)
    @FXML
    void available_add_btn_pressed(ActionEvent event) {
        if (!Comps_availableTableView.getSelectionModel().isEmpty()) {
            try {
                toBeAddedCompetencies.add(Comps_availableTableView.getSelectionModel().getSelectedItem());
                availableCompetencies.remove(Comps_availableTableView.getSelectionModel().getSelectedIndex());
              refreshAllTables();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else System.out.println("Select a competency from Available");
    }

    @FXML
    void available_remove_btn_pressed(ActionEvent event) {
        if(!to_be_added.getSelectionModel().isEmpty()){
           try {
               availableCompetencies.add(to_be_added.getSelectionModel().getSelectedItem());
               toBeAddedCompetencies.remove(to_be_added.getSelectionModel().getSelectedIndex());
           refreshAllTables();
           }catch (Exception e){
               e.printStackTrace();
           }
        }
        else System.out.println("Select a competency from To be added");
    }

    @FXML
    void cancel_ALL_changes(ActionEvent event) {


    }

    @FXML
    void cancel_remove_button_pressed(ActionEvent event) {
        if(!to_be_deleted_table.getSelectionModel().isEmpty()){
            try {
                registeredCompetencies.add(to_be_deleted_table.getSelectionModel().getSelectedItem());
                toBeDeletedCompetencies.remove(to_be_deleted_table.getSelectionModel().getSelectedIndex());
              refreshAllTables();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else System.out.println("Select a competency from To be deleted");
    }

    @FXML
    void registered_remove_btn_pressed(ActionEvent event) {
        if(!registered_table.getSelectionModel().isEmpty()){
            try {
                toBeDeletedCompetencies.add(registered_table.getSelectionModel().getSelectedItem());
                registeredCompetencies.remove(registered_table.getSelectionModel().getSelectedIndex());
               refreshAllTables();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else System.out.println("Select a competency from Registered");

    }

    private void refreshAllTables(){
        to_be_deleted_table.refresh();
        registered_table.refresh();
        Comps_availableTableView.refresh();
        to_be_added.refresh();
    }

    @FXML
    void addAllAvailable(ActionEvent event) {
        toBeAddedCompetencies.addAll(availableCompetencies);
        availableCompetencies.clear();
        refreshAllTables();
    }

    @FXML
    void cancelAllToBeDeleted(ActionEvent event) {
        registeredCompetencies.addAll(toBeDeletedCompetencies);
        toBeDeletedCompetencies.clear();
        refreshAllTables();
    }

    //Since only the competencies that are not registered are available this means that the user has to refresh the view to get access to the deleted competencies again
    //This should be refined
    @FXML
    void removeAllRegistered(ActionEvent event) {
        toBeDeletedCompetencies.addAll(registeredCompetencies);
        registeredCompetencies.clear();
        refreshAllTables();
        System.out.println("OBS! YOU HAVE TO REFRESH TO BE ABLE TO ADD THE COMPETENCIES AGAIN");
    }

    @FXML
    void removeAllToBeAdded(ActionEvent event) {
        availableCompetencies.addAll(toBeAddedCompetencies);
        toBeAddedCompetencies.clear();
        refreshAllTables();

    }

}
