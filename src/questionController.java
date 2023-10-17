import java.io.FileReader;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.event.Event;

public class questionController extends FXMLController{
    
    public static Parent root;
    private Stage stage;
    private Scene scene;
   
    private static int questionCounter = 0;
    private static String CorrectAnswer = ""; 
    public  static int TotalOfCorrectAnswers = 0;
    private static boolean DidUserClick = false;
    static List<Integer> generatedRandomNum = new ArrayList<>();

   
    
    
    public void questionWindow(ActionEvent event) throws IOException, ParseException 
    {

        
        
        
        
            String questionTextDB = "";
            String choice1DB = "";
            String choice2DB = "";
            String choice3DB = "";
            String correctChoiceDB = "";

            try {
                String db = "jdbc:derby:C:\\test";
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                Connection con = DriverManager.getConnection(db, "", "");
                Statement stmt = con.createStatement();

                // Fetch the next question 
                ResultSet questionResult = stmt.executeQuery("SELECT * FROM APP." + Qategory +"_QUESTIONS OFFSET " + (getRandomQuestionNumber()) + " ROWS FETCH NEXT 1 ROW ONLY");

                if (questionResult.next()) {
                    int questionId = questionResult.getInt("question_id"); 
                    questionTextDB = questionResult.getString("question_text");


                    // Fetch the associated choices
                    ResultSet choicesResult = stmt.executeQuery("SELECT * FROM APP." + Qategory + "_CHOICES WHERE question_id = " + questionId);

                    int choiceNumber = 1;
                    while (choicesResult.next()) {
                        String choiceText = choicesResult.getString("choice_text");
                        if (choiceNumber == 1) {
                            choice1DB = choiceText;
                        } else if (choiceNumber == 2) {
                            choice2DB = choiceText;
                        } else if (choiceNumber == 3) {
                            choice3DB = choiceText;
                        }

                        // Check if it's the correct choice
                        if (choicesResult.getBoolean("is_correct")) {
                            correctChoiceDB = choiceText;
                        }
                        choiceNumber++;
                    }

                    // Increment the counter for the next question
                }

                // Close the resources
                questionResult.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }



        
        
        
        goToQuestionPage(event, questionTextDB, choice1DB, choice2DB, choice3DB, correctChoiceDB);
        
  
            
    }
    public void goToQuestionPage(ActionEvent event, String questionTextDB, String choice1DB, String choice2DB, String choice3DB, String correctChoiceDB) throws IOException
    {
        
        root = FXMLLoader.load(getClass().getResource("questionWindow.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        // Set usernaem 
        Label usernameLabel = (Label) root.lookup("#usernameLabel");
        usernameLabel.setText(User.username);

       Label questionText = (Label) root.lookup("#questionText");
       questionText.setText(questionTextDB);
       
       Button buttonText1 = (Button) root.lookup("#choice1");
       buttonText1.setText(choice1DB);
       
       Button buttonText2 = (Button) root.lookup("#choice2");
       buttonText2.setText(choice2DB);
       
       Button buttonText3 = (Button) root.lookup("#choice3");
       buttonText3.setText(choice3DB);
       
       CorrectAnswer = correctChoiceDB ;

       
 
    }
    
    public void CheckCorrectAnswer(ActionEvent event) throws IOException
    {
        Button choice = (Button) event.getSource();
        String choiceValue = choice.getText();
       
        if(DidUserClick == false) //this is to make the user just clieck one button
        {
            DidUserClick = true;
            if(choiceValue.equals(CorrectAnswer))
                
                TotalOfCorrectAnswers ++;
            
            else
                
              choice.setStyle("-fx-background-color: #DF2E38;-fx-font-size: 16px");
            
            
            ColorCorrectAnswer(); //even if the answer is false it will mark the correct answer to show it to the user
        }
        
    }
    public void ColorCorrectAnswer() throws IOException
    {
        
        Button button1 = (Button) root.lookup("#choice1");
        Button button2 = (Button) root.lookup("#choice2");
        Button button3 = (Button) root.lookup("#choice3");
        Button Correct = new Button();
        
        if(CorrectAnswer.equals(button1.getText()))
            Correct = button1;
        
        else if(CorrectAnswer.equals(button2.getText()))
            Correct = button2;
        
        else
            Correct = button3;
        
        Correct.setStyle("-fx-background-color: #A8DF8E;-fx-font-size: 16px");
    }
    
    public void goToNextQuestion(ActionEvent event) throws IOException, ParseException
    {
        if(questionCounter < FXMLController.NumberOfQuestions - 1)
        {
            if(DidUserClick)
            {
                questionCounter++;
                questionWindow(event);
            }
            DidUserClick = false;
            
        }
        else
        {
            FinalResultController FinalResult = new FinalResultController();
            FinalResult.GoToResultPage(event);
        }
        
    }
    
    
    public int getRandomQuestionNumber() {
        Random rand = new Random();
        
        // Generate a random integer between 0 (inclusive) and 100 (exclusive)
        
        int randomNum = 0;
        
        while (true) {
            randomNum = rand.nextInt(30);
            if (!generatedRandomNum.contains(randomNum)) {
                generatedRandomNum.add(randomNum);
                break;
            }
        }
       
        
        return randomNum;  
    }
    
    public void logout(Event event) {
       User.logout(event);
   }
}
