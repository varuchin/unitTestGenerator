import java.io.IOException;

/**
 * Created by Valery on 14/05/2017.
 */
public class Launcher {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UnitTestGenerator unitTestGenerator = new UnitTestGenerator("C:\\Users\\Valery\\workspace\\scopio\\1.2\\core\\codebase\\Unit test generator\\src\\test\\java");
        unitTestGenerator.generate("C:\\Users\\Valery\\workspace\\scopio\\1.2\\core\\codebase\\Unit test generator\\src\\main\\java", 80);
    }
}
