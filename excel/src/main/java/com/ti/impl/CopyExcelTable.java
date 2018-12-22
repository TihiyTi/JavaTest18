package com.ti.impl;

import com.ti.utils.TableJoiner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CopyExcelTable {
    //    private static String
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



    public static void main(String[] args) throws IOException {
//        File file = new File(CopyExcelTable.class.getResource("example1.xlsx").getFile());
//        File file2 = new File(CopyExcelTable.class.getResource("example2.xlsx").getFile());
        TableJoiner joiner = new TableJoiner();
        joiner.useStyle = false;
        joiner.joinV(fileNames  ,   out1);
        joiner.joinV(fileNames2  , out2);
        joiner.joinV(Stream.concat(fileNames.stream(), fileNames2.stream())
                .collect(Collectors.toList())  , out3);
    }
}
