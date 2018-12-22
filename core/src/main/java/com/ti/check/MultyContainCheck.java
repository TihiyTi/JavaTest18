package com.ti.check;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultyContainCheck {
    private List<List<String>> rules;

    public MultyContainCheck(){}

    public MultyContainCheck(List<List<String>> rules) {
        this.rules = rules;
    }
    public MultyContainCheck(String... rules) {
        this.rules = Arrays.stream(rules).map(Arrays::asList).collect(Collectors.toList());
    }

    //check contains
    public boolean check(String value){
        if(rules==null) System.out.println("Initialize rules, plz");
        return rules.stream().anyMatch(x -> allContains(value, x));
    }
    //check contains
    public boolean check(String value, String... rules){
        this.rules = Arrays.stream(rules).map(Arrays::asList).collect(Collectors.toList());
        return check(value);
    }
    //check contains
    public boolean check(String value, List<List<String>> rules){
        this.rules = rules;
        return check(value);
    }
    //check contains
    public boolean checkEqual(String value, List<String> rules){
        return rules.stream().anyMatch(x -> x.equals(value));
    }

    public boolean allContains(String value, List<String> list) {
//        boolean b = list.stream().filter(x->x.startsWith("!")).
//                map(x->x.substring(1)).map(s->StringUtils.containsIgnoreCase(value,s)).
//                allMatch(s->s);
//        b = ;
//        System.out.println("Value "+ value +"    b = "+b+ "   "+list.toString());
        return list.stream().filter(x -> x.startsWith("!")).
                    map(x -> x.substring(1)).noneMatch(s -> StringUtils.containsIgnoreCase(value, s))
                &&
                list.stream().filter(x -> !x.startsWith("!")).
                        allMatch(s -> StringUtils.containsIgnoreCase(value, s));
    }
}
