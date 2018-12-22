package com.ti.impl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TableTester extends Application {

    private static final String TABLE_TESTER_XML = "tableTesterXml.fxml";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(TABLE_TESTER_XML));
            BorderPane root = loader.load();
            TableController controller = loader.getController();

            Scene scene = new Scene(root, 600, 400);
            controller.initHotKey();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
