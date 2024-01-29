/**
 * Module-info for the JavaFX client application.
 *
 * <p>This module requires JavaFX controls and FXML. Additionally, it uses Unirest for HTTP requests,
 * Gson for JSON processing, and Java SQL for database interactions.
 * </p>
 *
 * <p>The package com.example.client is opened to JavaFX FXML and Gson for reflection purposes.
 * The com.example.client package and com.example.client.group package are exported for accessibility.
 * The com.example.client.group package is opened to Gson and JavaFX FXML.
 * </p>
 */
module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires unirest.java;
    requires gson;
    requires java.sql;

    opens com.example.client to javafx.fxml, gson;
    exports com.example.client;
    exports com.example.client.group;
    exports com.example.client.attendance;
    exports com.example.client.student;
    exports com.example.client.classDate;
    opens com.example.client.group to gson, javafx.fxml;
}