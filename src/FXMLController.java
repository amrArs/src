import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.json.simple.parser.ParseException;

public class FXMLController {
    
    public static String Qategory; 
    public static int NumberOfQuestions;
    boolean didUserChooseNumber = false;
    boolean chooseOnce = true;
    @FXML Label usernameLabel;
    
    public void initialize() {
        // set the username when the program starts
        usernameLabel.setText(User.username);
    }
    
    public void ChooseNumberOfQuestoin(ActionEvent event) throws IOException, ParseException
    {
        if(chooseOnce)
        {
            Button choise = (Button) event.getSource();
            choise.setStyle("-fx-background-color: #FFFFFF;-fx-font-size: 16px");
        
            NumberOfQuestions = Integer.parseInt(choise.getText());
            didUserChooseNumber = true;
            chooseOnce = false;
        }
        
    }
    public void ChooseKindOfQuestions(ActionEvent event) throws IOException, ParseException, SQLException
    {
        if(didUserChooseNumber)
        {
            Button QategoryButton = (Button) event.getSource();
            Qategory = QategoryButton.getText();
        
            questionController q = new questionController();
            q.questionWindow(event);
        }
       
    }
   public static String GetQategory()
    {
        return Qategory;
    }
   
   public void logout(Event event) {
       User.logout(event);
   }
}
