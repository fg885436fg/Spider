package spider.demo.tools;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Get {

    public static String getExcrptionInfo (Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        return baos.toString();

    }
}
