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
import javafx.beans.property.SimpleIntegerProperty;
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
 * Window for retrieving information about students from the system.
 */
public class GetStudentWindow {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code GetStudentWindow} class.
     * </p>
     */
    public GetStudentWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Creates the layout for the Get Student window.
     */
    public static void createGetStudentLayout() {
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

        Label label1 = new Label("Podaj id studenta (prioretyzowane)");
        label1.setStyle("-fx-font-size: 15px;");
        Label label2 = new Label("Podaj imię studenta");
        label2.setStyle("-fx-font-size: 15px;");
        Label label3 = new Label("Podaj nazwisko studenta");
        label3.setStyle("-fx-font-size: 15px;");
        Label label4 = new Label("Podaj id grupy studenta/ów");
        label4.setStyle("-fx-font-size: 15px;");

        Button goBackButton = new Button("Powrót do strony głównej");
        goBackButton.setStyle("-fx-font-size: 15px;");
        goBackButton.setOnAction(e -> {
            newStage.close();
            MainWindowController.mainStage.show();
        });

        TableView<Student> studentTable = new TableView<>();

        TableColumn<Student, Integer> idColumn = new TableColumn<>("Student ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Student, String> groupIdColumn = new TableColumn<>("Group ID");
        groupIdColumn.setCellValueFactory(cellData -> {
            Student student = cellData.getValue();
            Integer groupId = student.getGroup() == null ? null : student.getGroup().getId();
            return new SimpleStringProperty(groupId == null ? "" : groupId.toString());
        });


        studentTable.getColumns().addAll(idColumn, nameColumn, surnameColumn, groupIdColumn);

        Button fetchButton = new Button("Pobierz studenta/ów");
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

                List<Student> students = getStudents(id, name, surname, groupId);

                ObservableList<Student> groupList = FXCollections.observableArrayList(students);
                studentTable.getItems().clear();
                studentTable.setItems(groupList);

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

        layout.getChildren().addAll(label1, textField1, label2, textField2, label3, textField3, label4, textField4,
                hBox, studentTable, notepadLabel, notepad);
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
     * Retrieves a list of students based on the provided information.
     *
     * @param id      The ID of the student (priority).
     * @param name    The name of the student.
     * @param surname The surname of the student.
     * @param groupId The ID of the group the student belongs to.
     * @return A list of students matching the criteria.
     * @throws RuntimeException If an error occurs during the retrieval process.
     */
    private static List<Student> getStudents(String id, String name, String surname, String groupId) {
        try {
            String responseJsonAsString;
            if (!id.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/students")
                        .queryString("id", id).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!name.isEmpty() && !surname.isEmpty() && !groupId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/students")
                        .queryString("name", name).queryString("surname", surname).queryString("groupId", groupId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!name.isEmpty() && !surname.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/students")
                        .queryString("name", name).queryString("surname", surname).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!surname.isEmpty() && !groupId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/students")
                        .queryString("surname", surname).queryString("groupId", groupId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!name.isEmpty() && !groupId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/students")
                        .queryString("name", name).queryString("groupId", groupId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!name.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/students")
                        .queryString("name", name).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!surname.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/students")
                        .queryString("surname", surname).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!groupId.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/students")
                        .queryString("groupId", groupId).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/students").asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            }

            Type studentListType = new TypeToken<ArrayList<Student>>() {}.getType();
            return gson.fromJson(responseJsonAsString, studentListType);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
