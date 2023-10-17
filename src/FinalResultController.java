import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class FinalResultController {
    
    private Stage stage;
    private Scene scene;
    
    public void GoToResultPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("FinalResultWindow.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
       
        Label Result = (Label) root.lookup("#FinalResult");
        
        Result.setText(Integer.toString(questionController.TotalOfCorrectAnswers));
    }
}
