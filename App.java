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
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
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
    VBox introBox;
    TextField tasksTextBox;
    Text fileText;
    ArrayList<String> tasks = new ArrayList<String>();
    Text numTasksText;
    ToggleButton priorityBtn;
    Label toDoItem;
    Label titleLabel;
    TextField titleTextField;
    Label titeEnterLabel;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        // Create components to add.
        VBox presetBox = new VBox();
        presetBox.setStyle("-fx-border-color: black; -fx-border-width: 2");
        presetBox.setAlignment(Pos.TOP_CENTER);
        
        introBox = new VBox();
        introBox.setAlignment(Pos.TOP_CENTER);

        // has the content of the lists
        contentBox = new VBox();
        buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);

        Label welcome = new Label();
        welcome.setText("Welcome to the To-Do List!");
        welcome.setStyle("-fx-font-size: 18");
        Label label = new Label();
        label.setText("Please enter your tasks: ");

        tasksTextBox = new TextField();
        tasksTextBox.setPromptText("type here");
        tasksTextBox.setMaxWidth(250);
        Button clearTasksBtn = new Button("Clear Tasks");
        Button fileBtn = new Button("Import list from file");
        Button saveBtn = new Button("Save list to file");
        numTasksText = new Text(tasks.size() + " task(s) remaining");
        priorityBtn = new ToggleButton("High Priority");
        titeEnterLabel = new Label("Enter list name: ");
        titleTextField = new TextField();
        titleTextField.setPromptText("type here");
        titleTextField.setMaxWidth(200);
        titleLabel = new Label("Title Goes Here.");
        titleLabel.setStyle("-fx-font-weight: bold");
        Button newListBtn = new Button("Add new list");

        // organize compents
        introBox.getChildren().addAll(welcome, titeEnterLabel, titleTextField, titleLabel, label, tasksTextBox, priorityBtn);
        buttonBox.getChildren().addAll(clearTasksBtn, fileBtn, saveBtn);
        contentBox.getChildren().addAll();
        presetBox.getChildren().addAll(introBox, contentBox, buttonBox, newListBtn, numTasksText);

        // Reactions 
        tasksTextBox.setOnAction(event -> addTask());
        clearTasksBtn.setOnAction(event -> clear());
        fileBtn.setOnAction(event -> importFile());
        saveBtn.setOnAction(event -> saveFile());
        titleTextField.setOnAction(event -> listTitle());
        newListBtn.setOnAction(event -> newList());

        // set scene and display stage
        Scene scene = new Scene(presetBox, 400, 300);
        stage.setScene(scene);
        stage.show();

    }
    // Method for adding tasks
    // adding/assembling tasks 
    void addTask() {
        String input = tasksTextBox.getText();
        tasksTextBox.clear();
        String taskText;
        // If priority add *
        if (priorityBtn.isSelected()) {
            taskText = input + " *";
        } else {
            taskText = input;
        }
        addTask(taskText);
    }
    void addTask(String task) {
        // making to do lay out
        CheckBox checkBox = new CheckBox();
        HBox toDo = new HBox();
        
        toDoItem = new Label(" " + task);
        toDoItem.setStyle("-fx-font-size: 13");
        checkBox.setMaxSize(12, 13);

        // When checkbox is clicked
        checkBox.setOnAction(checkboxEvent -> {
            toDo.setDisable(true);
            // remove from array list
            tasks.remove(task);
            numTasksText.setText(tasks.size() + " task(s) remaining");
        });
        toDo.getChildren().addAll(checkBox, toDoItem);
        contentBox.getChildren().add(toDo);
        tasks.add(task);
        // updating number of tasks
        numTasksText.setText(tasks.size() + " task(s) remaining");
    }

    // clearing all tasks
    void clear() {
        contentBox.getChildren().clear();
        // clears arraylist
        tasks.clear();
        numTasksText.setText(tasks.size() + " task(s) remaining");
    }
    
    /**
     * Importing tasks from file ("list.txt")
     */
    void importFile() {
        // choose file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        File file = fileChooser.showOpenDialog(null);  
        // if no file is selected      
        if (file == null) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            // 'repeat' until scanner doesn't have a next line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    // Add to numTasks so it gets saved to file as well
                    addTask(line);
                }
            }
        } catch (FileNotFoundException e) {
            return;
        }
    }
    // saving tasks to text file
    void saveFile() {
        // choose file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Task List");

        // if there is a title, change file name to that
        String title = titleLabel.getText();
        if (!title.equals("Title Goes Here.")){
            fileChooser.setInitialFileName(title + ".txt");
        } else {
            // set the list.txt file as default
            fileChooser.setInitialFileName("list.txt");
        }
       
        File file = fileChooser.showSaveDialog(null);
        // if no file is selected
        if (file == null) {
            return;
        }
        try {
            PrintWriter fileWriter = new PrintWriter(new FileWriter(file, false));
            // repeat for the number of tasks added
            for (int i = 0; i < tasks.size(); i++) {
                fileWriter.println(tasks.get(i));
            }
            fileWriter.close();
        } catch (Exception e) {
            return;
        }
    }
    // adding a title
    void listTitle() {
        String title = titleTextField.getText();
        introBox.getChildren().removeAll(titeEnterLabel, titleTextField);
        titleLabel.setText(title);
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold");
    }
    void newList() {
        // clear current tasks and array list
        clear();
        // reset title field so user enter new list name
        titleTextField.clear();
        titleLabel.setText("Title Goes Here.");
        titleLabel.setStyle("-fx-font-weight: bold");

        // readd the textfield and label to allow user to readd title name
        if (!introBox.getChildren().contains(titeEnterLabel)) {
            introBox.getChildren().add(1, titeEnterLabel);
            introBox.getChildren().add(2, titleTextField);
        }
    }
}