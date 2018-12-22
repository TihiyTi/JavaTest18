package com.ti.excel;

import com.ti.utils.ExcelUtility;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableViewer extends Application {
    @Override
    public void start(Stage stage) {
        ObservableList<UserAccount> usersData = FXCollections.observableArrayList();
        initData(usersData);


        TableView<UserAccount> table = new TableView<>();


        // Create column UserName (Data type of String).
        TableColumn<UserAccount, String> userNameCol = new TableColumn<>("User Name");
        TableColumn<UserAccount, String> emailCol = new TableColumn<>("Email");
        TableColumn<UserAccount, String> fullNameCol = new TableColumn<>("Full Name");
        TableColumn<UserAccount, String> firstNameCol = new TableColumn<>("First Name");
        TableColumn<UserAccount, String> lastNameCol = new TableColumn<>("Last Name");

        userNameCol.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("userName"));

//        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
//        loginColumn.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
//        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        table.getColumns().addAll(userNameCol, emailCol, fullNameCol,firstNameCol, lastNameCol);
        table.setItems(usersData);


        StackPane root = new StackPane();
        root.setPadding(new Insets(5));
        root.getChildren().add(table);

        stage.setTitle("TableView (o7planning.org)");

        Scene scene = new Scene(root, 450, 300);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public List<String> readData() throws FileNotFoundException {
        List<String> list = new ArrayList<>();

        File f = new File(ExcelModify.class.getResource("Spravochnik.xlsx").getFile());
        InputStream inputStream= new FileInputStream(f);

        Sheet sheet = ExcelUtility.getMainSheet(inputStream);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            if(row.getRowNum() == 0){
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()){
                    String s = cellIterator.next().getStringCellValue();
                    list.add(s);
                    System.out.println(s);
                }
            }
        }
        return list;
    }
    private void initData(ObservableList<UserAccount> usersData) {
        usersData.add(new UserAccount(1L, "Alex", "qwerty", "alex@mail.com",""));
        usersData.add(new UserAccount(2L, "Bob", "dsfsdfw", "bob@mail.com",""));
        usersData.add(new UserAccount(3L, "Jeck", "dsfdsfwe", "Jeck@mail.com",""));
        usersData.add(new UserAccount(4L, "Mike", "iueern", "mike@mail.com",""));
        usersData.add(new UserAccount(5L, "colin", "woeirn", "colin@mail.com",""));
    }

}
