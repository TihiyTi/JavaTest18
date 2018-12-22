package com.ti.excel;

import com.ti.utils.ExcelUtility;
import com.ti.utils.SimpleSheetParser;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.List;

public class ExcelModify {
    public static void main(String[] args) throws FileNotFoundException {

//        ClassLoader classLoader = ExcelModify.class.getClassLoader();
        File f = new File(ExcelModify.class.getResource("Spravochnik.xlsx").getFile());
//        InputStream inputStream= new FileInputStream(f);

        Sheet sheet = ExcelUtility.getMainSheet(f);
        List<List<String>> table = SimpleSheetParser.getLisListStringFromSheet(sheet);
        table.forEach(x-> System.out.println(x.toString()));
    }
}
