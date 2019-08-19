package Schedule.View.ScheduleView;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Schedule.Models.MyDateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class DateTableViewTestController {

    MyDateTimeFormatter dtFormatter = new MyDateTimeFormatter();
    ObservableList<PersonDate> personDates = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textfield;

    @FXML
    private Spinner hourSpinner;

    @FXML
    private Spinner minuteSpinner;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TableView<PersonDate> tableview;

    @FXML
    private TableColumn<PersonDate, String> col1;

    @FXML
    private TableColumn<PersonDate, LocalDateTime> col2;

    @FXML
    void add(ActionEvent event) {
        try
        {
            if(!datepicker.getEditor().getText().isBlank() && !textfield.getText().isBlank()){

                    LocalDateTime localDateTime = LocalDateTime.of(datepicker.getValue(),LocalTime.of(Integer.parseInt(hourSpinner.getEditor().getText()), Integer.parseInt(minuteSpinner.getEditor().getText())));
                    personDates.add(new PersonDate(textfield.getText(), localDateTime));

                //Gets the local date from the datepicker and combines it with a localtime generated from the two spinners (hourspinner and minutespinner)
                    System.out.println(textfield.getText() + ", "+ localDateTime.format(dtFormatter.getDateTimeFormatter()));
                    tableview.refresh();

                } else System.out.println("Enter name and, select a date and time!");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        initTimeSpinners();
        initTable();
        loadData();

    }

    private void loadData() {
        personDates.addAll(
                new PersonDate("Pontus", LocalDateTime.of(LocalDate.of(2019, 05, 24), LocalTime.of(23, 13))),
                new PersonDate("Per", LocalDateTime.of(LocalDate.of(1996, 8, 21), LocalTime.of(11, 31)))
        );
        tableview.refresh();

    }

    private void initTable() {
        col1.setCellValueFactory(new PropertyValueFactory<PersonDate, String>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<PersonDate, LocalDateTime>("dateTime"));
        tableview.setItems(personDates);
    }

    private void initTimeSpinners() {
        //Datepicker
        datepicker.setShowWeekNumbers(true);
        datepicker.setValue(LocalDate.now());
        //Timespinners
            //Hour time spinner
        SpinnerValueFactory<Integer> hourValueFactory= new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 12);
        this.hourSpinner.setValueFactory(hourValueFactory);
        hourSpinner.setEditable(true);
            //Minute time spinner
        SpinnerValueFactory<Integer> minuteValueFactory= new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 5);
        this.minuteSpinner.setValueFactory(minuteValueFactory);
        minuteSpinner.setEditable(true);
    }

}
