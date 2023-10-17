
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    public static String username;
    
    public static void logout(Event event) {
        Parent root = (Parent) event.getSource();
        Stage currentStage = (Stage) root.getScene().getWindow();
        
        String db = "jdbc:derby:C:\\test";

        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(db, "", "");

            // Create a SQL statement
            Statement statement = connection.createStatement();

            // Execute an SQL update statement to set all "is_logged_in" to false
            String sqlUpdate = "UPDATE Users SET is_logged_in = false";
            int rowsUpdated = statement.executeUpdate(sqlUpdate);

            // Close the resources
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        currentStage.close();
        
    }
}
