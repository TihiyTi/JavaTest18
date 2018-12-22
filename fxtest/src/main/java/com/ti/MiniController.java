package com.ti;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class MiniController {
    public TextField name;
    public TextField surname;
    public TextField age;

    PrintServer printServer;

    public void login(ActionEvent actionEvent) {
        printServer.printString("Name " + name.getText());
        printServer.printString("Surname " + surname.getText());
        printServer.printString("Age " + age.getText());
    }
}
