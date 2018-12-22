import org.junit.Ignore;
import org.junit.Test;
import wolframbridge.WolframBridge;
import wolframreo.CycleRadialData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class OneSphereOptimizatorTest {

    @Test
    public void optimizeTest(){
        OneSphereOptimizator optimizator = new OneSphereOptimizator();

//        test createOptimizeElements()
        optimizator.xNStep = 7;
        optimizator.yNStep = 7;
        optimizator.rNStep = 7;
        optimizator.xStep = 0.2;
        optimizator.yStep = 0.2;
        optimizator.rStep = 0.2;

        List<OptimizeElement> optimizeElements = optimizator.createOptimizeElements();
        System.out.println(optimizeElements.toString());
    }

    @Ignore
    @Test
    public void getOptimizeDeltaTest(){
        String name = "Artem";
        double[] equalSphere = new double[]{ 15, -6, 52 };
        CycleRadialData radialData = WolframBridge.w_GetRadial(name);
        OneSphereOptimizator optimizator = new OneSphereOptimizator();
        //        test createOptimizeElements()
        optimizator.xNStep = 7;
        optimizator.yNStep = 7;
        optimizator.rNStep = 7;
        optimizator.xStep = 0.2;
        optimizator.yStep = 0.2;
        optimizator.rStep = 0.2;
        optimizator.parameters = WolframBridge.w_NewGetParam(name);
        optimizator.electrodSystemPoints = WolframBridge.w_ElectrodSystemPoints(name);
        optimizator.atrialPoints = WolframBridge.w_AtrialPoints(name);
        optimizator.dZexpererimental = radialData.getdZ();
        optimizator.setEqualSphereXYR(equalSphere);
        optimizator.setParameters(WolframBridge.w_NewGetParam(name), new double[]{4.2,4.2,4.2,4.2,4.2}, 1.35);
        optimizator.dro1 = 0.0142459;
        optimizator.sqrtCreteria = true;

        System.out.println("Complete configuration");
        optimizator.optimize();

        double[][] atrialPoints = new double[][]{{-2., 35.}, {-31., 32.}, {-38., 0.}};
        double[][] parameters = new double[][]{
                {4.2, 1.35, 0.04, 0.02, /*0.037*/ 0.052, /*0.031*/0.03, 0.014, 0.044/*0.048*/},
                {4.2, 1.35, 0.04, 0.02, /*0.032*/0.052, /*0.025*/0.03, 0.013, 0.046/*0.047*/},
                {4.2, 1.35, 0.05, 0.025, /*0.047*/0.052, /*0.024*/0.03, 0.003, 0.03},
                {4.2, 1.35, 0.05, 0.025, /*0.039*/0.052, /*0.025*/0.03, -0.004, 0.061},
                {4.2, 1.35, 0.04, 0.02, /*0.037*/0.052, /*0.03*/0.03, -0.014, 0.031}};
        double[] dialtoleSphere = new double[]{15,-6,52};

        double[][] contour = new double[][]{/*{-39, -22}, */{-37, -27}, {12, -47}, {67, -42}, {76, -11}, {41, 29}/*, {31, 32}*/};



        IntStream.range(0,3).forEach(i -> assertArrayEquals(atrialPoints[i], optimizator.atrialPoints[i], 0.1 ));
        IntStream.range(0,5).forEach(i -> assertArrayEquals(parameters[i], optimizator.parameters[i], 0.001 ));
        assertArrayEquals(dialtoleSphere, optimizator.equalSphereXYR, 0.01);


//        assertEquals(118.548, optimizator., 0.01);

//        IntStream.range(0,5).forEach(i -> assertArrayEquals(contour[i], optimizator.electrodSystemPoints[i], 0.1 ));

//        IntStream.range(0,5).forEach(i -> assertArrayEquals(parameters[i], optimizator.parameters[i], 0.1 ));

    }

//    @Test
//    public void getZafterSystoleTest(){
//        OneSphereOptimizator optimizator = new OneSphereOptimizator();
//        OptimizeElement element = new OptimizeElement(0.,0.,0.);
//        double[] zAfterSystole = optimizator.getZafterSystole(element);
//
//    }

//    REAL test
    @Test
    public void sourceVariants(){
        List<double[]> list = ReoMathUtils.arrayVariants(1, 0.2, true, 1, 0.2, true, 1, 0.2, false);
        list.forEach(x-> System.out.println(Arrays.toString(x)));
        System.out.println(list.size());
    }
    @Test
    public void test(){
        int xNOfStep = 1; double xStep = 0.2; boolean xBidirectional = true;
        int yNOfStep = 1; double yStep = 0.2; boolean yBidirectional = true;
        int zNOfStep = 1; double zStep = 0.2; boolean zBidirectional = false;
        double[] array = IntStream.iterate(xBidirectional ? (-(int)(xNOfStep/xStep)) : 0, i->i+1).limit(xBidirectional ? (2*(long)(xNOfStep/xStep)+1) : (long)(xNOfStep/xStep)+1)
                .mapToDouble(x-> x*xStep).toArray();
        System.out.println(Arrays.toString(array));
    }

}