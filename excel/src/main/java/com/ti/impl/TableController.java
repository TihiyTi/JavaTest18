package com.ti.impl;

import com.ti.utils.ExcelUtility;
import com.ti.utils.SimpleSheetParser;
import com.ti.fxexcel.TableCreator;
import com.ti.utils.TextFieldFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TableController implements Initializable {

    public TableView<ObservableList<StringProperty>> table;
    public BorderPane borderPane;
    public TextField filter;
    public Label elements;
    public TextField addToAll;

    TextFieldFactory fieldFactory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableCreator creator = new TableCreator();
        ObservableList<ObservableList<StringProperty>> observableList = creator.fillRandomTable(table, 3, 5);
        ObservableList<ObservableList<StringProperty>> filteredList = prepareTextField(observableList);
        table.setItems(filteredList);

        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
//        borderPane.setCenter(table);
    }

    public void initHotKey(){
        borderPane.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.S,  KeyCombination.CONTROL_ANY),
                ()->{
                    String out1 = "C:\\Users\\Alexey\\Documents\\JavaProjects\\JavaTest18\\excel\\src\\main\\resources\\com\\ti\\excel\\out.xlsx";
                    SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
                    Sheet sh = wb.createSheet();
                    AtomicInteger rowNum = new AtomicInteger();
                    List<List<String>> listList = table.getItems().stream().map(y -> y.stream().map(StringProperty::getValue).collect(Collectors.toList())).collect(Collectors.toList());
                    listList.forEach(y->{
                        Row row = sh.createRow(rowNum.get());
                        for (int i = 0; i < y.size(); i++) {
                            Cell cell = row.createCell(i);
                            cell.setCellValue(y.get(i));
                        }
                        rowNum.getAndIncrement();
                    });
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(out1);
                        wb.write(out);
                        out.close();
                        wb.dispose();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }

                });
        borderPane.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_ANY),
                ()->{
                    table.getItems().forEach(x-> System.out.println(x.get(1).toString()));
                });
    }

    public void loadSimpleList(ActionEvent actionEvent) {
        System.out.println("ChooseFile");
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("C:\\Users\\Alexey\\Documents\\JavaProjects\\JavaTest18\\excel\\src\\main\\resources\\com\\ti\\excel"));
        File selectedFile = chooser.showOpenDialog(borderPane.getScene().getWindow());
        if(selectedFile!=null){
            System.out.println("!"+selectedFile.getAbsolutePath());
            List<List<String>> listList = SimpleSheetParser.getLisListStringFromSheet(Objects.requireNonNull(ExcelUtility.getMainSheet(selectedFile)));
            ObservableList<ObservableList<StringProperty>> observableList = prepareListForTable(listList);

            fieldFactory = new TextFieldFactory(observableList);
            fieldFactory.linkSizeLabel(elements);
            fieldFactory.linkFilterTextField(filter, 1);
            ObservableList<ObservableList<StringProperty>> filteredList = fieldFactory.getFilteredList();
//
//            ObservableList<ObservableList<StringProperty>> filteredList = prepareTextField(observableList);

            TableCreator creator = new TableCreator();
            creator.addAutoCompletionList();
            creator.clearTable(table);
            creator.fillTable(table, filteredList, listList.get(0));
//            table.setItems(filteredList);
        }
    }

    private ObservableList<ObservableList<StringProperty>> prepareTextField(ObservableList<ObservableList<StringProperty>> observableList){
        fieldFactory = new TextFieldFactory(observableList);
        fieldFactory.linkSizeLabel(elements);
        fieldFactory.linkFilterTextField(filter, 1);
        return fieldFactory.getFilteredList();
    }

    private void prepareAddToAllTextField(){

//        addToAll.
    }

    private ObservableList<ObservableList<StringProperty>> prepareListForTable(List<List<String>> listList){
        ObservableList<ObservableList<StringProperty>> observableLists = FXCollections.observableArrayList();
        listList.stream().skip(1).
                forEach(x-> observableLists.add(FXCollections.observableList(x.stream().map(SimpleStringProperty::new).collect(Collectors.toList()))));
        return observableLists;
    }
}
