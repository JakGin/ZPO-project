package com.example.client.group;

import com.example.client.Group;
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
 * Window for adding a new group to the system.
 */
public class PostGroupWindow {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code PostGroupWindow} class.
     * </p>
     */
    public PostGroupWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Creates the layout for the Post Group window.
     */
    public static void createPostGroupLayout() {
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

        Label label1 = new Label("Podaj nazwę grupy (wymagane)");
        label1.setStyle("-fx-font-size: 15px;");

        Button goBackButton = new Button("Powrót do strony głównej");
        goBackButton.setStyle("-fx-font-size: 15px;");
        goBackButton.setOnAction(e -> {
            newStage.close();
            MainWindowController.mainStage.show();
        });

        Button fetchButton = new Button("Dodaj grupę");
        fetchButton.setStyle("-fx-font-size: 15px;");
        fetchButton.setOnAction(e -> {
            try {
                String name = textField1.getText();
                name = name.strip();

                if (name.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania grupy");
                    alert.setContentText("Nazwa grupy nie może być pusta");
                    alert.showAndWait();
                    return;
                }

                Group group = new Group(name);

                postGroup(group);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sukces");
                alert.setHeaderText("Dodano grupę");
                alert.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd podczas dodawania grupy");
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

        newStage.setTitle("Dodaj grupę");
        newStage.show();

        newStage.setOnCloseRequest(e -> {
            e.consume();
            newStage.close();
            MainWindowController.mainStage.show();
        });
    }
    /**
     * Adds a new group to the system based on the provided information.
     *
     * @param group The Group object containing the information about the new group.
     * @throws IOException If an error occurs during the HTTP request.
     */
    private static void postGroup(Group group) throws IOException {
        try {
            String json = gson.toJson(group);
            HttpResponse<String> response = Unirest.post(MainWindowController.BASE_URL + "/api/groups")
                    .header("Content-Type", "application/json")
                    .body(json)
                    .asString();
            if (response.getStatus() != 201) {
                throw new IOException("Non-successful response code: " + response.getStatus());
            }
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }
}
