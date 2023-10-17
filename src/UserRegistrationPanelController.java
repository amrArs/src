
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;



public class UserRegistrationPanelController {
    @FXML private TextField regUsernameField;
    @FXML private TextField regPasswordField;
    @FXML private Label warning;
//    
    
    public void registration() throws IOException{ 
        String username = regUsernameField.getText();
        String password = regPasswordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            warning.setText("Please fill in both the username and password fields.");
        } else {
            warning.setText("");
            String db = "jdbc:derby:C:\\test";
            try (Connection connection = DriverManager.getConnection(db, "", "");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password, is_logged_in) VALUES (?, ?, true)")) {
                
                statement.setString(1, username);
                statement.setString(2, password);
                statement.executeUpdate();

                User.username = username;
                
                // Close the window and starr the program
                Stage currentStage = (Stage) regUsernameField.getScene().getWindow();
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("KindOfQuestions.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                currentStage.close();



        } catch (SQLException e) {
           System.out.println(e);
            if (e.getSQLState().equals("23505")) { // error if the username is already exist (because username is UNIUQE).
                warning.setText("The username is already in use. Please choose a different one or login.");
                } 
            }
            
        }
    }
    
    public void goToLoginPanel() throws IOException {
        Stage currentStage = (Stage) regUsernameField.getScene().getWindow();
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("UserLoginPanel.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        currentStage.close();
    }
}