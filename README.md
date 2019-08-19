# DoctorSchedule
Using javafx 12 to create a scheduling program for different sections and departments for an educational hospital

The JavaFx part was done following this example
https://www.youtube.com/watch?v=BHj6zbH3inI

VM Options
Using IntelliJ, if you have add VM options, this can be done by pasting in the following
Run- Edit Configurations - VM Options: --module-path /path/to/javafx-sdk-12.0.2/lib --add-modules javafx.controls,javafx.fxml
For me this looks like
--module-path "/Users/PontusN/Documents/Java downloaded libraries/JavaFX/javafx-sdk-12.0.1/lib" --add-modules javafx.controls,javafx.fxml 

SQLITE
OBS! if you cannot connect tot the database you may have to change the directory since it is an SQLite file and the filepath will be different
jdbc:sqlite: /file_path/DoctorScheduleDB.db
