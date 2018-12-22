package com.ti.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TableJoiner {
    public boolean useFirstTitle = true;
    public boolean useStyle = true;
    private SXSSFWorkbook resultWorkbook;
    private Map<Short,Short> styleMap;

    public void joinV(List<String> fileNames, String outFile) throws IOException {
        System.out.println("Start " + outFile);
        resultWorkbook = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = resultWorkbook.createSheet();
        AtomicInteger rowNum = new AtomicInteger();

        AtomicBoolean titleAddAlready = new AtomicBoolean(false);
        fileNames.forEach(x->{
            List<List<Cell>> listList = SimpleSheetParser.getListListCellFromSheet(Objects.requireNonNull(ExcelUtility.getMainSheet(x)));
            styleMap = new HashMap<>();
            if(!titleAddAlready.get()){
                Row row = sh.createRow(rowNum.get());
                rowNum.getAndIncrement();
                fillRowByCells(row, listList.get(0));
                titleAddAlready.set(true);
            }

            listList.subList(1,listList.size()).forEach(listOfCell->{
                Row row = sh.createRow(rowNum.get());
                fillRowByCells(row, listOfCell);
                rowNum.getAndIncrement();
            });
            System.out.println("    Add: "+ x);
//            System.out.println("Num of RES STYLES = "+ resultWorkbook.getNumCellStyles());
        });
        FileOutputStream out = new FileOutputStream(outFile);
        resultWorkbook.write(out);
        out.close();

        // dispose of temporary files backing this workbook on disk
        resultWorkbook.dispose();
        System.out.println("Complete " + outFile);
    }

    private CellStyle getStyleByMap(Cell from){
        short oldStyleIndex = from.getCellStyle().getIndex();
        if(styleMap.containsKey(oldStyleIndex)){
            return resultWorkbook.getCellStyleAt(styleMap.get(oldStyleIndex));
        }
        CellStyle newStyle = resultWorkbook.createCellStyle();
        newStyle.cloneStyleFrom(from.getCellStyle());
        styleMap.put(from.getCellStyle().getIndex(), newStyle.getIndex());
        return newStyle;
    }
    private void cloneCell(Cell from, Cell to){
//        to.setCellType(from.getCellType());
        if(useStyle){
            to.setCellStyle(getStyleByMap(from));
        }
        switch (from.getCellType()) {
            case BLANK:
                to.setCellValue(from.getStringCellValue());
                break;
            case BOOLEAN:
                to.setCellValue(from.getBooleanCellValue());
                break;
            case ERROR:
                to.setCellErrorValue(from.getErrorCellValue());
                break;
            case FORMULA:
                to.setCellFormula(from.getCellFormula());
                break;
            case NUMERIC:
                to.setCellValue(from.getNumericCellValue());
                break;
            case STRING:
                to.setCellValue(from.getStringCellValue());
                break;
        }
    }
    private void fillRowByCells(Row row, List<Cell> cellList){
        fillRowByCells(row, cellList, 0);
    }
    private void fillRowByCells(Row row, List<Cell> cellList, int skip){
        for (int i = skip; i < cellList.size(); i++) {
            Cell cell = row.createCell(i);
            cloneCell(cellList.get(i), cell);
        }
    }
}
