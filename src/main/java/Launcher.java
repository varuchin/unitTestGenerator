import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Valery on 14/05/2017.
 */
public class Launcher {
//
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        UnitTestGenerator unitTestGenerator = new UnitTestGenerator("C:\\Users\\Valery\\workspace\\scopio\\1.2\\core\\codebase\\Unit test generator\\src\\test\\java");
//        unitTestGenerator.generate("C:\\Users\\Valery\\workspace\\scopio\\1.2\\core\\codebase\\Unit test generator\\src\\main\\java", 80);
//    }

    public static void main(String[] args) throws IOException {
        Generator generator = new Generator();
        HashMap<String, Integer> methodsWithComplexity = new HashMap<>();

        methodsWithComplexity.put("firstMethod", 1);
        methodsWithComplexity.put("secondMethod", 2);

        generator.generateUnitTests("D:\\repository\\tests", methodsWithComplexity);
    }
}

