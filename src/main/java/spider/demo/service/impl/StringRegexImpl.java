package spider.demo.service.impl;

import org.springframework.stereotype.Service;
import spider.demo.service.StringRegex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: lanyubing
 * @create: 2018-06-12 17:36
 **/
@Service
public class StringRegexImpl implements StringRegex {


    @Override
    public String returnRegexString(String regexRule, String words) {
        Pattern mPattern = Pattern.compile(regexRule);
        Matcher mMatcher = mPattern.matcher(words);
        while (mMatcher.find()) {
            return mMatcher.group(1);
        }
        return null;
    }

    @Override
    public boolean checkString(String regexRule, String words) {
        return Pattern.matches(regexRule,words);
    }
}
