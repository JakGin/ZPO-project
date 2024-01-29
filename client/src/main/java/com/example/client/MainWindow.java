package com.example.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for the application, responsible for launching the JavaFX application window.
 */
public class MainWindow extends Application {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code MainWindow} class.
     * </p>
     */
    public MainWindow() {
        // Initialize any necessary components or perform setup if needed.
    }
    /**
     * Entry point for launching the JavaFX application.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = MainWindowController.createMainWindowLayout(stage);
        stage.setScene(scene);
        stage.setTitle("Aplikacja obecność");
        stage.show();

        stage.setOnCloseRequest(e -> {
            e.consume();
            stage.close();
        });
    }
    /**
     * The main method that launches the JavaFX application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch();
    }
}