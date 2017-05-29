import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class Generator {

    public void generateUnitTests(String path, HashMap<String, Integer> methodshWithCyclomaticComplexity) throws IOException {
        File file = new File(path + "/unitTests.java");
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
        StringBuilder builder = new StringBuilder();

        builder.append("import org.junit.Test;\n\n");
        builder.append("public class ");
        builder.append(FilenameUtils.getBaseName(file.getName()) + " {\n\n");

        methodshWithCyclomaticComplexity.forEach((method, complexity) -> {
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
