package com.example.client.student;

import com.example.client.LocalDateTimeAdapter;
import com.example.client.MainWindowController;
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
 * Window for deleting a student from the system.
 */
public class DeleteStudentWindow {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code DeleteStudentWindow} class.
     * </p>
     */
    public DeleteStudentWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Creates the layout for the Delete Student window.
     */
    public static void createDeleteStudentLayout() {
        Stage newStage = new Stage();

        VBox layout = new VBox(10);

        Label notepadLabel = new Label("Notatnik");
        notepadLabel.setStyle("-fx-font-size: 20px;");
        TextArea notepad = new TextArea();
        notepad.setStyle("-fx-font-size: 15px;");
        notepad.setMaxWidth(400);
        notepad.textProperty().bindBidirectional(MainWindowController.notepadText);

        TextField textField1 = new TextField();

        textField1.setMaxWidth(200);

        Label label1 = new Label("Podaj id studenta (wymagane)");
        label1.setStyle("-fx-font-size: 15px;");

        Button goBackButton = new Button("Powrót do strony głównej");
        goBackButton.setStyle("-fx-font-size: 15px;");
        goBackButton.setOnAction(e -> {
            newStage.close();
            MainWindowController.mainStage.show();
        });

        Button fetchButton = new Button("Usuń studenta");
        fetchButton.setStyle("-fx-font-size: 15px;");
        fetchButton.setOnAction(e -> {
            try {
                String id = textField1.getText();
                id = id.strip();

                if (id.length() != 6) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas usuwania studenta");
                    alert.setContentText("Id studenta musi mieć 6 znaków");
                    alert.showAndWait();
                    return;
                }

                deleteStudent(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sukces");
                alert.setHeaderText("Usunięto studenta");
                alert.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd podczas usuwania studenta");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        HBox hBox = new HBox(10);

        hBox.getChildren().addAll(fetchButton, goBackButton);
        hBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(label1, textField1, hBox, notepadLabel, notepad);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene newScene = new Scene(layout, 1000, 900);

        newStage.setScene(newScene);

        newStage.setTitle("Usuń studenta");
        newStage.show();

        newStage.setOnCloseRequest(e -> {
            e.consume();
            newStage.close();
            MainWindowController.mainStage.show();
        });
    }
    /**
     * Deletes a student from the system based on the provided information.
     *
     * @param studentId The ID of the student to be deleted.
     * @throws IOException If an error occurs during the deletion process.
     */
    private static void deleteStudent(String studentId) throws IOException {
        try {
            HttpResponse<String> response = Unirest.delete(MainWindowController.BASE_URL + "/api/students")
                    .queryString("id", studentId).asString();
            if (response.getStatus() != 200) {
                throw new IOException("Błąd podczas usuwania studenta");
            }
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }
}
