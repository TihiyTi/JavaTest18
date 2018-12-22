package com.ti.check;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CheckRuleBuilder {

    public static List<List<String>> ruleBuild(String ruleString){
        return Arrays.stream(ruleString.split(";")).map(x-> Arrays.asList(x.split(","))).collect(Collectors.toList());
    }
}
