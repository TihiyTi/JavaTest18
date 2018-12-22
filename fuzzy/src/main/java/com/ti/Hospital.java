package com.ti;

import com.ti.check.CheckRuleBuilder;
import com.ti.check.MultyContainCheck;

import java.util.stream.Stream;

public class Hospital{
    HospitalType type;
    int number = 0;

    public Hospital(HospitalType type, int number) {
        this.type = type;
        this.number = number;
    }

    public Hospital(String sourceName){
        type = findHospitalType(sourceName);
        number = findHospitalNumber(sourceName);
    }

    public String canonicalName(){
        return type.name() + (number==0?"":number);
    }

    @Override
    public String toString() {
        return type.name() + " " + number;
    }

    @Override
    public boolean equals(Object obj) {
        return type.equals(((Hospital) obj).type) & number == ((Hospital) obj).number;
    }

    enum HospitalType {
        GP("гп,!дгп;гор,пол,!детск"),
        DGP("дгп;детс,гор,пол"),
        STP("стом,пол"),
        GB("гкб;гор,бол")
        ;

        HospitalType(String rule){
            checker = new MultyContainCheck(CheckRuleBuilder.ruleBuild(rule));
        }

        private MultyContainCheck checker;
        public boolean hospitalCheck(String source){
            return checker.check(source);
        }
    }

    public static String canonicalNameStatic(String source){
        HospitalType type = findHospitalType(source);
        int number = findHospitalNumber(source);
        return type==null ? "NULL" : type.name() +" "+ (number==0?"":number);
    }

    public static HospitalType findHospitalType(String source){
        return Stream.of(HospitalType.values()).filter(x -> x.hospitalCheck(source)).findFirst().orElse(null);
    }
    public static int findHospitalNumber(String source){
        return Stream.of(source.split("\\D")).filter(x->!x.isEmpty()).filter(x-> x.length() < 4).map(Integer::valueOf).findFirst().orElse(0);
    }
}