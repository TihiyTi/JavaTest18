package wolframbridge;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import wolframreo.CycleRadialData;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class WolframBridge {
    public static String path = "-linkmode launch -linkname 'C:/Program Files/Wolfram Research/Mathematica/11.3/MathKernel.exe'";
    public static KernelLink wolframKernel = null;

    public static void main(String[] args) {
        initKernel();
//        w_GetRadial("Alex");
    }

    public static double[][] w_AtrialPoints(String name){
        try {
            doAndWait("AtrialPoints[\""+name+"\"]");
            return wolframKernel.getDoubleArray2();
        } catch (MathLinkException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static double[][] w_GetParam(String name){
        return Stream.of("a","b","R","h","x","y").map(x-> getDoubleArray("GetParam[\""+name+"\"][\""+x+"\"]")).toArray(double[][]::new);
    }
    public static double[][] w_NewGetParam(String name){
        return Stream.of("a","b","R","h","x","y").map(x-> getDoubleArray("GetNewParam[\""+name+"\"][\""+x+"\"]")).toArray(double[][]::new);
    }
    public static double[][] w_HeartContour(String name){
        try {
            doAndWait("HeartContours[\""+name+"\"]");
            return wolframKernel.getDoubleArray2();
        } catch (MathLinkException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static double[][] w_ElectrodSystemPoints(String name){
        return Arrays.stream(Objects.requireNonNull(w_HeartContour(name))).skip(1).limit(5).toArray(double[][]::new);
    }
    public static CycleRadialData w_GetRadial(String name){
        double[] dZ = getDoubleArray("GetRadial[\""+name+"\"][\"dZRad\"]");
        double[] zBase = getDoubleArray("GetRadial[\""+name+"\"][\"zBase\"]");
        double flBase = getDouble("GetRadial[\""+name+"\"][\"flBase\"]");
        double dZfl = getDouble("GetRadial[\""+name+"\"][\"flDZ\"]");
        return new CycleRadialData(zBase,dZ,flBase,dZfl);
    }



    private static void doAndWait(String evaluate) throws MathLinkException {
        if(wolframKernel == null){initKernel();}
        wolframKernel.evaluate(evaluate);
        wolframKernel.waitForAnswer();
    }
    private static double[] getDoubleArray(String exp){
        if(wolframKernel == null){initKernel();}
        try {
            wolframKernel.evaluate(exp);
            wolframKernel.waitForAnswer();
            return wolframKernel.getDoubleArray1();
        } catch (MathLinkException e) {
            System.out.println("Fatal error opening link: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    private static double getDouble(String exp){
        if(wolframKernel == null){initKernel();}
        try {
            wolframKernel.evaluate(exp);
            wolframKernel.waitForAnswer();
            return wolframKernel.getDouble();
        } catch (MathLinkException e) {
            System.out.println("Fatal error opening link: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    private static void initKernel(){
        try {
            wolframKernel = MathLinkFactory.createKernelLink(path);// подключаем ядро
            wolframKernel.discardAnswer();// дожидаемся загрузки ядра
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

//    public static void testArray() throws MathLinkException {
//        wolframKernel.evaluate("{{1,2,3,4},{2,3,4,5}}");
//        wolframKernel.waitForAnswer();
//        int[][] res = wolframKernel.getIntArray2();
//        Stream.of(res).forEach(x-> System.out.println(Arrays.toString(x)));
//    }
}
