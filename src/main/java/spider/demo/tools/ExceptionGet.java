package spider.demo.tools;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * 获取异常的详细信息
 */
public class ExceptionGet {

    public static String getExcrptionInfo (Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        return baos.toString();

    }
}
