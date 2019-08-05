package Schedule.View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Schedule.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainItemsController {

    private Main main;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void showCompetencies(ActionEvent event) throws IOException {
        main.showCompetencies();
    }

    @FXML
    void showDepartments(ActionEvent event) throws IOException{
        main.showDepartments();
    }

    @FXML
    void showDoctors(ActionEvent event) throws IOException {
        main.showDoctors();
    }

    public void showSections(ActionEvent event) throws  IOException{
        main.showSections();
    }

    @FXML
    void initialize() {

    }


}
