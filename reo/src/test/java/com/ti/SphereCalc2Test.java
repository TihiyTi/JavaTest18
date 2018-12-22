package com.ti;

import com.ti.reo.SphereCalc2;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class SphereCalc2Test {

    @Test
    public void getFullImpedance() {
        double res = SphereCalc2.getFullImpedance(3,1.35, 0.04,0.02, 0.042,0.02, 0,0.035);
        assertEquals(30.3645, res, 0.001);
    }
    @Test
    public void timeCalc2Test() throws Exception {
        long start_time = System.nanoTime();
        List<Double> res = Stream.iterate(0, n-> n+1).limit(1000000).parallel().
                map(x -> SphereCalc2.getFullImpedance(3 + 0.001*x,1.35, 0.04,0.02, 0.042,0.02, 0,0.035)).
                collect(Collectors.toList());
        long end_time = System.nanoTime();

        double difference = (end_time - start_time) / 1e9;
        System.out.println(difference);
        System.out.println(res.size());
    }
}