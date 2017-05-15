import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Valery on 14/05/2017.
 */
public class UnitTestGenerator {

    private final Set<String> objectMethods;
    private final String UNIT_TEST_FOLDER;

    public UnitTestGenerator(String unitTestFolder) {
        UNIT_TEST_FOLDER = unitTestFolder;

        objectMethods = new HashSet<>();
        objectMethods.add("wait");
        objectMethods.add("equals");
        objectMethods.add("toString");
        objectMethods.add("hashCode");
        objectMethods.add("getClass");
        objectMethods.add("notify");
        objectMethods.add("notifyAll");
    }

    public void generate(String classFolder, int cyclomaticComplexity) throws IOException, ClassNotFoundException {
        List<String> classNames = getClassNames(classFolder);
        int complexity = getCyclomaticComplexityForFolder(classNames, cyclomaticComplexity);

        classNames.forEach(className -> {
            try {
                createUnitTestSetForClass(className, complexity);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private int getCyclomaticComplexityForFolder(List<String> classNames, int cyclomaticComplexity) {
        int methods = 0;
        for (String className: classNames) {
            try {
                methods += Class.forName(className).getMethods().length;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return cyclomaticComplexity/methods;
    }

    private List<String> getClassNames(String pathFolder) throws IOException {
        return Files.walk(Paths.get(pathFolder))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .map(File::getName)
                .map(FilenameUtils::getBaseName)
                .collect(Collectors.toList());
    }

    private Set<String> getClassMethods(String className) throws ClassNotFoundException {
        return Stream.of(Class.forName(className).getMethods())
                .map(Method::getName)
                .filter(methodName -> !objectMethods.contains(methodName))
                .collect(Collectors.toSet());
    }

    private void createUnitTestSetForClass(String className, int complexity) throws IOException, ClassNotFoundException {
        File file = new File(UNIT_TEST_FOLDER + File.separator + className + "Test.java");
        generateUnitTests(file, getClassMethods(className), complexity);
    }

    private void generateUnitTests(File unitTestsFolder, Set<String> classMethods, int complexity) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(unitTestsFolder));
        StringBuilder builder = new StringBuilder();
        builder.append("import org.junit.Test;\n\n");
        builder.append("public class ");
        builder.append(FilenameUtils.getBaseName(unitTestsFolder.getName()) + " {\n\n");

        classMethods.forEach(method -> {
            if (complexity != 0) {
                for (int i = 0; i < complexity; i++) {
                    builder.append("     @Test\n");
                    builder.append("     public void test" + method + i + "()" + " {\n\n");
                    builder.append("     } \n \n");
                }
            } else {
                builder.append("     @Test\n");
                builder.append("     public void test" + method + "()" + " {\n\n");
                builder.append("     } \n \n");
            }
        });
        builder.append(" }");

        osw.write(String.valueOf(builder));
        osw.flush();
        osw.close();
    }

}
