package com.example.client.classDate;

import com.example.client.*;
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
import java.time.format.DateTimeFormatter;

/**
 * Window for posting class date information.
 */
public class PostClassDateWindow {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code PostClassDateWindow} class.
     * </p>
     */
    public PostClassDateWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Creates the layout for the Post Class Date window.
     */
    public static void createPostClassDateLayout() {
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

        Label label2 = new Label("Podaj datę zajęć (wymagane)");
        label2.setStyle("-fx-font-size: 15px;");
        DatePicker datePicker = new DatePicker();

        Button goBackButton = new Button("Powrót do strony głównej");
        goBackButton.setStyle("-fx-font-size: 15px;");
        goBackButton.setOnAction(e -> {
            newStage.close();
            MainWindowController.mainStage.show();
        });


        Label label3 = new Label("Podaj godzinę rozpoczęcia zajęć (wymagane)");
        label3.setStyle("-fx-font-size: 15px;");
        ComboBox<Integer> hourPicker = new ComboBox<>();
        hourPicker.getItems().addAll(null, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

        Label label4 = new Label("Podaj minutę rozpoczęcia zajęć (wymagane)");
        label4.setStyle("-fx-font-size: 15px;");
        ComboBox<Integer> minutePicker = new ComboBox<>();
        minutePicker.getItems().addAll(null, 0, 15, 30, 45);

        Button fetchButton = new Button("Dodaj zajęcia");
        fetchButton.setStyle("-fx-font-size: 15px;");
        fetchButton.setOnAction(e -> {
            try {
                if (datePicker.getValue() == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania zajęć");
                    alert.setContentText("Nie podano daty zajęć");
                    alert.showAndWait();
                    return;
                } else if (hourPicker.getValue() == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania zajęć");
                    alert.setContentText("Nie podano godziny rozpoczęcia zajęć");
                    alert.showAndWait();
                    return;
                } else if (minutePicker.getValue() == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania zajęć");
                    alert.setContentText("Nie podano minuty rozpoczęcia zajęć");
                    alert.showAndWait();
                    return;
                }

                String groupId = textField1.getText();
                String date = datePicker.getValue().toString();
                String hour = hourPicker.getValue().toString();
                String minute = minutePicker.getValue().toString();

                groupId = groupId.strip();
                date = date.strip();
                hour = hour.strip();
                minute = minute.strip();

                if(groupId.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania zajęć");
                    alert.setContentText("Nie podano id grupy");
                    alert.showAndWait();
                    return;
                } else if(date.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania zajęć");
                    alert.setContentText("Nie podano daty zajęć");
                    alert.showAndWait();
                    return;
                } else if(hour.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania zajęć");
                    alert.setContentText("Nie podano godziny rozpoczęcia zajęć");
                    alert.showAndWait();
                    return;
                } else if(minute.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas dodawania zajęć");
                    alert.setContentText("Nie podano minuty rozpoczęcia zajęć");
                    alert.showAndWait();
                    return;
                }

                postClassDate(date, hour, minute, groupId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sukces");
                alert.setHeaderText("Dodano zajęcia");
                alert.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd podczas dodania zajęć");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        HBox hBox2 = new HBox(10);

        hBox2.getChildren().addAll(fetchButton, goBackButton);
        hBox2.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(label2, datePicker, label3, hourPicker, label4, minutePicker,
                label1, textField1, hBox2, notepadLabel, notepad);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene newScene = new Scene(layout, 1000, 900);

        newStage.setScene(newScene);

        newStage.setTitle("Dodaj zajęcia");
        newStage.show();

        newStage.setOnCloseRequest(e -> {
            e.consume();
            newStage.close();
            MainWindowController.mainStage.show();
        });
    }
    /**
     * Sends a POST request to add class date information to the server.
     *
     * @param date    The date of the class date.
     * @param hour    The hour of the class date.
     * @param minute  The minute of the class date.
     * @param groupId The ID of the group associated with the class date.
     */
    private static void postClassDate(String date, String hour, String minute, String groupId) {
        try {
            if (hour.length() == 1)
                hour = "0" + hour;
            if (minute.length() == 1)
                minute = "0" + minute;

            LocalDateTime dateTime = LocalDateTime.parse(date + " " + hour + ":" + minute, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            Group group = new Group();
            group.setId(Integer.valueOf(groupId));

            ClassDate classDate = new ClassDate();
            classDate.setDate(dateTime);
            classDate.setGroup(group);

            String json = gson.toJson(classDate);

            HttpResponse<String> apiResponse = Unirest.post(MainWindowController.BASE_URL + "/api/classDates")
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
