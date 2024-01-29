package com.example.client.attendance;

import com.example.client.Attendance;
import com.example.client.ClassDate;
import com.example.client.LocalDateTimeAdapter;
import com.example.client.MainWindowController;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Window for retrieving attendance information.
 */
public class GetAttendanceWindow {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code GetAttendanceWindow} class.
     * </p>
     */
    public GetAttendanceWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Creates the layout for the Get Attendance window.
     */
    public static void createGetAttendanceLayout() {
        Stage newStage = new Stage();

        VBox layout = new VBox(10);

        Label notepadLabel = new Label("Notatnik");
        notepadLabel.setStyle("-fx-font-size: 20px;");
        TextArea notepad = new TextArea();
        notepad.setStyle("-fx-font-size: 15px;");
        notepad.setMaxWidth(400);
        notepad.textProperty().bindBidirectional(MainWindowController.notepadText);

        TextField textField1 = new TextField();
        TextField textField3 = new TextField();
        TextField textField4 = new TextField();

        textField1.setMaxWidth(200);
        textField3.setMaxWidth(200);
        textField4.setMaxWidth(200);

        Label label1 = new Label("Podaj id obecności (prioretyzowane)");
        label1.setStyle("-fx-font-size: 15px;");

        Label label2 = new Label("Podaj status obecności");
        label2.setStyle("-fx-font-size: 15px;");
        ComboBox<String> statusPicker = new ComboBox<>();
        statusPicker.getItems().addAll(null, "PRESENT", "ABSENT", "LATE", "EXCUSED");

        Label label3 = new Label("Podaj id zajęć");
        label3.setStyle("-fx-font-size: 15px;");

        Label label4 = new Label("Podaj id studenta");
        label4.setStyle("-fx-font-size: 15px;");

        Button goBackButton = new Button("Powrót do strony głównej");
        goBackButton.setStyle("-fx-font-size: 15px;");
        goBackButton.setOnAction(e -> {
            newStage.close();
            MainWindowController.mainStage.show();
        });

        TableView<Attendance> attendanceTable = new TableView<>();

        TableColumn<Attendance, Integer> idColumn = new TableColumn<>("Attendance ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Attendance, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Attendance, String> dateColumn = new TableColumn<>("ClassDate ID");
        dateColumn.setCellValueFactory(cellData -> {
            Attendance attendance = cellData.getValue();
            Integer date = null;
            if (attendance.getClassDate() != null)
                date = attendance.getClassDate().getId();
            return new SimpleStringProperty(date == null ? "" : String.valueOf(date));
        });

        TableColumn<Attendance, String> studentColumn = new TableColumn<>("Student ID");
        studentColumn.setCellValueFactory(cellData -> {
            Attendance attendance = cellData.getValue();
            Integer student = null;
            if (attendance.getStudent() != null)
                student = attendance.getStudent().getId();
            return new SimpleStringProperty(student == null ? "" : String.valueOf(student));
        });

        attendanceTable.getColumns().addAll(idColumn, statusColumn, dateColumn, studentColumn);

        Button fetchButton = new Button("Pobierz zajęcia");
        fetchButton.setStyle("-fx-font-size: 15px;");
        fetchButton.setOnAction(e -> {
            try {
                String id = textField1.getText();
                String status = statusPicker.getValue() == null ? "" : statusPicker.getValue();
                String classDateId = textField3.getText();
                String studentId = textField4.getText();

                id = id.strip();
                status = status.strip();
                classDateId = classDateId.strip();
                studentId = studentId.strip();

                List<Attendance> attendances = getAttendances(id, status, classDateId, studentId);

                ObservableList<Attendance> groupList = FXCollections.observableArrayList(attendances);
                attendanceTable.getItems().clear();
                attendanceTable.setItems(groupList);

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd podczas pobierania studenta/ów");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        HBox hBox = new HBox(10);

        hBox.getChildren().addAll(fetchButton, goBackButton);
        hBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(label1, textField1, label2, statusPicker, label3, textField3, label4, textField4,
                hBox, attendanceTable, notepadLabel, notepad);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene newScene = new Scene(layout, 1000, 900);

        newStage.setScene(newScene);

        newStage.setTitle("Pobierz studenta/ów");
        newStage.show();

        newStage.setOnCloseRequest(e -> {
            e.consume();
            newStage.close();
            MainWindowController.mainStage.show();
        });
    }
    /**
     * Retrieves attendance information from the server based on specified criteria.
     *
     * @param id         The ID of the attendance (optional).
     * @param status     The status of the attendance (optional).
     * @param classDateId The ID of the class date (optional).
     * @param studentId  The ID of the student (optional).
     * @return A list of Attendance objects based on the specified criteria.
     */
    private static List<Attendance> getAttendances(String id, String status, String classDateId, String studentId) {
        try {
            String responseJsonAsString;
            if (!id.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/attendances")
                        .queryString("id", id).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!status.isEmpty() && !classDateId.isEmpty() && !studentId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/attendances")
                        .queryString("status", status).queryString("classDateId", classDateId)
                        .queryString("studentId", studentId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!status.isEmpty() && !classDateId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/attendances")
                        .queryString("status", status).queryString("classDateId", classDateId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!status.isEmpty() && !studentId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/attendances")
                        .queryString("status", status).queryString("studentId", studentId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!classDateId.isEmpty() && !studentId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/attendances")
                        .queryString("classDateId", classDateId).queryString("studentId", studentId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!status.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/attendances")
                        .queryString("status", status).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!classDateId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/attendances")
                        .queryString("classDateId", classDateId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!studentId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/attendances")
                        .queryString("studentId", studentId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/attendances").asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            }

            Type classDateListType = new TypeToken<ArrayList<Attendance>>() {}.getType();
            return gson.fromJson(responseJsonAsString, classDateListType);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
