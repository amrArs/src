import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainApp extends Application {
    public static void main(String[] args) {
        
        launch(args);
       
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        String windowPath = "UserRegistrationPanel.fxml";
            
        // Define the database connection parameters
        String db = "jdbc:derby:C:\\test";
        String username = "";
        String password = "";

        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(db, username, password);

            // Create a SQL statement
            Statement statement = connection.createStatement();

            // Execute a query to retrieve data from the "is_logged_in" column
            String sqlQuery = "SELECT is_logged_in, username FROM Users";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Loop through the results and check if the "is_logged_in" column is true or false
            while (resultSet.next()) {
                boolean isLoggedInDB = resultSet.getBoolean("is_logged_in");
                String usernameDB = resultSet.getString("username");
                
                if (isLoggedInDB) {
                    windowPath = "KindOfQuestions.fxml";
                    User.username = usernameDB;
                    
                    

                } 
                
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        
        
        
        
        
        Parent root = FXMLLoader.load(getClass().getResource(windowPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}