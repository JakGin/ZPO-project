package com.example.client.classDate;

import com.example.client.ClassDate;
import com.example.client.LocalDateTimeAdapter;
import com.example.client.MainWindowController;
import com.example.client.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Window for retrieving class date information.
 */
public class GetClassDateWindow {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code GetClassDateWindow} class.
     * </p>
     */
    public GetClassDateWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Creates the layout for the Get Class Date window.
     */
    public static void createGetClassDateLayout() {
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

        Label label1 = new Label("Podaj id zajęć (prioretyzowane)");
        label1.setStyle("-fx-font-size: 15px;");

        Label label2 = new Label("Podaj datę zajęć");
        label2.setStyle("-fx-font-size: 15px;");
        DatePicker datePicker = new DatePicker();

        Button goBackButton = new Button("Powrót do strony głównej");
        goBackButton.setStyle("-fx-font-size: 15px;");
        goBackButton.setOnAction(e -> {
            newStage.close();
            MainWindowController.mainStage.show();
        });

        Label label3 = new Label("Podaj godzinę rozpoczęcia zajęć");
        label3.setStyle("-fx-font-size: 15px;");
        ComboBox<Integer> hourPicker = new ComboBox<>();
        hourPicker.getItems().addAll(null, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

        Label label4 = new Label("Podaj minutę rozpoczęcia zajęć");
        label4.setStyle("-fx-font-size: 15px;");
        ComboBox<Integer> minutePicker = new ComboBox<>();
        minutePicker.getItems().addAll(null, 0, 15, 30, 45);

        Label label5 = new Label("Podaj id grupy zajęć");
        label5.setStyle("-fx-font-size: 15px;");

        TableView<ClassDate> classDateTable = new TableView<>();

        TableColumn<ClassDate, Integer> idColumn = new TableColumn<>("ClassDate ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ClassDate, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> {
            ClassDate classDate = cellData.getValue();
            String date = classDate.getDate() == null ? null : classDate.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return new SimpleStringProperty(date == null ? "" : date);
        });

        TableColumn<ClassDate, String> groupIdColumn = new TableColumn<>("Group ID");
        groupIdColumn.setCellValueFactory(cellData -> {
            ClassDate classDate = cellData.getValue();
            Integer groupId = classDate.getGroup() == null ? null : classDate.getGroup().getId();
            return new SimpleStringProperty(groupId == null ? "" : groupId.toString());
        });

        classDateTable.getColumns().addAll(idColumn, dateColumn, groupIdColumn);

        Button fetchButton = new Button("Pobierz zajęcia");
        fetchButton.setStyle("-fx-font-size: 15px;");
        fetchButton.setOnAction(e -> {
            try {
                String id = textField1.getText();
                String groupId = textField2.getText();

                String date = datePicker.getValue() == null ? null : datePicker.getValue().toString();
                String hour = hourPicker.getValue() == null ? null : hourPicker.getValue().toString();
                String minute = minutePicker.getValue() == null ? null : minutePicker.getValue().toString();

                if (date != null && (hour == null || minute == null)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas pobierania zajęć");
                    alert.setContentText("Podano datę, ale nie podano godziny lub minuty");
                    alert.showAndWait();
                    return;
                } else if(hour != null && date == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas pobierania zajęć");
                    alert.setContentText("Podano godzinę, ale nie podano daty lub minuty");
                    alert.showAndWait();
                    return;
                } else if(minute != null && date == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd podczas pobierania zajęć");
                    alert.setContentText("Podano minutę, ale nie podano daty lub godziny");
                    alert.showAndWait();
                    return;
                }

                id = id.strip();
                groupId = groupId.strip();
                date = date != null ? date.strip() : null;
                hour = hour != null ? hour.strip() : null;
                minute = minute != null ? minute.strip() : null;

                List<ClassDate> classDates = getClassDates(id, date, hour, minute, groupId);

                ObservableList<ClassDate> groupList = FXCollections.observableArrayList(classDates);
                classDateTable.getItems().clear();
                classDateTable.setItems(groupList);

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd podczas pobierania zajęć");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        HBox hBox = new HBox(10);

        hBox.getChildren().addAll(fetchButton, goBackButton);
        hBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(label1, textField1, label2, datePicker, label3, hourPicker, label4, minutePicker,
                label5, textField2, hBox, classDateTable, notepadLabel, notepad);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene newScene = new Scene(layout, 1000, 900);

        newStage.setScene(newScene);

        newStage.setTitle("Pobierz zajęcia");
        newStage.show();

        newStage.setOnCloseRequest(e -> {
            e.consume();
            newStage.close();
            MainWindowController.mainStage.show();
        });
    }
    /**
     * Sends a GET request to retrieve class dates from the server.
     *
     * @param id      The ID of the class date.
     * @param date    The date of the class date.
     * @param hour    The hour of the class date.
     * @param minute  The minute of the class date.
     * @param groupId The ID of the group associated with the class date.
     * @return A list of class dates based on the specified parameters.
     */
    private static List<ClassDate> getClassDates(String id, String date, String hour, String minute, String groupId) {
        try {
            String dateTime = null;
            if (date != null) {
                if (hour.length() == 1)
                    hour = "0" + hour;

                if (minute.length() == 1)
                    minute = "0" + minute;

                dateTime = date + " " + hour + ":" + minute;
            }

            String responseJsonAsString;
            if (!id.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/classDates")
                        .queryString("id", id).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (dateTime != null && !groupId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/classDates")
                        .queryString("date", dateTime).queryString("groupId", groupId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (dateTime != null) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/classDates")
                        .queryString("date", dateTime).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!groupId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/classDates")
                        .queryString("groupId", groupId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/classDates").asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            }

            Type classDateListType = new TypeToken<ArrayList<ClassDate>>() {}.getType();
            return gson.fromJson(responseJsonAsString, classDateListType);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
