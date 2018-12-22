package com.ti.fxexcel;

import com.ti.impl.AdvanceTableCell;
import com.ti.utils.DataGenerator;
import com.ti.utils.ExcelUtility;
import com.ti.utils.SimpleSheetParser;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TableCreator {
//    int numOfColumns;
    private DataGenerator g;
    List<String> variants;

    public TableView<ObservableList<StringProperty>> getTable(int numOfColumn){
        TableView<ObservableList<StringProperty>> table = new TableView<>();
        for (int i = 0; i < numOfColumn; i++) {
            table.getColumns().add(createColumn(i));
        }
        return table;
    }
    public ObservableList<ObservableList<StringProperty>> getTable(TableView<ObservableList<StringProperty>> table, List<List<String>> listList){
        for (int i = 0; i < listList.get(0).size(); i++) {
            table.getColumns().add(createColumn(listList.get(0).get(i),i));
        }
        ObservableList<ObservableList<StringProperty>> observableLists = FXCollections.observableArrayList();
        listList.forEach(x-> observableLists.add(FXCollections.observableList(x.stream().map(SimpleStringProperty::new).collect(Collectors.toList()))));
        observableLists.forEach(x->table.getItems().add(x));
        return observableLists;
    }
    public ObservableList<ObservableList<StringProperty>> fillTable(
            TableView<ObservableList<StringProperty>> table, ObservableList<ObservableList<StringProperty>> listList, List<String> titles){
        for (int i = 0; i < titles.size(); i++) {
            table.getColumns().add(createColumn(titles.get(i),i));
        }
        SortedList<ObservableList<StringProperty>> sortedData = new SortedList<>(listList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
//        listList.forEach(x->table.getItems().add(x));
        return listList;
    }

    public ObservableList<ObservableList<StringProperty>> fillRandomTable(TableView<ObservableList<StringProperty>> table, int numOfColumn, int numOfRow) {
        ObservableList<ObservableList<StringProperty>> observableLists = FXCollections.observableArrayList();
        for (int i = 0; i < numOfColumn; i++) {
            table.getColumns().add(createColumn(i));
        }
        for (int i = 0; i < numOfRow; i++) {
            observableLists.add(randomRowData(numOfColumn-1));
//            table.getItems().add(randomRowData(numOfColumn-1));
        }
        table.setItems(observableLists);
        return observableLists;
    }

    public TableView<ObservableList<StringProperty>> getSampleTable(int numOfColumn, int numOfRow){
        TableView<ObservableList<StringProperty>> table = new TableView<>();
        fillRandomTable(table, numOfColumn, numOfRow);
        return table;
    }

    private TableColumn<ObservableList<StringProperty>, String> createColumn(int columnIndex) {
        return createColumn("Column" + columnIndex, columnIndex);
    }
    private TableColumn<ObservableList<StringProperty>, String> createColumn(String name, int columnIndex){
        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
        column.setText(name);
        column.setCellValueFactory(cellDataFeatures ->{
            ObservableList<StringProperty> values = cellDataFeatures.getValue();
            if (columnIndex >= values.size()) {
                return new SimpleStringProperty("-");
            } else {
                return cellDataFeatures.getValue().get(columnIndex);
            }
        });
        column.setCellFactory(col -> new AdvanceTableCell<>(variants));
//        column.setCellFactory(col -> new TableCell<ObservableList<StringProperty>, String>(){
//            @Override
//            protected void updateItem(String item, boolean empty){
//                super.updateItem(item,empty);
//                if (empty || item == null) {
//                        setStyle("-fx-background-color: lightblue");
//                }
//                else{
////                    setStyle("");
//                    setStyle("-fx-background-color: red");
//                    setText(getItem());
//                }
//            }
//        });
        return column;
    }

    private ObservableList<StringProperty> randomRowData(int numOfColumn){
        ObservableList<StringProperty> list = FXCollections.observableArrayList();
        if(g ==null){
            g = new DataGenerator();
        }
        list.addAll(g.getNext(numOfColumn).stream().map(SimpleStringProperty::new).collect(Collectors.toList()));
        return list;
    }

    public void clearTable(TableView tableView){
//        tableView.getItems().clear();
        tableView.getColumns().clear();
    }

    public void addAutoCompletionList(){
        String variantrFile = "C:\\Users\\Alexey\\Documents\\JavaProjects\\JavaTest18\\excel\\src\\main\\resources\\com\\ti\\excel\\Variants.xlsx";
        List<List<String>> listList = SimpleSheetParser.getLisListStringFromSheet(Objects.requireNonNull(ExcelUtility.getMainSheet(variantrFile)));
        variants = listList.stream().skip(1).map(x->x.get(0)).collect(Collectors.toList());
//        System.out.println(variants.toString());
    }
}
