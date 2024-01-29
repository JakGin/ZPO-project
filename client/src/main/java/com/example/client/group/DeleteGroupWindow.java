package com.example.client.group;

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
 * Window for deleting a group.
 */
public class DeleteGroupWindow {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code DeleteGroupWindow} class.
     * </p>
     */
    public DeleteGroupWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Creates the layout for the Delete Group window.
     */
    public static void createDeleteGroupLayout() {
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

        Label label1 = new Label("Podaj id grupy (wymagane)");
        label1.setStyle("-fx-font-size: 15px;");

        Button goBackButton = new Button("Powrót do strony głównej");
        goBackButton.setStyle("-fx-font-size: 15px;");
        goBackButton.setOnAction(e -> {
            newStage.close();
            MainWindowController.mainStage.show();
        });

        Button fetchButton = new Button("Usuń grupę");
        fetchButton.setStyle("-fx-font-size: 15px;");
        fetchButton.setOnAction(e -> {
            try {
                String id = textField1.getText();
                id = id.strip();

                if (id.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas usuwania grupy");
                    alert.setContentText("Id grupy nie może być puste");
                    alert.showAndWait();
                    return;
                }

                deleteGroup(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sukces");
                alert.setHeaderText("Usunięto grupę");
                alert.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd podczas usuwania grupy");
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

        newStage.setTitle("Usuń grupę");
        newStage.show();

        newStage.setOnCloseRequest(e -> {
            e.consume();
            newStage.close();
            MainWindowController.mainStage.show();
        });
    }
    /**
     * Deletes a group using its ID.
     *
     * @param groupId The ID of the group to be deleted.
     * @throws IOException If an error occurs during the deletion process.
     */
    private static void deleteGroup(String groupId) throws IOException {
        try {
            HttpResponse<String> response = Unirest.delete(MainWindowController.BASE_URL + "/api/groups").queryString("id", groupId).asString();
            if (response.getStatus() != 200) {
                throw new IOException("Błąd podczas usuwania grupy");
            }
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }
}
