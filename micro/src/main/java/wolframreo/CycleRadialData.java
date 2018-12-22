package wolframreo;

import java.util.Arrays;

public class CycleRadialData {

    double[] zBase;
    double[] dZ;
    double flBase;
    double dZfl;

    public CycleRadialData(double[] zBase, double[] dZ, double flBase, double dZfl) {
        this.zBase = zBase;
        this.dZ = dZ;
        this.flBase = flBase;
        this.dZfl = dZfl;
    }

    @Override
    public String toString(){
        return  " dZ: " + Arrays.toString(dZ) +
                " zase: " + Arrays.toString(zBase) +
                " dzFl: " + dZfl +
                " flBase: " + flBase;
    }

    public double[] getzBase() {
        return zBase;
    }

    public double[] getdZ() {
        return dZ;
    }

    public double getFlBase() {
        return flBase;
    }

    public double getdZfl() {
        return dZfl;
    }
}
