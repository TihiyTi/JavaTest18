package com.ti;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FXSimpleView extends Application {

    private static final Logger LOG = LogManager.getLogger(FXSimpleView.class.getName());

    @Override
    public void start(Stage primaryStage){

        try{
            LOG.info("Start");
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root,600,600);
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
