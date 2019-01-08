import org.junit.Test;
import reodata.ReoTempData;
import wolframbridge.WolframBridge;
import wolframreo.SignalRadialData;
import wolframreo.TimeStampRadialData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.Assert.assertArrayEquals;

public class OptimizatorManagerTest {

    @Test
    public void optimize(){
        String name = "Artem";
        double[] equalSphere = new double[]{ 15, -6, 52 };
        TimeStampRadialData radialData = WolframBridge.w_GetRadialPoint(name);
        SignalRadialData signalData = WolframBridge.w_SignalRadialData(name);

//        OptimizatorManager mng = new OptimizatorManager(OptVariator.VariatorType.TEST);
        OptimizatorManager mng = new OptimizatorManager();
        mng.setHeartParameters(WolframBridge.w_AtrialPoints(name) ,WolframBridge.w_ElectrodSystemPoints(name));
        mng.setExperimentalData(signalData);
        mng.setEqualSphere(equalSphere);
        mng.setModelParameters(WolframBridge.w_NewGetParam(name), new double[]{4.2,4.2,4.2,4.2,4.2}, 1.35, 0.03);

//        OptimizeElement optimum = mng.optimizeByTimeStamp(radialData);
//        assertArrayEquals(new double[]{-4.6, 2.4, 0, 6.6}, optimum.getAsArray(), 0.01);

        OptimizeElement optimum2 = mng.optimizeByTimeStamp(signalData.getCycleRadialData().get(0).getDataAtTime(8));
        System.out.println(optimum2.toString());

//        System.out.println("METHOD 1:");
//        List<OptimizeElement> list = mng.optimizeByCycle(signalData.getCycleRadialData().get(0));
//        list.forEach(x-> System.out.print(x.toString()));

//        List<OptimizeElement> list = Arrays.asList(optimum,optimum);



//        List<List<OptimizeElement>> listOfList = mng.optimize();
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
//        try {
//            Files.write(
//                    Paths.get(name + " " + simpleDateFormat.format(new Date())+".txt"),
//                    (Iterable<String>)listOfList.stream()
//                            .flatMap(s-> Stream.concat(Stream.of("New CYCLE:"),s.stream().map(OptimizeElement::toLog)))::iterator,
//                    CREATE, WRITE
//                    );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void optimizeAlex(){
        String name = "Alex";
        double[] equalSphere = ReoTempData.getEqualSphereAlex();
        SignalRadialData signalData = WolframBridge.w_SignalRadialData(name);
//        OptimizatorManager mng = new OptimizatorManager(OptVariator.VariatorType.TEST);
        OptimizatorManager mng = new OptimizatorManager();
        mng.setHeartParameters(WolframBridge.w_AtrialPoints(name) ,WolframBridge.w_ElectrodSystemPoints(name));
        mng.setExperimentalData(signalData);
        mng.setEqualSphere(equalSphere);
        mng.setModelParameters(WolframBridge.w_NewGetParam(name), new double[]{4.2,4.2,4.2,4.2,4.2}, 1.35, 0.037);

//        OptimizeElement optimum = mng.optimizeByTimeStamp(radialData);
//        assertArrayEquals(new double[]{-4.6, 2.4, 0, 6.6}, optimum.getAsArray(), 0.01);

//        System.out.println("GET ONE TIMESTAMP");
//        OptimizeElement optimum2 = mng.optimizeByTimeStamp(signalData.getCycleRadialData().get(0).getDataAtTime(8));
//        System.out.println(optimum2.toString());

//        System.out.println("GET ONE CYCLE");
//        List<OptimizeElement> list = mng.optimizeByCycle(signalData.getCycleRadialData().get(0));
//        list.forEach(x-> System.out.print(x.toString()));

        System.out.println("GET SIGNAL");
        List<List<OptimizeElement>> listOfList = mng.optimize();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
        try {
            Files.write(
                    Paths.get(name + " " + simpleDateFormat.format(new Date())+".txt"),
                    (Iterable<String>)listOfList.stream()
                            .flatMap(s-> Stream.concat(Stream.of("New CYCLE:"),s.stream().map(OptimizeElement::toLog)))::iterator,
                    CREATE, WRITE
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void optimizeIvan(){
        String name = "Ivan";
        double[] equalSphere = ReoTempData.getEqualSphereIvan();
        SignalRadialData signalData = WolframBridge.w_SignalRadialData(name);
//        OptimizatorManager mng = new OptimizatorManager(OptVariator.VariatorType.TEST);
        OptimizatorManager mng = new OptimizatorManager();
        mng.setHeartParameters(WolframBridge.w_AtrialPoints(name) ,WolframBridge.w_ElectrodSystemPoints(name));
        mng.setExperimentalData(signalData);
        mng.setEqualSphere(equalSphere);
        mng.setModelParameters(WolframBridge.w_NewGetParam(name), new double[]{4.2,4.2,4.2,4.2,4.2}, 1.35, 0.02);

//        OptimizeElement optimum = mng.optimizeByTimeStamp(radialData);
//        assertArrayEquals(new double[]{-4.6, 2.4, 0, 6.6}, optimum.getAsArray(), 0.01);

//        System.out.println("GET ONE TIMESTAMP");
//        OptimizeElement optimum2 = mng.optimizeByTimeStamp(signalData.getCycleRadialData().get(0).getDataAtTime(8));

//        System.out.println("GET ONE CYCLE");
//        List<OptimizeElement> list = mng.optimizeByCycle(signalData.getCycleRadialData().get(0));

        System.out.println("GET SIGNAL");
        List<List<OptimizeElement>> listOfList = mng.optimize();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
        try {
            Files.write(
                    Paths.get(name + " " + simpleDateFormat.format(new Date())+".txt"),
                    (Iterable<String>)listOfList.stream()
                            .flatMap(s-> Stream.concat(Stream.of("New CYCLE:"),s.stream().map(OptimizeElement::toLog)))::iterator,
                    CREATE, WRITE
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}