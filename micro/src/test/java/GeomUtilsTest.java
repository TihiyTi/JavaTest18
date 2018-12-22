import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class GeomUtilsTest {
    @Test
    public void pointToCircleDistance(){
        double dis = GeomUtils.pointToCircleDistance(-2, 35, 15, -6, 52);
        assertEquals(7.61532, dis, 0.00001);
    }
    @Test
    public void pointsToCircleAvDistance_1(){
        double[][] points = new double[][]{{-2,35},{-31,32},{-38,0}};
        double dis = GeomUtils.pointsToCircleAvDistance(points, 15, -6, 52);
        assertEquals(118.548, dis, 0.001);
    }
    @Test
    public void pointsToCircleAvDistance_2(){
        double[][] points = new double[][]{{-2,35},{-31,32},{-38,0}};
        double[] sphereXYR = new double[]{15,-6,52};
        double dis = GeomUtils.pointsToCircleAvDistance(points, sphereXYR);
        assertEquals(118.548, dis, 0.001);
    }

}