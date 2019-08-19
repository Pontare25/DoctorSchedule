package Schedule.View.CompetencyView;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import Schedule.Database.DoctorDBHandler;
import Schedule.Models.Competency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class competencyViewController {

    private ObservableList<Competency> competencies = FXCollections.observableArrayList();
    private DoctorDBHandler dbHandler = new DoctorDBHandler();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Competency> competencyTableView;

    @FXML
    private TableColumn<Competency, Integer> id_col;

    @FXML
    private TableColumn<Competency, String> title_col;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ToggleButton toggleBtnEditDesc;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea desscriptionToBeAddedTextArea;

    @FXML
    void initialize() {
        initColumns();
        loadData();

    }

    //Adds a new competency to the competency table in the database, the competency name is set to be unique to hinder duplicates
    @FXML
    void addCompetency(ActionEvent event) {
        if(!titleField.getText().isBlank()){
            String sql = "INSERT INTO competencies(comp_name, comp_desc) VALUES(?,?)";
            if(desscriptionToBeAddedTextArea.getText().isBlank()){
                sql = "INSERT INTO competencies(comp_name) VALUES(?)"; //Since a description is not mandatory
            }

            try (Connection conn = dbHandler.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, titleField.getText());
                if(!desscriptionToBeAddedTextArea.getText().isBlank()){
                    pstmt.setString(2, desscriptionToBeAddedTextArea.getText());
                }
                pstmt.executeUpdate();

                System.out.println("Competency successfully added to the database");
                competencies.clear();
                loadData();
                clearAddFields();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private void clearAddFields() {
        titleField.clear();
        desscriptionToBeAddedTextArea.clear();
        titleField.requestFocus();
    }

    @FXML
    void editDescription(ActionEvent event) {
        //Sets the button to be toggled and makes the description box editable
        if (!competencyTableView.getSelectionModel().isEmpty() && toggleBtnEditDesc.isSelected()){
            descriptionTextArea.setEditable(true);
            toggleBtnEditDesc.setText("Commit changes");
        }
        //Updates the description in the database and sets the text to be uneditable
        if(!competencyTableView.getSelectionModel().isEmpty() && !toggleBtnEditDesc.isSelected()){
            descriptionTextArea.setEditable(false);

            String sql = "UPDATE competencies SET comp_desc = ? "
                    + "WHERE comp_id = ?";

            try (Connection conn = dbHandler.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, descriptionTextArea.getText());
                pstmt.setInt(2, competencyTableView.getSelectionModel().getSelectedItem().getCompID());

                // update
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            toggleBtnEditDesc.setText("Edit Description");
            //FocusModel focusModel = competencyTableView.getFocusModel();

            int index = competencyTableView.getSelectionModel().getFocusedIndex();
            loadData();
            competencyTableView.getSelectionModel().select(index);
            competencyTableView.requestFocus();

        }
    }


    @FXML
    void cancelBtnPressed(ActionEvent event) {
        titleField.clear();
        desscriptionToBeAddedTextArea.clear();
    }



    private void initColumns() {
        id_col.setCellValueFactory(new PropertyValueFactory<Competency, Integer>("compID"));
        title_col.setCellValueFactory(new PropertyValueFactory<Competency, String>("compTitle"));

        competencyTableView.setItems(competencies);
    }

    private void loadData() {
        competencies.clear();
        String SQLquery="SELECT * FROM competencies";

        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                try {
                    //System.out.println(rs.getString("fname"));
                    competencies.add(new Competency(rs.getInt("comp_id"), rs.getString("comp_name"), rs.getString("comp_desc")));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        competencyTableView.refresh();
        competencyTableView.getSelectionModel().selectFirst();
        competencyTableView.requestFocus();
        loadDescription();
    }

    @FXML
    void loadDescriptionKey(KeyEvent event) {
        if(!competencyTableView.getSelectionModel().isEmpty()){
            loadDescription();
        }
    }
    @FXML
    void loadDescriptionMouse(MouseEvent event) {
        if(!competencyTableView.getSelectionModel().isEmpty()){
            loadDescription();
        }
    }
    private void loadDescription() {
        descriptionTextArea.setText(competencyTableView.getSelectionModel().getSelectedItem().getCompDesc());
    }


    public void deleteSelectedCompetency(ActionEvent event) {
        if(!competencyTableView.getSelectionModel().isEmpty()){
            try {
                int comp_id = competencyTableView.getSelectionModel().getSelectedItem().getCompID();
                String sql = "DELETE FROM competencies WHERE comp_id = ?";

                try (Connection conn = dbHandler.connect();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    // set the corresponding param
                    pstmt.setInt(1, comp_id);
                    // execute the delete statement
                    pstmt.executeUpdate();
                    System.out.println("Competency successfully deleted");
                    loadData();

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}
