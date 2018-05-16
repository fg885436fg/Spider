package spider.demo.config;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义的mybatis注解语言，
 * 使用场景：
 * @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select("SELECT * FROM users WHERE id IN (#{userIds})")
    List<User> selectUsers(@Param("userIds") List<String> userIds);
    @date 2018年3月21日
    @author lanyubing
 */
public class SimpleSelectInExtendedLanguageDriver
        extends XMLLanguageDriver implements LanguageDriver {
    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration,
                                     String script, Class<?> parameterType) {
        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            script = matcher.replaceAll("(<foreach collection=\"$1\" item=\"__item\" separator=\",\" >#{__item}</foreach>)");
        }
        script = "<script>" + script + "</script>";
        return super.createSqlSource(configuration, script, parameterType);
    }
}

