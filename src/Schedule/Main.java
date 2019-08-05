package Schedule;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Schedule.Database.DoctorDBHandler;
import Schedule.View.DoctorView.EditDoctorCompetencyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Main extends Application{


//The main is primarily used as a way to load different views in a more cohesive way. Instead of haveing to cloase and open different windows
//The windows are instead loaded into the center of the borderpane of the mainlayout
//Smaller views such as add stages are also accessed via the main for convenience

        private static Stage primaryStage;
        private static BorderPane mainLayout;
        private static DoctorDBHandler dbHandler;

       @Override
        public void start(Stage primaryStage) throws IOException {
            this.dbHandler = new DoctorDBHandler();
            this.primaryStage=primaryStage;
            this.primaryStage.setTitle("Doctor Schedule app");


            showMainView();
            showMainItems();
        }

    private static void testConnection() {
       // String SQLquery = "SELECT * FROM doctors";
        String SQLquery="SELECT doctors.doctor_id, doctors.fname, doctors.lname, doctors.title, doctors.oncall, doctors.available, doctors.mainsection, sections.section_id, sections.section_name FROM doctors " +
                "INNER JOIN sections ON sections.section_id=doctors.mainsection";


        try {
            Connection conn = dbHandler.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(SQLquery);
            while (rs.next()){
                System.out.println(rs.getString("fname") + ", " + rs.getString("lname") + ", " + Boolean.toString(rs.getBoolean("available")) + " "+ rs.getString("section_name"));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
            launch(args);
        }

        private void showMainView() throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainView.fxml"));
            mainLayout = loader.load();
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void showMainItems() throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainItems.fxml"));
            BorderPane mainItems = loader.load();
            mainLayout.setCenter(mainItems);
        }

       public static void showDoctors() throws IOException{
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(Main.class.getResource("View/DoctorView/doctorView.fxml"));
           BorderPane doctorView = loader.load();
           mainLayout.setCenter(doctorView);
       }

    public static void showCompetencies() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/CompetencyView/competencyView.fxml"));
        BorderPane competencyView = loader.load();
        mainLayout.setCenter(competencyView);
    }


       public static void showAddDoctorWindow() throws IOException{
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(Main.class.getResource("View/DoctorView/addDoctor.fxml"));
           BorderPane AddNewEmployee = loader.load();

           Stage addDialogeStage = new Stage();
           addDialogeStage.setTitle("Add new Doctor");
           addDialogeStage.initModality(Modality.WINDOW_MODAL);
           addDialogeStage.setResizable(false);
           addDialogeStage.initOwner(primaryStage);
           Scene scene = new Scene(AddNewEmployee);
           addDialogeStage.setScene(scene);
           addDialogeStage.showAndWait();
       }

    public static void showAddCompetencyWindow(int person_id) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/DoctorView/EditDoctorCompetency.fxml"));
        BorderPane EditCompetencies = loader.load();

        Stage addDialogeStage = new Stage();
        addDialogeStage.setTitle("Edit Competencies");
        addDialogeStage.initModality(Modality.WINDOW_MODAL);
        addDialogeStage.setResizable(true);
        addDialogeStage.initOwner(primaryStage);
        Scene scene = new Scene(EditCompetencies);
        addDialogeStage.setScene(scene);
        EditDoctorCompetencyController editDoctorCompetencyController = loader.getController();
        editDoctorCompetencyController.setPersonId(person_id);
        editDoctorCompetencyController.loadData();
        addDialogeStage.showAndWait();

    }

    public static void showDepartments() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/DepartmentView/departmentView.fxml"));
        BorderPane departmentView = loader.load();
        mainLayout.setCenter(departmentView);
    }

    public static void showSections() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/SectionView/sectionView.fxml"));
        BorderPane sectionsView = loader.load();
        mainLayout.setCenter(sectionsView);
    }


}
