package com.example.client.group;

import com.example.client.Group;
import com.example.client.LocalDateTimeAdapter;
import com.example.client.MainWindowController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
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
 * Window for retrieving groups based on specified criteria.
 */
public class GetGroupWindow {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code GetGroupWindow} class.
     * </p>
     */
    public GetGroupWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Creates the layout for the Get Group window.
     */
    public static void createGetGroupLayout() {
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

        Label label1 = new Label("Podaj id grupy (prioretyzowane)");
        label1.setStyle("-fx-font-size: 15px;");
        Label label2 = new Label("Podaj nazwę grupy");
        label2.setStyle("-fx-font-size: 15px;");

        Button goBackButton = new Button("Powrót do strony głównej");
        goBackButton.setStyle("-fx-font-size: 15px;");
        goBackButton.setOnAction(e -> {
            newStage.close();
            MainWindowController.mainStage.show();
        });

        TableView<Group> groupTable = new TableView<>();

        TableColumn<Group, Integer> idColumn = new TableColumn<>("Group ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Group, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        groupTable.getColumns().addAll(idColumn, nameColumn);

        Button fetchButton = new Button("Pobierz grupę/y");
        fetchButton.setStyle("-fx-font-size: 15px;");
        fetchButton.setOnAction(e -> {
            try {
                String id = textField1.getText();
                String name = textField2.getText();
                id = id.strip();
                name = name.strip();

                List<Group> groups = getGroups(id, name);

                ObservableList<Group> groupList = FXCollections.observableArrayList(groups);
                groupTable.getItems().clear();
                groupTable.setItems(groupList);

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd podczas pobierania grupy");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        HBox hBox = new HBox(10);

        hBox.getChildren().addAll(fetchButton, goBackButton);
        hBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(label1, textField1, label2, textField2, hBox, groupTable, notepadLabel, notepad);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene newScene = new Scene(layout, 1000, 900);

        newStage.setScene(newScene);

        newStage.setTitle("Pobierz grupę/y");
        newStage.show();

        newStage.setOnCloseRequest(e -> {
            e.consume();
            newStage.close();
            MainWindowController.mainStage.show();
        });
    }
    /**
     * Retrieves a list of groups based on specified criteria.
     *
     * @param id   The ID of the group (optional).
     * @param name The name of the group (optional).
     * @return The list of groups matching the criteria.
     */
    private static List<Group> getGroups(String id, String name) {
        try {
            String responseJsonAsString;
            if (!id.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/groups")
                        .queryString("id", id).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else if (!name.isEmpty()) {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/groups")
                        .queryString("name", name).asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            } else {
                HttpResponse<JsonNode> apiResponse = Unirest.get(MainWindowController.BASE_URL + "/api/groups").asJson();
                responseJsonAsString = apiResponse.getBody().toString();
            }

            Type groupListType = new TypeToken<ArrayList<Group>>() {}.getType();
            return gson.fromJson(responseJsonAsString, groupListType);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
