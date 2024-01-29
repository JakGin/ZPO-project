package com.example.client.student;

import com.example.client.Group;
import com.example.client.LocalDateTimeAdapter;
import com.example.client.MainWindowController;
import com.example.client.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Window for adding a new student to the system.
 */
public class PostStudentWindow {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code PostStudentWindow} class.
     * </p>
     */
    public PostStudentWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Creates the layout for the Add Student window.
     */
    public static void createPostStudentLayout() {
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
        TextField textField3 = new TextField();
        TextField textField4 = new TextField();

        textField1.setMaxWidth(200);
        textField2.setMaxWidth(200);
        textField3.setMaxWidth(200);
        textField4.setMaxWidth(200);

        Label label1 = new Label("Podaj id studenta (wymagane)");
        label1.setStyle("-fx-font-size: 15px;");
        Label label2 = new Label("Podaj imię studenta (wymagane)");
        label2.setStyle("-fx-font-size: 15px;");
        Label label3 = new Label("Podaj nazwisko studenta (wymagane)");
        label3.setStyle("-fx-font-size: 15px;");
        Label label4 = new Label("Podaj id grupy studenta");
        label4.setStyle("-fx-font-size: 15px;");

        Button goBackButton = new Button("Powrót do strony głównej");
        goBackButton.setStyle("-fx-font-size: 15px;");
        goBackButton.setOnAction(e -> {
            newStage.close();
            MainWindowController.mainStage.show();
        });

        Button fetchButton = new Button("Dodaj studenta");
        fetchButton.setStyle("-fx-font-size: 15px;");
        fetchButton.setOnAction(e -> {
            try {
                String id = textField1.getText();
                String name = textField2.getText();
                String surname = textField3.getText();
                String groupId = textField4.getText();

                id = id.strip();
                name = name.strip();
                surname = surname.strip();
                groupId = groupId.strip();

                if (id.length() != 6) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania studenta");
                    alert.setContentText("Id studenta musi mieć długość 6 znaków");
                    alert.showAndWait();
                    return;
                } else if(name.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania studenta");
                    alert.setContentText("Imię studenta nie może być puste");
                    alert.showAndWait();
                    return;
                } else if(surname.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania studenta");
                    alert.setContentText("Nazwisko studenta nie może być puste");
                    alert.showAndWait();
                    return;
                } else if(groupId.isEmpty()) {
                    groupId = null;
                }

                postStudent(id, name, surname, groupId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sukces");
                alert.setHeaderText("Dodano studenta");
                alert.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd podczas dodania studenta");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        HBox hBox = new HBox(10);

        hBox.getChildren().addAll(fetchButton, goBackButton);
        hBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(label1, textField1, label2, textField2, label3, textField3, label4, textField4,
                hBox, notepadLabel, notepad);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene newScene = new Scene(layout, 1000, 900);

        newStage.setScene(newScene);

        newStage.setTitle("Dodaj studenta");
        newStage.show();

        newStage.setOnCloseRequest(e -> {
            e.consume();
            newStage.close();
            MainWindowController.mainStage.show();
        });
    }
    /**
     * Adds a new student to the system based on user input.
     *
     * @param id      The ID of the student (required).
     * @param name    The name of the student (required).
     * @param surname The surname of the student (required).
     * @param groupId The ID of the group the student belongs to (optional).
     * @throws RuntimeException If an error occurs during the addition process.
     */
    private static void postStudent(String id, String name, String surname, String groupId) {
        try {
            Group group = null;
            if (groupId != null) {
                group = new Group();
                group.setId(Integer.parseInt(groupId));
            }

            Student student = new Student(name, surname, group);
            student.setId(Integer.parseInt(id));

            String json = gson.toJson(student);

            HttpResponse<String> apiResponse = Unirest.post(MainWindowController.BASE_URL + "/api/students")
                    .header("Content-Type", "application/json")
                    .body(json)
                    .asString();
            if (apiResponse.getStatus() != 201) {
                throw new IOException(apiResponse.getBody());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
