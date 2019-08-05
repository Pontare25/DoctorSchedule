package Schedule.View;

import Schedule.Main;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainViewController {

    private Main main;

    @FXML
    public void goHome() throws IOException {
        main.showMainItems();
    }
}
