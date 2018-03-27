package spider.demo.domain.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * sf的Cookies实体类
 * @date 2018年3月27日
 * @author lanyubing
 */
public class SfCookie {

    private Map<String,String> SfCookie = new HashMap<>();

    public Map<String, String> getSfCookie () {
        return SfCookie;
    }

    public void setSfCookie (Map<String, String> sfCookie) {
        SfCookie = sfCookie;
    }
}
