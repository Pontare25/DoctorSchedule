package Schedule.View.DoctorView;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import Schedule.Database.DoctorDBHandler;
import Schedule.Models.Section;
import Schedule.Models.Title;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class addDoctorController {

    private DoctorDBHandler dbHandler = new DoctorDBHandler();
    private ObservableList<Title> titles = FXCollections.observableArrayList();
    private ObservableList<Section> sections = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameField;


    @FXML
    private ComboBox<Title> titleCombobox;

    @FXML
    private ComboBox<Section> MainSectionCombobox;

    @FXML
    void initialize() {
        try {
            initTitleCombobox();
            initSectionComboBox();
            fnameField.requestFocus();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Adds a doctor to the DB
    @FXML
    void addDoctor(ActionEvent event) {

        if(fnameField.getText().isBlank() || fnameField.getText().isBlank() || titleCombobox.getSelectionModel().isEmpty() || MainSectionCombobox.getSelectionModel().isEmpty()){
            System.out.println("Enter in all Fields");
        }else{
            String sql = "INSERT INTO doctors(fname, lname, title, mainsection) VALUES(?,?,?,?)";

            try (Connection conn = dbHandler.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, fnameField.getText());
                pstmt.setString(2, lnameField.getText());
                pstmt.setInt(3, titleCombobox.getSelectionModel().getSelectedItem().getTitle_id());
                pstmt.setInt(4, MainSectionCombobox.getSelectionModel().getSelectedItem().getSection_id());

                pstmt.executeUpdate();

                System.out.println("Doctor successfully added to the database");
                clearAddFields();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
       // clearAddFields();
    }


    @FXML
    void cancel(ActionEvent event) {

    }




    private void initTitleCombobox() {
        titles.clear();
        sections.clear();
        ////////Title Combobox
        //The below is the alternative way of loading a custom class into a combobox. I later understood the same/better result could be achieved by overriding the toString function
       /* titleCombobox.setCellFactory(new Callback<ListView<Title>, ListCell<Title>>() {
            @Override
            public ListCell<Title> call(ListView<Title> titleListView) {

                final ListCell<Title> cell = new ListCell<Title>(){

                    @Override
                    protected void updateItem(Title t, boolean bln){
                        super.updateItem(t, bln);
                        if(t!= null)
                        {
                            setText(t.getTitle_name());
                            //setText(Integer.toString(t.getTitle_id()) + ": " +t.getTitle_name());
                        }
                        else
                            setText(null);
                    }
                };

                return cell;
            }
        });*/
        String SQLquery="SELECT * FROM titles";
        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                titles.add(new Title(rs.getInt("title_id"), rs.getString("title_name")));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        titleCombobox.setItems(titles);
    }

    private void initSectionComboBox() {
        String SQLquery="SELECT * FROM sections";
        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                //Section(int section_id, String section_name, int head_of_section, int main_section, int department_id)
                sections.add(new Section(rs.getInt("Section_id"), rs.getString("section_name"), rs.getInt("head_of_section"), rs.getInt("main_section"), rs.getInt("department_id")));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        MainSectionCombobox.setItems(sections);
    }

    private void clearAddFields(){
        fnameField.clear();
        lnameField.clear();
        titleCombobox.getSelectionModel().selectFirst();
        MainSectionCombobox.getSelectionModel().selectFirst();
        fnameField.requestFocus();
    }
}
