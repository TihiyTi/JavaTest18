import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleFuzzyTestTest {
    @Test
    public void test(){
        Hospital a = new Hospital(Hospital.HospitalType.GP, 0);
        Hospital b = new Hospital(Hospital.HospitalType.GP, 1);
        System.out.println(a.equals(b));
        System.out.println(b.equals(a));
    }

}