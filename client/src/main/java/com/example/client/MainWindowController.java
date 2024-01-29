package com.example.client;

import com.example.client.attendance.GetAttendanceWindow;
import com.example.client.attendance.PostAttendanceWindow;
import com.example.client.classDate.GetClassDateWindow;
import com.example.client.classDate.PostClassDateWindow;
import com.example.client.group.DeleteGroupWindow;
import com.example.client.group.GetGroupWindow;
import com.example.client.group.PostGroupWindow;
import com.example.client.student.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller class for the main window of the application.
 * Manages the layout and actions of the main window.
 */
public class MainWindowController {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code MainWindowController} class.
     * </p>
     */
    public MainWindowController() {
        // Initialize any necessary components or perform setup if needed.
    }
    /** The main stage of the application. */
    public static Stage mainStage;
    /** Property for binding the notepad text. */
    public static StringProperty notepadText = new SimpleStringProperty();
    /** Base URL for API requests. */
    public static String BASE_URL = "http://localhost:8080";
    /**
     * Creates the layout for the main window.
     *
     * @param stage The primary stage for the application.
     * @return The Scene object for the main window layout.
     */
    public static Scene createMainWindowLayout(Stage stage) {
        mainStage = stage;
        VBox layout = new VBox(10);

        Label mainLabel = new Label("Witaj w aplikacji do zarządzania obecnościami!");
        mainLabel.setStyle("-fx-font-size: 30px;");
        mainLabel.setWrapText(true);

        HBox groupButtons = new HBox(10);
        Label groupLabel = new Label("Zarządzaj grupami");
        groupLabel.setStyle("-fx-font-size: 20px;");

        Label notepadLabel = new Label("Notatnik");
        notepadLabel.setStyle("-fx-font-size: 20px;");
        TextArea notepad = new TextArea();
        notepad.setStyle("-fx-font-size: 15px;");
        notepad.setMaxWidth(400);
        notepad.textProperty().bindBidirectional(notepadText);

        Button getGroupButton = new Button("Pobierz grupę/y");
        getGroupButton.setStyle("-fx-font-size: 15px;");
        getGroupButton.setOnAction(e -> {
            mainStage.close();
            GetGroupWindow.createGetGroupLayout();
        });

        Button postGroupButton = new Button("Dodaj grupę");
        postGroupButton.setStyle("-fx-font-size: 15px;");
        postGroupButton.setOnAction(e -> {
            mainStage.close();
            PostGroupWindow.createPostGroupLayout();
        });

        Button deleteGroupButton = new Button("Usuń grupę");
        deleteGroupButton.setStyle("-fx-font-size: 15px;");
        deleteGroupButton.setOnAction(e -> {
            mainStage.close();
            DeleteGroupWindow.createDeleteGroupLayout();
        });

        groupButtons.getChildren().addAll(groupLabel, getGroupButton, postGroupButton, deleteGroupButton);
        groupButtons.setAlignment(Pos.CENTER);

        HBox studentButtons = new HBox(10);
        Label studentLabel = new Label("Zarządzaj studentami");
        studentLabel.setStyle("-fx-font-size: 20px;");

        Button getStudentButton = new Button("Pobierz studenta/ów");
        getStudentButton.setStyle("-fx-font-size: 15px;");
        getStudentButton.setOnAction(e -> {
            mainStage.close();
            GetStudentWindow.createGetStudentLayout();
        });

        Button postStudentButton = new Button("Dodaj studenta");
        postStudentButton.setStyle("-fx-font-size: 15px;");
        postStudentButton.setOnAction(e -> {
            mainStage.close();
            PostStudentWindow.createPostStudentLayout();
        });

        Button deleteStudentButton = new Button("Usuń studenta");
        deleteStudentButton.setStyle("-fx-font-size: 15px;");
        deleteStudentButton.setOnAction(e -> {
            mainStage.close();
            DeleteStudentWindow.createDeleteStudentLayout();
        });

        Button addStudentToGroupButton = new Button("Dodaj studenta do grupy");
        addStudentToGroupButton.setStyle("-fx-font-size: 15px;");
        addStudentToGroupButton.setOnAction(e -> {
            mainStage.close();
            AddStudentToGroupWindow.createAddStudentToGroupLayout();
        });

        Button deleteStudentFromGroupButton = new Button("Usuń studenta z grupy");
        deleteStudentFromGroupButton.setStyle("-fx-font-size: 15px;");
        deleteStudentFromGroupButton.setOnAction(e -> {
            mainStage.close();
            DeleteStudentFromGroupWindow.createDeleteStudentFromGroupLayout();
        });

        studentButtons.getChildren().addAll(studentLabel, getStudentButton, postStudentButton, deleteStudentButton,
                addStudentToGroupButton, deleteStudentFromGroupButton);
        studentButtons.setAlignment(Pos.CENTER);

        HBox classDateButtons = new HBox(10);
        Label classDateLabel = new Label("Zarządzaj zajęciami");
        classDateLabel.setStyle("-fx-font-size: 20px;");

        Button getClassDateButton = new Button("Pobierz zajęcia");
        getClassDateButton.setStyle("-fx-font-size: 15px;");
        getClassDateButton.setOnAction(e -> {
            mainStage.close();
            GetClassDateWindow.createGetClassDateLayout();
        });

        Button postClassDateButton = new Button("Dodaj zajęcia");
        postClassDateButton.setStyle("-fx-font-size: 15px;");
        postClassDateButton.setOnAction(e -> {
            mainStage.close();
            PostClassDateWindow.createPostClassDateLayout();
        });

        classDateButtons.getChildren().addAll(classDateLabel, getClassDateButton, postClassDateButton);
        classDateButtons.setAlignment(Pos.CENTER);

        HBox attendanceButtons = new HBox(10);
        Label attendanceLabel = new Label("Zarządzaj obecnościami");
        attendanceLabel.setStyle("-fx-font-size: 20px;");

        Button getAttendanceButton = new Button("Pobierz obecności");
        getAttendanceButton.setStyle("-fx-font-size: 15px;");
        getAttendanceButton.setOnAction(e -> {
            mainStage.close();
            GetAttendanceWindow.createGetAttendanceLayout();
        });

        Button postAttendanceButton = new Button("Dodaj obecności");
        postAttendanceButton.setStyle("-fx-font-size: 15px;");
        postAttendanceButton.setOnAction(e -> {
            mainStage.close();
            PostAttendanceWindow.createPostAttendanceLayout();
        });

        attendanceButtons.getChildren().addAll(attendanceLabel, getAttendanceButton, postAttendanceButton);
        attendanceButtons.setAlignment(Pos.CENTER);

        // Add the button to the StackPane
        layout.getChildren().addAll(mainLabel, groupButtons, studentButtons, classDateButtons, attendanceButtons, notepadLabel, notepad);

        // Center the button in the StackPane
        layout.setAlignment(Pos.CENTER);

        // Create a scene
        return new Scene(layout, 1000, 900);
    }
}