import com.ti.reo.SphereCalc2;
import wolframreo.TimeStampRadialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OneSphereOptimizator {
    private OptVariator variator;
    private TimeStampRadialData data;

    //    int xNStep = 100;
    //    int yNStep = 100;
//    int rNStep = 100;
//    double xStep = 0.1;
//    double yStep = 0.1;
//    double rStep = 0.1;
    boolean sqrtCreteria = true;

    //    Геометрические параметры
    double[][] atrialPoints;
    double[][] electrodSystemPoints;

    double[][] parameters;

    double[] equalSphereXYR;
    double[] zBeforeSystole;
    double dro1;

    public void setDro1(double dro1){
        this.dro1 = dro1;
    }

    public void setzBeforeSystole(double[] zBeforeSystole) {
        this.zBeforeSystole = zBeforeSystole;
    }
    public void setParameters(double[][] parametersByParam, double[] ro1, double ro2, double h) {
        double[][] parameters = new double[5][8];
        if(equalSphereXYR==null){
            System.out.println("Необходимо заполнить параметры сферы!!");
        }
        for (int i = 0; i < 5; i++) {
            parameters[i][0] = ro1[0];
            parameters[i][1] = ro2;
            parameters[i][2] = parametersByParam[0][i];
            parameters[i][3] = parametersByParam[1][i];
            parameters[i][4] = equalSphereXYR[2]/1000;
            //fixme Исправить жесткое задание h
            parameters[i][5] = h;
            parameters[i][6] = parametersByParam[4][i];
            parameters[i][7] = parametersByParam[5][i];
        }
        this.parameters = parameters;
    }
    public void setEqualSphereXYR(double[] equalSphereXYR){
        this.equalSphereXYR = equalSphereXYR;
    }
    public void setTimeStampRadialData(TimeStampRadialData data){
        this.data = data;
        //TODO 26.12.2018 Alexey: сделать расчет через использование параметров электродной системы для измерения первого слоя
        dro1 = data.getdZfl()*Math.PI*(0.06*0.06 - 0.03*0.03)/(2*0.03);
    }
    public void setHeartParameters(double[][] atrialPoints, double[][] electrodSystemPoints){
        this.atrialPoints = atrialPoints;
        this.electrodSystemPoints = electrodSystemPoints;
    }

    @Deprecated
    double[] dZexpererimental;
    @Deprecated
    public void setdZexpererimental(double[] dZexpererimental) {
        this.dZexpererimental = dZexpererimental;
    }

    public OptimizeElement optimize(){
        double artialThreshold = GeomUtils.pointsToCircleAvDistance(atrialPoints, equalSphereXYR);
//        System.out.println("pointsToCircleAvDistance()");
//        System.out.print("Atrial points: ");
//        Stream.of(atrialPoints).forEach(x-> System.out.print(Arrays.toString(x)));
//        System.out.println();
//        System.out.println("sphereXYR: " + Arrays.toString(equalSphereXYR));
//        System.out.println("AtrialThreshold:" + artialThreshold);
//        System.out.println();

//        System.out.println("SourceElements:");
        List<OptimizeElement> list = createOptimizeElements(sourceVariants());
//        System.out.println("SourceElements SIZE:  " + list.size());

//        System.out.println("Filter artialDistanceElements:");
        List<OptimizeElement> artialDistanceElements = list.stream()
                .map(this::fillAtrialDistance)
                .filter(x -> (x.atrialDistance2 < artialThreshold))
//                .peek(x-> System.out.print("AT   "+x.toStringXYZR(equalSphereXYR)) )
//                .peek(x-> System.out.print("FE "+x.toString()) )
                .collect(Collectors.toList())
                ;
//        System.out.println("Filter SIZE: " + artialDistanceElements.size());


//        System.out.println("Sorted:");
        zBeforeSystole = getZbeforeSystole();
        List<OptimizeElement> optimizeElementList = artialDistanceElements.stream().parallel()
                .map(this::getOptimizeDelta)
//                .peek(x -> System.out.print("AT   "+x.toString()))
                .sorted(Comparator.comparingDouble(x -> x.delta))
//                .peek(x-> System.out.print("S " + x.toString()))
                .collect(Collectors.toList())
                ;
//        System.out.println("Sorted SIZE: " + optimizeElementList.size());

//        System.out.println("Minimum:");
        OptimizeElement optimumElement = optimizeElementList.stream().parallel()
                .min(Comparator.comparingDouble(x -> x.delta)).get();
        System.out.print(optimumElement.toString());
        return optimumElement;
    }

    OptimizeElement fillAtrialDistance(OptimizeElement source){
        return source.setAtrialDistance2(
                GeomUtils.pointsToCircleAvDistance(atrialPoints,GeomUtils.sphereModify(equalSphereXYR, new double[]{source.dx,source.dy,source.dr}))
        );
    }

//    private double getOptimizeDelta(double[] zAfterSystole){
//        return IntStream.range(0, dZexpererimental.length)
//                .mapToDouble(i -> (zBeforeSystole[i] - zAfterSystole[i] - dZexpererimental[i]))
//                .map(x-> sqrtCreteria? x : x*x).sum();
//    }

    private OptimizeElement getOptimizeDelta(OptimizeElement element){
        double[] zAfterSystole = getZafterSystole(element);
        double delta = IntStream.range(0, data.getdZ().length)
                .mapToDouble(i -> (-zBeforeSystole[i] + zAfterSystole[i] - data.getdZ()[i]))
//                .peek(System.out::print)
                .map(x-> sqrtCreteria?  x*x : Math.abs(x)).sum();
        element.delta = delta;
        return element;
    }

    double[] getZafterSystole(OptimizeElement element){
        double[][] sphereCenterMove = VectorTests.sphereCenterMoveUpdate(electrodSystemPoints, element.dx, element.dy);
        double[][] listOfParams = IntStream.range(0, 5)
                //todo проверить sphereCenterMove меняются местами
                .mapToObj(i -> ReoMathUtils.paramXYRModifyCln(parameters[i], sphereCenterMove[i][1]/1000, sphereCenterMove[i][0]/1000, -element.dr/1000.))
                .map(p -> ReoMathUtils.paramRo1Modify(p, -dro1))
                .toArray(double[][]::new);
        double[] list = Arrays.stream(listOfParams)
//                .peek(p-> System.out.println("AA" + Arrays.toString(p)))
                .mapToDouble(SphereCalc2::getFullImpedance).toArray();
        return list;
    }
    double[] getZbeforeSystole(){
//        Stream.of(parameters).forEach(x-> System.out.println("Param: " + Arrays.toString(x)));
        double[] list = Stream.of(parameters).mapToDouble(SphereCalc2::getFullImpedance).toArray();
//        Stream.of(list).forEach(x-> System.out.println("zBeforeSystole" + Arrays.toString(list)));
        return list;
    }

//    List<OptimizeElement> createOptimizeElements(){
//        List<OptimizeElement> list = new ArrayList<>();
//        for (int dx = -xNStep; dx <= xNStep; dx++) {
//            for (int dy = -yNStep; dy < yNStep; dy++) {
//                for (int dr = -rNStep; dr < rNStep; dr++) {
//                    list.add(new OptimizeElement(xStep*dx, yStep*dy, rStep*dr));
//                }
//            }
//        }
//        return list;
//    }

    List<OptimizeElement> createOptimizeElements(double[][] source){
        return Stream.of(source).map(x-> new OptimizeElement(x[0], x[1], x[2])).collect(Collectors.toList());
    }
    List<OptimizeElement> createOptimizeElements(List<double[]> source){
        return source.stream().map(x-> new OptimizeElement(x[0], x[1], x[2])).collect(Collectors.toList());
    }
    List<double[]> sourceVariants(){
        return ReoMathUtils.arrayVariants(variator.getxNStep(), variator.getxStep(), true,
                                        variator.getyNStep(), variator.getyStep(), true,
                                        variator.getrNStep(), variator.getrStep(), false);
    }

    public void setVariator(OptVariator variator) {
        this.variator = variator;
    }
}
