package spider.demo.tools;

import org.apache.http.util.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author lanyubing
 * @date 2018年3月23日
 */
public class DateUtil {

    /**
     * 获取符合sf结算网址日期的当前格式。
     * 示例：3-2018
     *
     * @return
     */
    public String getSfDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-YYYY");
        return formatter.format(now);
    }

    /**
     * 返回当前时间（date类型）
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public Date getNowDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateStr = formatter.format(now);
        return dateStrConvertDate(dateStr);
    }

    /**
     * 返回任意格式的当前时间（date类型）
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public Date getNowFreeFormatterDate(String formatterStr) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterStr);
        String dateStr = formatter.format(now);
        return dateStrConvertDate(dateStr,formatterStr);
    }

    /**
     * 将日期字符串转换为时间，格式为（yyyy-MM-dd HH:mm:ss）
     *
     * @param dateStr
     * @return
     */

    public Date dateStrConvertDate(String dateStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将日期字符串转换为时间，格式自定义(如：yyyy-MM-dd HH:mm:ss)
     *
     * @param dateStr
     * @return
     */

    public Date dateStrConvertDate(String dateStr, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取随意格式的当日期字符串。
     * 可通过days 属性，获得当日以前X天的日期
     *
     * @param format 格式，例如：MM dd yyyy
     * @param days   今日前days天
     * @return
     */
    public String getAnyNowDate(String format, long days) {
        LocalDate now = LocalDate.now();
        now = now.minusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(now);
    }

    /**
     * 获取随意格式的当日期字符串。
     * 可通过days 属性，获得当日以前X月的日期
     *
     * @param format 格式，例如：MM -dd-yyyy
     * @param mons   今日前×月
     * @return
     */
    public String getAnyMonDate(String format, int mons) {
        LocalDate now = LocalDate.now();
        now = now.minusMonths(mons);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(now);
    }

    /**
     * 得到两个日期之间的日期差。
     *
     * @param str1 yyyy-MM-dd 格式 例如 2017-01-01 2017-1-1
     * @param str2
     * @return
     */
    public long getDistanceDays(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265";
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (TextUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

}
