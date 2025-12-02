import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.text.Text;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Template JavaFX application.
 */
public class App extends Application{
    VBox contentBox;
    HBox buttonBox;
    TextField addTasks;
    Text fileText;
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
        contentBox = new VBox();
        buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        

        Label welcome = new Label();
        welcome.setText("Welcome to the To-Do List!");
        welcome.setStyle("-fx-font-size: 20");
        Label label = new Label();
        label.setText("Please enter your tasks: ");

        addTasks = new TextField();
        addTasks.setPromptText("type here");
        addTasks.setMaxWidth(250);
        Button addTasksBtn = new Button("Add");
        Button clearTasksBtn = new Button("Clear Tasks");
        Button fileBtn = new Button("Import list from file");
        Button saveBtn = new Button("Save list to file");


        // organize compents
        introBox.getChildren().addAll(welcome, label, addTasks, addTasksBtn);
        buttonBox.getChildren().addAll(clearTasksBtn, fileBtn, saveBtn);
        contentBox.getChildren().addAll();
        presetTunesBox.getChildren().addAll(introBox, contentBox, buttonBox);

        // Reactions 
        addTasksBtn.setOnAction(event -> task());
        clearTasksBtn.setOnAction(event -> clear());
        fileBtn.setOnAction(event -> importFile());
        saveBtn.setOnAction(event -> saveFile());

        // set scene and display stage
        Scene scene = new Scene(presetTunesBox, 400, 300);
        stage.setScene(scene);
        stage.show();

    }
    // Method for adding tasks
    void task() {
        String text = addTasks.getText();
        addTasks.clear();
        CheckBox checkBox = new CheckBox();
        HBox toDo = new HBox();
        Label toDoItem = new Label(" " + text);
        toDoItem.setStyle("-fx-font-size: 13");
        checkBox.setMaxSize(12, 13);

        checkBox.setOnAction(checkboxEvent -> {
            toDo.setDisable(true);
        });
        toDo.getChildren().addAll(checkBox, toDoItem);
        contentBox.getChildren().add(toDo);
    }
    void clear() {
        contentBox.getChildren().clear();
    }
    void importFile() {
        File file = new File("list.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {

                    CheckBox checkBox = new CheckBox();
                    HBox toDo = new HBox();
                    Label toDoItem = new Label(" " + line);
                    toDoItem.setStyle("-fx-font-size: 13");
                    checkBox.setMaxSize(12, 13);

                    checkBox.setOnAction(checkboxEvent -> {
                        toDo.setDisable(true);
                    });

                    toDo.getChildren().addAll(checkBox, toDoItem);
                    contentBox.getChildren().add(toDo);
                }
            }
        } catch (FileNotFoundException e) {
            return;
        }
    }
    void saveFile() {
        File file = new File("list.txt");
        // a fileWriter is almost the same as a printWriter but instead of replacing the file it adds to it
        try (FileWriter writer = new FileWriter(file, true)) {
            for (javafx.scene.Node node : contentBox.getChildren()) {
                if (node instanceof HBox toDo) {
                    Label label = (Label) toDo.getChildren().get(1);
                    writer.write(label.getText().trim() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            return;
        }
    }
}