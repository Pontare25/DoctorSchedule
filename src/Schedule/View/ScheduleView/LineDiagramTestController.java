package Schedule.View.ScheduleView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class LineDiagramTestController {
    ObservableList<String> months = FXCollections.observableArrayList();
    ObservableList<XYChart.Series<Number, /*Date,*/ Number>> scheduleList = FXCollections.observableArrayList(); //First number is the employee_ID, second is the date, third is some kind of performance

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    void load(ActionEvent event) {

        months.addAll("Jan", "Feb", "March", "April");

        XYChart.Series<String, Number>series = new XYChart.Series<String, Number>();
        series.setName("My portfolio");

        //lineChart.setLegendSide(Side.BOTTOM);

        series.getData().add(new XYChart.Data<String, Number>(months.get(0), 200));
        series.getData().add(new XYChart.Data<String, Number>(months.get(1), 500));
        series.getData().add(new XYChart.Data<String, Number>(months.get(2), 300));
        series.getData().add(new XYChart.Data<String, Number>(months.get(3), 600));
        lineChart.getData().add(series);
    }

    @FXML
    void initialize() {
        lineChart.setAnimated(false);

    }
}
