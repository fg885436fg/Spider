package spider.demo.domain.vo;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * sf的Cookies实体类
 *
 * @author lanyubing
 * @date 2018年3月27日
 */
public class SfCookie {

    private Map<String, String> sfCookie = new HashMap<>();

    public Map<String, String> getSfCookie () {
        return sfCookie;
    }

    public void setSfCookie (String SfCookieJsonstr) {

        sfCookie = JSON.parseObject(SfCookieJsonstr, Map.class);
    }
}
