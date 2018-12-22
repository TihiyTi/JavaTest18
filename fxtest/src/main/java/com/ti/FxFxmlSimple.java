package com.ti;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FxFxmlSimple extends Application {

    private static  final String SCENE_XML = "charts.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(SCENE_XML));
            AnchorPane root =  loader.load();
            Scene scene = new Scene(root,600,600);
//            String css = "TestTheme.css";
//            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception{
        super.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}