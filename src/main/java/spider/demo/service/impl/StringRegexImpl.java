package spider.demo.service.impl;

import org.springframework.stereotype.Service;
import spider.demo.service.StringRegex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: lanyubing
 * @create: 2018-06-12 17:36
 **/
@Service
public class StringRegexImpl implements StringRegex {

    public final static Map<String,String> URL_MAP = new HashMap();
    static {
        URL_MAP.put(FIND_SF_URL, "SF");
        URL_MAP.put(FIND_TEST_URL, "Test");
    }

    @Override
    public String returnRegexString(String regexRule, String words) {
        Pattern mPattern = Pattern.compile(regexRule);
        Matcher mMatcher = mPattern.matcher(words);
        while (mMatcher.find()) {
            return mMatcher.group(0);
        }
        return null;
    }

    @Override
    public boolean checkString(String regexRule, String words) {
        return Pattern.matches(regexRule,words);
    }
}
