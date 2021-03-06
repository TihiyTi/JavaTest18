package com.ti;

import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class SphereCalcTest{

    @Test
    @Ignore
    public void testGetMeasurement() throws Exception {
        SphereModelParam param = new SphereModelParam(3,1.35, 0.04,0.02, 0.042,0.02, 0,0.035);
        SphereModelSimple model = new SphereModelSimple(param);
        OneLayerModelSimple oneModel = new OneLayerModelSimple(0.06, 0.03);
        assertEquals(-1.467, model.getImpedance(), 0.001);
        assertEquals(30.3645, model.getFullImpedance(), 0.001);
        assertEquals(30.53, model.setR(0.037).getFullImpedance(), 0.001);

        ReoProcessor proc = new ReoProcessor(model,true);
        assertEquals(0.0002, proc.getDeltaRadius(0.006), 0.0001);
        assertEquals(-0.00086, proc.getDeltaRadius(-0.02752), 0.00001);
        assertEquals(-0.00103, proc.getDeltaRadius(-0.033), 0.00001);


        List<Double> listOfDeltaZ = new ArrayList<>();
        listOfDeltaZ.add(0.006);
        listOfDeltaZ.add(-0.02752);
        List<Double> listOfDeltaFirst = new ArrayList<>();
        listOfDeltaFirst.add(-0.051);
        listOfDeltaFirst.add(0.039);

        List<Double> listOfRad = proc.getDeltaRadiusList(listOfDeltaZ, ReoProcessor.OMH);
        List<Double> listOfRo = oneModel.getDeltaRoList(listOfDeltaFirst, OneLayerModelSimple.OMH);
//        System.out.println(listOfRad.toString());

//        System.out.println(oneModel.getDeltaRo(39 + 51));

        assertEquals(30.433, model.setRoTissue(3+0.0072).getFullImpedance(), 0.0001);
        assertEquals(30.3122, model.setRoTissue(3 - 0.0055).getFullImpedance(), 0.0001);
        assertEquals(30.2437, model.setRoTissue(3-0.0127).getFullImpedance(), 0.0001);

        System.out.println(proc.getDeltaRadius(-0.0435));
        System.out.println(proc.getDeltaRadius(-0.0435, oneModel.getDeltaRo(39 + 51)));

        System.out.println(oneModel.getDeltaRo(-51));
        System.out.println(oneModel.getDeltaRo(39));
        System.out.println(proc.getDeltaRadius(0.006, oneModel.getDeltaRo(-51)));
        System.out.println(proc.getDeltaRadius(-0.027, oneModel.getDeltaRo(39)));

        System.out.println(proc.getDeltaRadiusList(listOfDeltaZ,listOfRo, ReoProcessor.OMH).toString());

        param = new SphereModelParam(3.51,1.35, 0.04,0.02, 0.051,0.03, 0,0.047);
        model = new SphereModelSimple(param);
        proc = new ReoProcessor(model,true);
        System.out.println(proc.getDeltaRadius(-0.047, oneModel.getDeltaRo(25)));


//        assertEquals(-0.005, proc.getDeltaRadius(0.542), 0.0001);

//        assertEquals(-0.01, proc.getDeltaRadius(1.004, 0.1), 0.0001);
//        assertEquals(-0.01, proc.getDeltaRadius(0.883, 0.2), 0.0001);

//        System.out.println(proc.getDeltaRadius(0.0, 0.2));
//        System.out.println(proc.getDeltaRadius(0.02, 0.2));
//        System.out.println(proc.getDeltaRadius(0.3, 0.2));
//        SphereModelParam param1 = new SphereModelParam(3.51,1.35, 0.04,0.02, 0.051,0.03, 0,0.047);
//        SphereModelSimple model1 = new SphereModelSimple(param1);
//
//        System.out.println(model1.getFullImpedance());
//        System.out.println(model1.setRoTissue(3.51 - 0.018).setR(0.051 - 0.0072).getFullImpedance());
//        ReoProcessor proc1 = new ReoProcessor(model1);
//        System.out.println(proc1.getDeltaRadius(-0.061, -0.018));

    }
    @Ignore
    @Test
    public void timeCalcObjectTest() throws Exception {

        long start_time = System.nanoTime();
        SphereModelParam param = new SphereModelParam(3,1.35, 0.04,0.02, 0.042,0.02, 0,0.035);
        SphereCalc calc = new SphereCalc(param);
        List<Double> res = Stream.iterate(0, n-> n+1).limit(1000000).parallel().
                    map(x -> calc.setParam(new SphereModelParam(3 + 0.001*x,1.35, 0.04,0.02, 0.042,0.02, 0,0.035)).getMeasurementFullImp()).
                    collect(Collectors.toList());
        long end_time = System.nanoTime();

        double difference = (end_time - start_time) / 1e9;
        System.out.println(difference);
        System.out.println(res.size());
    }
    @Ignore
    @Test
    public void timeCalcTest() throws Exception {

        long start_time = System.nanoTime();
        SphereCalc calc = new SphereCalc();
        List<Double> res = Stream.iterate(0, n-> n+1).limit(1000000).parallel().
                map(x -> calc.getMeasurementFullImp(3 + 0.001*x,1.35, 0.04,0.02, 0.042,0.02, 0,0.035)).
                collect(Collectors.toList());
        long end_time = System.nanoTime();

        double difference = (end_time - start_time) / 1e9;
        System.out.println(difference);
        System.out.println(res.size());
    }
}