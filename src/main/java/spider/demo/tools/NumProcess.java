package spider.demo.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 用于去除小数点与多余的零
 *
 * @author jiauwu
 */
public class NumProcess {

    /**
     * 使用java正则表达式去掉多余的.与0，并只保留两位小数
     *
     * @param s
     * @return
     */
    public String subZeroAndDot (String s) {

        //i的值代表着要保留几位数。
        int i = 2;

        if (s.indexOf(".") > 0) {
            //去掉多余的0
            s = s.replaceAll("0+?$", "");

            //如最后一位是.则去掉
            s = s.replaceAll("[.]$", "");

        }

        if (s.indexOf(".") > 0) {
            if (s.length() - (s.indexOf(".") + 1) > i) {

                i = s.indexOf(".") + i + 1;
                s = s.substring(0, i);


            }

        }


        return s;
    }

    /**
     * 四舍五入。
     * @param f 保留两位。
     * @return
     */
    public double halfUp (double f) {
        BigDecimal b = new BigDecimal(f);
        f = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
        return f;
    }

    /**
     * 向上取整
     * @param f
     * @return
     */
    public double roundUp (double f) {
        BigDecimal b = new BigDecimal(f);
        f = b.setScale(2, BigDecimal.ROUND_UP).doubleValue();
        return f;
    }

    /**
     * 向下取整
     *
     */
    public double roundDowm (double f) {
        BigDecimal b = new BigDecimal(f);
        f = b.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        return f;
    }


}
