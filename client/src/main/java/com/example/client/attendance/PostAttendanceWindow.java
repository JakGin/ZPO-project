package com.example.client.attendance;

import com.example.client.*;
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
 * Window for adding attendance information.
 */
public class PostAttendanceWindow {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code PostAttendanceWindow} class.
     * </p>
     */
    public PostAttendanceWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Creates the layout for the Post Attendance window.
     */
    public static void createPostAttendanceLayout() {
        Stage newStage = new Stage();

        VBox layout = new VBox(10);

        Label notepadLabel = new Label("Notatnik");
        notepadLabel.setStyle("-fx-font-size: 20px;");
        TextArea notepad = new TextArea();
        notepad.setStyle("-fx-font-size: 15px;");
        notepad.setMaxWidth(400);
        notepad.textProperty().bindBidirectional(MainWindowController.notepadText);

        TextField textField3 = new TextField();
        TextField textField4 = new TextField();

        textField3.setMaxWidth(200);
        textField4.setMaxWidth(200);

        Label label2 = new Label("Podaj status obecności (wymagane)");
        label2.setStyle("-fx-font-size: 15px;");
        ComboBox<String> statusPicker = new ComboBox<>();
        statusPicker.getItems().addAll(null, "PRESENT", "ABSENT", "LATE", "EXCUSED");

        Label label3 = new Label("Podaj id zajęć (wymagane)");
        label3.setStyle("-fx-font-size: 15px;");

        Label label4 = new Label("Podaj id studenta (wymagane)");
        label4.setStyle("-fx-font-size: 15px;");

        Button goBackButton = new Button("Powrót do strony głównej");
        goBackButton.setStyle("-fx-font-size: 15px;");
        goBackButton.setOnAction(e -> {
            newStage.close();
            MainWindowController.mainStage.show();
        });

        Button fetchButton = new Button("Dodaj obecność");
        fetchButton.setStyle("-fx-font-size: 15px;");
        fetchButton.setOnAction(e -> {
            try {
                String status = statusPicker.getValue() == null ? "" : statusPicker.getValue();
                String classDateId = textField3.getText();
                String studentId = textField4.getText();

                status = status.strip();
                classDateId = classDateId.strip();
                studentId = studentId.strip();

                if (status.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania obecności");
                    alert.setContentText("Podaj status obecności");
                    alert.showAndWait();
                    return;
                } else if (classDateId.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania obecności");
                    alert.setContentText("Podaj id zajęć");
                    alert.showAndWait();
                    return;
                } else if (studentId.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania obecności");
                    alert.setContentText("Podaj id studenta");
                    alert.showAndWait();
                    return;
                }

                postAttendance(status, classDateId, studentId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sukces");
                alert.setHeaderText("Dodano obecność");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd podczas dodawania obecności");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        HBox hBox = new HBox(10);

        hBox.getChildren().addAll(fetchButton, goBackButton);
        hBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(label2, statusPicker, label3, textField3, label4, textField4,
                hBox, notepadLabel, notepad);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene newScene = new Scene(layout, 1000, 900);

        newStage.setScene(newScene);

        newStage.setTitle("Dodaj obecność");
        newStage.show();

        newStage.setOnCloseRequest(e -> {
            e.consume();
            newStage.close();
            MainWindowController.mainStage.show();
        });
    }
    /**
     * Sends a POST request to add attendance to the server.
     *
     * @param status     The status of the attendance.
     * @param classDateId The ID of the class date.
     * @param studentId  The ID of the student.
     */
    private static void postAttendance(String status, String classDateId, String studentId) {
        try {
            ClassDate classDate = new ClassDate();
            classDate.setId(Integer.parseInt(classDateId));

            Student student = new Student();
            student.setId(Integer.parseInt(studentId));

            Attendance attendance = new Attendance(Attendance.Status.fromString(status), student, classDate);

            String json = gson.toJson(attendance);

            HttpResponse<String> apiResponse = Unirest.post(MainWindowController.BASE_URL + "/api/attendances")
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
