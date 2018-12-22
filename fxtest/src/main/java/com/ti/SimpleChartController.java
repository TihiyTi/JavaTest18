package com.ti;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SimpleChartController implements PrintServer {
    public Button next;

    private static  final String MINI_FXML = "mini.fxml";

    public void buttonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(MINI_FXML));
        Parent mini = loader.load();
        MiniController miniController = loader.getController();
        miniController.printServer = this;
        Scene miniScene = new Scene(mini);
        Stage miniStage = new Stage();
        miniStage.setScene(miniScene);
        miniStage.show();
    }

    @Override
    public void printString(String s){
        System.out.println(s);
    }
}
