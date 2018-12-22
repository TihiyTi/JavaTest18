package com.ti.reo;

public class SphereCalc2 {

    public static int N = 100;

    public static double getFullImpedance(double ro1, double ro2, double a, double b, double r, double h, double x, double y){
        double value = ro1*2*b/(Math.PI*(a*a-b*b));
        value += getMeasurement(ro1, ro2, a, b, r, h, x, y);
        return value;
    }

    public static double getFullImpedance(double[] params){
        double value = params[0]*2*params[3]/(Math.PI*(params[2]*params[2]-params[3]*params[3]));
        value += getMeasurement(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
        return value;
    }


    private static double getMeasurement(double ro1, double ro2, double a, double b, double r, double h, double x, double y) {
        double toA = toA(a, r, h, x, y);
        double toB = toB(a, r, h, x, y);
        double toN = toN(b, r, h, x, y);
        double toM = toM(b, r, h, x, y);

        double value = potentialInPointFromElectrode(ro1, ro2, r, toA, toM, getCosinus(toM,toA, (a - b)));
        value -= potentialInPointFromElectrode(ro1, ro2, r, toA, toN, getCosinus(toN,toA, (a + b)));
        value += potentialInPointFromElectrode(ro1, ro2, r, toB, toN, getCosinus(toN,toB, (a - b)));
        value -= potentialInPointFromElectrode(ro1, ro2, r, toB, toM, getCosinus(toM,toB, (a + b)));
        return value;


    }

    private static double toM(double b, double r, double h, double x, double y){
        return Math.sqrt((r+h)*(r+h) +
                (b - y)*(b - y) + x*x);
    }
    private static double toN(double b, double r, double h, double x, double y){
        return Math.sqrt((r+h)*(r+h) +
                (b + y)*(b + y) + x*x);
    }

    private static double toA(double a, double r, double h, double x, double y){
        return Math.sqrt((r+h)*(r+h) +
                (a - y)*(a - y) + x*x);
    }

    private static double toB(double a, double r, double h, double x, double y){
        return Math.sqrt((r+h)*(r+h) +
                (a + y)*(a + y) + x*x);
    }

    private static double getCosinus(double toPoint, double toElectrode, double toPointElectrode){
        double value;
        value = toPoint*toPoint+toElectrode*toElectrode - toPointElectrode*toPointElectrode;
        value /= (2 * toPoint * toElectrode);
        return value;
    }


    private static double potentialInPointFromElectrode(double ro1, double ro2, double r, double toEl, double toPt, double cosElPt){
        double potentialValue;
        potentialValue = ro1/(Math.PI*toEl)*summaPoN(ro1, ro2, r, N, toPt, toEl, cosElPt);
        return potentialValue;
    }

    private static double summaPoN(double ro1, double ro2, double r, int n, double toPoint, double toElectrode, double cosinus){
        double value = 0;
        for(int i = 0; i < n; i++){
            value += underSumm(ro1, ro2, r, i, toPoint, toElectrode, cosinus);
        }
        return value;
    }
    private static double underSumm(double ro1, double ro2, double r, int n, double toPoint, double toElectrode, double cosinus){
        double value = 1;
        for(int i = 0; i < n; i++){
            value *= r*r/(toElectrode*toPoint);
        }
        value *= r/toPoint;
        value *= ((n*(ro2 - ro1))/(n*(ro2 + ro1) + ro2));
        value *= legandr(n,cosinus);
        return value;
    }

    private static double legandr(int n, double cosinus){
        double element = 1;
        double elementPrePre = 1;
        double elementPre = cosinus;
        for(int i = 2; i <= n; i++){
            element = (2*i - 1)* cosinus *elementPre - (i - 1)*elementPrePre;
            element /= i;
            elementPrePre = elementPre;
            elementPre = element;
        }
        if(n==1){
            return cosinus;
        }
        return element;
    }



}
