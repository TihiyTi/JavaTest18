import com.ti.check.CheckRuleBuilder;
import com.ti.check.MultyContainCheck;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class SimpleFuzzyTest {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println(FuzzySearch.ratio("this is a test", "this is a test!"));
        List<List<String>> rules = CheckRuleBuilder.ruleBuild("");
        Stream<String> stream = Files.lines(Paths.get(Objects.requireNonNull(SimpleFuzzyTest.class.getClassLoader().getResource("lpu.txt")).toURI()));
//        stream.filter(x-> HospitalType.GP.equals(findHospitalType(x))).forEach(System.out::println);
        stream.map(x-> (Hospital.canonicalNameStatic(x) +" ___ "+ x)).sorted().forEach(System.out::println);

    }






}
