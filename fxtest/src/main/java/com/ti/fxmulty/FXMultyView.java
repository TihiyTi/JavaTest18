package com.ti.fxmulty;

import com.ti.MiniController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMultyView extends Application {

    private static  final String SCENE_XML = "fxMultyView.fxml";


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(SCENE_XML));
            Parent mini = loader.load();
            FxMultyViewController miniController = loader.getController();

            Scene miniScene = new Scene(mini);
            Stage miniStage = new Stage();
            miniStage.setScene(miniScene);
            miniStage.show();
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
