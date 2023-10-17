
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserLoginPanelController {
    @FXML private TextField loginUsernameField;
    @FXML private TextField loginPasswordField;
    @FXML private Label warning;;
    
    
    public void login() throws IOException{ 
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            warning.setText("Please fill in both the username and password fields.");
        } else {
            warning.setText("");
            
            String db = "jdbc:derby:C:\\test";
            
            try (Connection connection = DriverManager.getConnection(db, "", "");
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
                PreparedStatement updateStatement = connection.prepareStatement("UPDATE Users SET is_logged_in = true WHERE username = ? AND password = ?")){
                statement.setString(1, username);
                statement.setString(2, password);
//
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    
                    User.username = username;
                    updateStatement.setString(1, username);
                    updateStatement.setString(2, password);
                    updateStatement.executeUpdate();

                    // Close the window and start the program
                    Stage currentStage = (Stage) loginUsernameField.getScene().getWindow();
                    
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("KindOfQuestions.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    
                    currentStage.close();
//        
            } else {
                // User does not exist
                 warning.setText("Login Failed. Invalid username or password.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
            
        }
    }
    
    public void goToRegistrationPanel() throws IOException {
        Stage currentStage = (Stage) loginUsernameField.getScene().getWindow();
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("UserRegistrationPanel.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        currentStage.close();
    }
}