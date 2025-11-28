import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Template JavaFX application.
 */
public class App extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        // Create components to add.
        VBox presetTunesBox = new VBox();
        presetTunesBox.setStyle("-fx-border-color: black; -fx-border-width: 2");
        
        VBox introBox = new VBox();
        introBox.setAlignment(Pos.TOP_CENTER);

        // has the content of the lists
        VBox contentBox = new VBox();

        Label welcome = new Label();
        welcome.setText("Welcome to the To-Do List!");
        welcome.setStyle("-fx-font-size: 20");
        Label label = new Label();
        label.setText("Please enter your tasks: ");

        TextField addTasks = new TextField();
        addTasks.setPromptText("type here");
        addTasks.setMaxWidth(250);
        Button addTasksBtn = new Button("Add");



        // organize compents
        introBox.getChildren().addAll(welcome, label, addTasks, addTasksBtn);
        contentBox.getChildren().addAll();
        presetTunesBox.getChildren().addAll(introBox, contentBox);


        // Reactions 
        addTasksBtn.setOnAction(event -> {
            String text = addTasks.getText();
            addTasks.clear();
            HBox toDo = new HBox();
            Label toDoItem = new Label(" " + text);
            toDoItem.setStyle("-fx-font-size: 13");
            CheckBox checkBox = new CheckBox();
            checkBox.setMaxSize(12, 13);
            toDo.getChildren().addAll(checkBox, toDoItem);
            contentBox.getChildren().add(toDo);
        });



        // set scene and display stage
        Scene scene = new Scene(presetTunesBox, 400, 300);
        stage.setScene(scene);
        stage.show();





    //     VBox contentBox = new VBox();
    //     contentBox.setAlignment(Pos.CENTER);

    //     Label promptLabel = new Label();
    //     promptLabel.setText("TO-DO LIST");

    //     TextField thoughtsBox = new TextField();
    //     thoughtsBox.setMaxWidth(150);
    //     thoughtsBox.setPromptText("type here");
        
    //     messageBox = new VBox();

    //     Button submissionBtn = new Button();
    //     submissionBtn.setText("Submit");

    //     // Set up reactions (aka callbacks).
    //     // submissionBtn.setOnAction(event -> onSubmitThought(thoughtsBox, messageBox));

    //     // Add components to the content box.
    //     contentBox.getChildren().add(promptLabel);
    //     contentBox.getChildren().add(thoughtsBox);
    //     contentBox.getChildren().add(submissionBtn);
    //     contentBox.getChildren().add(messageBox);

    //     // Set up the window and display it.
    //     Scene scene = new Scene(contentBox, 300, 200);
    //     stage.setScene(scene);
    //     stage.setTitle("To-Do List App");
    //     stage.show();
    // }

    // /**
    //  * Handle the submission of a thought.
    //  * @param inputBox  The TextField where the user types their thought.
    //  * @param outputBox The TextArea where the submitted thoughts are displayed.
    //  */
    // void onSubmitThought(TextField inputBox, VBox outputBox) {
    //     String text = inputBox.getText();
    //     inputBox.clear();
    //     outputBox.appendText( text + "\n");
    //     Button checkBtn = new Button("O");
    //     messageBox.getChildren().add(checkBtn);
    }
}
