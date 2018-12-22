package com.ti.utils;

import com.ti.check.MultyContainCheck;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TextFieldFactory{

    private FilteredList<ObservableList<StringProperty>> filteredList;
    Predicate<ObservableList<StringProperty>> p1 = i -> true;
    Predicate<ObservableList<StringProperty>> p2 =  i -> true;
    private int linkFilterColumn;

    public TextFieldFactory(ObservableList<ObservableList<StringProperty>> data) {
        filteredList = new FilteredList<>(data, x -> true);

    }


    public void linkSizeLabel(Label label){
        label.textProperty().bind(Bindings.format("Элементов: %d", Bindings.size(filteredList)));
    }

    public void linkFilterTextField(TextField textField, int filtercolumn){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem register = new MenuItem("Register");
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<String> choices  = filteredList.get(0).stream().map(StringExpression::getValue).collect(Collectors.toList());
                choices.add("a");
                choices.add("b");
                choices.add("c");

                ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
                dialog.setTitle("Choice Dialog");
                dialog.setHeaderText("Look, a Choice Dialog");
                dialog.setContentText("Choose your letter:");

// Traditional way to get the response value.
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    System.out.println("Your choice: " + result.get());
                }

// The Java 8 way to get the response value (with lambda expression).
                result.ifPresent(letter -> System.out.println("Your choice: " + letter));
            }
        });
        contextMenu.getItems().add(register);

        textField.setContextMenu(contextMenu);
        textField.textProperty().addListener((observable,oldValue,newValue)->{
            if(newValue == null || newValue.isEmpty()){
                p1 = i -> true;
//                p1 = null;
                filteredList.setPredicate(p2);
            }else{
                if(newValue.endsWith(".")){
                    String filter = newValue.substring(0,newValue.length()-1);
                    List<List<String>> rule = Arrays.stream(filter.split(";")).map(x-> Arrays.asList(x.split(","))).collect(Collectors.toList());
                    MultyContainCheck checker = new MultyContainCheck(rule);
                    p1 = person -> checker.check(person.get(filtercolumn).getValue());
                    filteredList.setPredicate(p1.and(p2));
                }
            }
        });

    }

    public ObservableList<ObservableList<StringProperty>> getFilteredList(){
        return filteredList;
    }

//    public static void setVariantsTextField(TextField filterField, ){
//
//    }

}
