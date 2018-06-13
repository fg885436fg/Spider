package spider.demo.service;

/**
 * 返回字符串中符合正则公式的字符
 *
 * @author: lanyubing
 * @create: 2018-06-12 17:26
 **/
public interface StringRegex {

    /**
     * 寻找违禁词（也就是**）
     */
    String FIND_FORBIDDEN_REGEX = ".*\\*.*";

    /**
     * 返回符合正则表达式的字符串
     *
     * @param regexRule 正则表达式规则
     * @param words     检测字符
     * @return
     */
    String returnRegexString(String regexRule, String words);

    /**
     * 检查字符串中是否有符合正则表达式的字符，若有则返回true
     *
     * @param regexRule 正则表达式
     * @param words     检测字符
     * @return
     */
    boolean checkString(String regexRule, String words);

}
