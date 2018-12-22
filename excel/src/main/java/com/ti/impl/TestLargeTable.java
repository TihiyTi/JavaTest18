package com.ti.impl;

import com.ti.utils.ExcelUtility;
import com.ti.utils.SimpleSheetParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestLargeTable{

    private static List<String> fileNames = Arrays.asList(
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\AMB1_20000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\AMB20001_30000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\AMB30001_50000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\AMB50001_80000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\AMB80001_120000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\AMB120001_180600.xls"
            );
    private static List<String> fileNames2 = Arrays.asList(
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\ST1_50000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\ST50001_100000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\ST100001_150000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\ST150001_200000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\ST200001_250000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\ST250001_300000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\ST300001_350000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\ST350001_400000.xls",
            "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\ST400001_460000.xls"
    );

    private static String out1 = "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\test\\AMB.xlsx";
    private static String out2 = "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\test\\ST.xlsx";
    private static String out3 = "Z:\\DZM\\_ПОЛИКЛИНИКИ\\СКУУ\\test\\AMB_ST.xlsx";

    public static void main(String[] args) throws Throwable {
        merge(fileNames, out1);
        merge(fileNames2, out2);
        List<String> result = Stream.concat(
                fileNames.stream(), fileNames2.stream())
                .collect(Collectors.toList());
        merge(result, out3);
    }

    public static void merge(List<String> fileNames, String outFile) throws IOException {
        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        AtomicInteger rowNum = new AtomicInteger();

        fileNames.forEach(x->{
            List<List<String>> listList = SimpleSheetParser.getLisListStringFromSheet(Objects.requireNonNull(ExcelUtility.getMainSheet(x)));
            listList.forEach(y->{
                Row row = sh.createRow(rowNum.get());
                for (int i = 0; i < y.size(); i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(y.get(i));
                }
                rowNum.getAndIncrement();
            });
            System.out.println("Complete: "+ x);
        });

        FileOutputStream out = new FileOutputStream(outFile);
        wb.write(out);
        out.close();

        // dispose of temporary files backing this workbook on disk
        wb.dispose();
    }
}