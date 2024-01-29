package com.example.client.student;

import com.example.client.Group;
import com.example.client.LocalDateTimeAdapter;
import com.example.client.MainWindowController;
import com.example.client.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Window for adding a student to a group in the system.
 */
public class AddStudentToGroupWindow {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code AddStudentToGroupWindow} class.
     * </p>
     */
    public AddStudentToGroupWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Creates the layout for the Add Student to Group window.
     */
    public static void createAddStudentToGroupLayout() {
        Stage newStage = new Stage();

        VBox layout = new VBox(10);

        Label notepadLabel = new Label("Notatnik");
        notepadLabel.setStyle("-fx-font-size: 20px;");
        TextArea notepad = new TextArea();
        notepad.setStyle("-fx-font-size: 15px;");
        notepad.setMaxWidth(400);
        notepad.textProperty().bindBidirectional(MainWindowController.notepadText);

        TextField textField1 = new TextField();
        TextField textField2 = new TextField();

        textField1.setMaxWidth(200);
        textField2.setMaxWidth(200);

        Label label1 = new Label("Podaj id studenta (wymagane)");
        label1.setStyle("-fx-font-size: 15px;");
        Label label2 = new Label("Podaj id grupy (wymagane)");
        label2.setStyle("-fx-font-size: 15px;");

        Button goBackButton = new Button("Powrót do strony głównej");
        goBackButton.setStyle("-fx-font-size: 15px;");
        goBackButton.setOnAction(e -> {
            newStage.close();
            MainWindowController.mainStage.show();
        });

        Button fetchButton = new Button("Dodaj studenta do grupy");
        fetchButton.setStyle("-fx-font-size: 15px;");
        fetchButton.setOnAction(e -> {
            try {
                String studentId = textField1.getText();
                String groupId = textField2.getText();

                studentId = studentId.strip();
                groupId = groupId.strip();

                if (studentId.length() != 6) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodania studenta do grupy");
                    alert.setContentText("Id studenta musi mieć długość 6 znaków");
                    alert.showAndWait();
                    return;
                } else if(groupId.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodania studenta do grupy");
                    alert.setContentText("Id grupy nie może być puste");
                    alert.showAndWait();
                    return;
                }

                addStudentToGroup(studentId, groupId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sukces");
                alert.setHeaderText("Dodano studenta do grupy");
                alert.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd podczas dodania studenta do grupy");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        HBox hBox = new HBox(10);

        hBox.getChildren().addAll(fetchButton, goBackButton);
        hBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(label1, textField1, label2, textField2, hBox, notepadLabel, notepad);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene newScene = new Scene(layout, 1000, 900);

        newStage.setScene(newScene);

        newStage.setTitle("Dodaj studenta do grupy");
        newStage.show();

        newStage.setOnCloseRequest(e -> {
            e.consume();
            newStage.close();
            MainWindowController.mainStage.show();
        });
    }
    /**
     * Adds a student to a group in the system based on the provided information.
     *
     * @param studentId The ID of the student to be added to the group.
     * @param groupId   The ID of the group to which the student will be added.
     */
    private static void addStudentToGroup(String studentId, String groupId) {
        try {
            HttpResponse<String> apiResponse = Unirest.put(MainWindowController.BASE_URL + "/api/students/addToGroup")
                    .queryString("studentId", studentId).queryString("groupId", groupId).asString();
            if (apiResponse.getStatus() != 200) {
                throw new IOException(apiResponse.getBody());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
