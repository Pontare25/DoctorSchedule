package Schedule.competency;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.security.PublicKey;

public class Competency {
     private SimpleIntegerProperty compID;
     private SimpleStringProperty compTitle;
     private SimpleStringProperty compDesc;

    //Lista med subkompetenser


     public Competency(int compID, String compTitle, String compDesc) {
          this.compID = new SimpleIntegerProperty(compID);
          this.compTitle = new SimpleStringProperty(compTitle);
          this.compDesc = new SimpleStringProperty(compDesc);
     }

     public int getCompID() {
          return compID.get();
     }

     public SimpleIntegerProperty compIDProperty() {
          return compID;
     }

     public String getCompTitle() {
          return compTitle.get();
     }

     public SimpleStringProperty compTitleProperty() {
          return compTitle;
     }

     public String getCompDesc() {
          return compDesc.get();
     }

     public SimpleStringProperty compDescProperty() {
          return compDesc;
     }

     @Override
     public String toString(){
          return compTitle.get();
     }
}
