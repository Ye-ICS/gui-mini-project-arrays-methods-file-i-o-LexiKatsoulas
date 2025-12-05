import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.text.Text;
import java.io.PrintWriter;
import java.io.FileWriter;
/**
 * Template JavaFX application.
 */
public class App extends Application{
    VBox contentBox;
    HBox buttonBox;
    TextField tasksTextBox;
    Text fileText;
    ArrayList<String> numTasks = new ArrayList<>();
    Text numTasksText;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        // Create components to add.
        VBox presetBox = new VBox();
        presetBox.setStyle("-fx-border-color: black; -fx-border-width: 2");
        presetBox.setAlignment(Pos.TOP_CENTER);
        
        VBox introBox = new VBox();
        introBox.setAlignment(Pos.TOP_CENTER);

        // has the content of the lists
        contentBox = new VBox();
        buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        

        Label welcome = new Label();
        welcome.setText("Welcome to the To-Do List!");
        welcome.setStyle("-fx-font-size: 20");
        Label label = new Label();
        label.setText("Please enter your tasks: ");

        tasksTextBox = new TextField();
        tasksTextBox.setPromptText("type here");
        tasksTextBox.setMaxWidth(250);
        Button addTasksBtn = new Button("Add");
        Button clearTasksBtn = new Button("Clear Tasks");
        Button fileBtn = new Button("Import list from file");
        Button saveBtn = new Button("Save list to file");
        numTasksText = new Text();


        // organize compents
        introBox.getChildren().addAll(welcome, label, tasksTextBox, addTasksBtn);
        buttonBox.getChildren().addAll(clearTasksBtn, fileBtn, saveBtn);
        contentBox.getChildren().addAll();
        presetBox.getChildren().addAll(introBox, contentBox, buttonBox, numTasksText);

        // Reactions 
        addTasksBtn.setOnAction(event -> addTask());
        tasksTextBox.setOnAction(event -> addTask());
        clearTasksBtn.setOnAction(event -> clear());
        fileBtn.setOnAction(event -> importFile());
        saveBtn.setOnAction(event -> saveFile());

        // set scene and display stage
        Scene scene = new Scene(presetBox, 400, 300);
        stage.setScene(scene);
        stage.show();

    }
    // Method for adding tasks
    void addTask() {
        String text = tasksTextBox.getText();
        tasksTextBox.clear();
        numTasks.add(text);
        // updating number of tasks
        numTasksText.setText(numTasks.size() + " task(s) left");
        // making to do lay out
        CheckBox checkBox = new CheckBox();
        HBox toDo = new HBox();
        Label toDoItem = new Label(" " + text);
        toDoItem.setStyle("-fx-font-size: 13");
        checkBox.setMaxSize(12, 13);

        // When checkbox is clicked
        checkBox.setOnAction(checkboxEvent -> {
            toDo.setDisable(true);
            // remove from array list
            numTasks.remove(text);
            numTasksText.setText(numTasks.size() + " task(s) left");
        });
        toDo.getChildren().addAll(checkBox, toDoItem);
        contentBox.getChildren().add(toDo);
    }
    void clear() {
        contentBox.getChildren().clear();
        // clears arraylist
        numTasks.clear();
        numTasksText.setText(numTasks.size() + " task(s) left");
    }
    void importFile() {
        File file = new File("list.txt");
        try (Scanner scanner = new Scanner(file)) {
            // 'repeat' until scanner doesn't have a next line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    // Add to numTasks so it gets saved to file as well
                    numTasks.add(line);

                    // same code for adding a task lay out
                    CheckBox checkBox = new CheckBox();
                    HBox toDo = new HBox();
                    Label toDoItem = new Label(" " + line);
                    toDoItem.setStyle("-fx-font-size: 13");
                    checkBox.setMaxSize(12, 13);

                    checkBox.setOnAction(checkboxEvent -> {
                        toDo.setDisable(true);
                        numTasks.remove(line);
                        numTasksText.setText(numTasks.size() + " task(s) left");
                    });

                    toDo.getChildren().addAll(checkBox, toDoItem);
                    contentBox.getChildren().add(toDo);
                }
            }
        // update number of tasks after import
        numTasksText.setText(numTasks.size() + " task(s) left");
        } catch (FileNotFoundException e) {
            return;
        }
    }
    void saveFile() {
        try {
            PrintWriter fileWriter = new PrintWriter(new FileWriter("list.txt", false));
            // repeat for the number of tasks added
            for (int i = 0; i < numTasks.size(); i++) {
                fileWriter.println(numTasks.get(i));
            }

            fileWriter.close();
        } catch (Exception e) {
            return;
        }
    
    }
}