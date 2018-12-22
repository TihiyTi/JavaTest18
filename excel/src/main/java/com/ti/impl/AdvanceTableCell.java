package com.ti.impl;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;

import java.util.Arrays;
import java.util.List;

public class AdvanceTableCell<T,S> extends  TableCell<T, S> {
    private TextField textField;
    List<String> variants;

    public AdvanceTableCell(){}
    public AdvanceTableCell(List<String> autoCompleteVariants){
        variants = autoCompleteVariants;
    }

    @Override
    public void startEdit(){
        if(!isEmpty()){
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
            textField.requestFocus();
//            textField.positionCaret();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getString());
        textField = null;
        setGraphic(null);
        System.out.println("Cell edit END");
    }

    @Override
    public void updateItem(S item, boolean empty) {
        super.updateItem(item, empty);
        System.out.println("Update");
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
//                setGraphic(null);
//                setStyle("-fx-background-color: blue");
            }
        }
    }

    protected void commitHelper( boolean losingFocus ) {
        if(!getItem().equals(textField.getText())){
//            commitEdit(new Person.Pair(textField.getText(),"green"));
            commitEdit((S)textField.getText());
        }
        textField = null;
        setGraphic(null);
    }



    private void createTextField() {
        textField = new TextField(getString());
        TextFields.bindAutoCompletion(textField, variants);
        textField.positionCaret(getString().length());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    commitHelper(false);
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
        textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean arg1, Boolean arg2) {
                if (!arg2 && textField!=null) {
                    commitHelper(true);
                }
            }
        });
    }
    private String getString(){
        return getItem() == null ? "" : (String) getItem();
    }
}
