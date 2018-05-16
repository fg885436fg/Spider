package spider.demo.domain.entity;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * 作者的Cookie 实体类
 *
 * @author lanyubing
 * @date 2018年3月27日
 */
public class AuthorCookie {

    public AuthorCookie(String authorName,  String authorSite) {
        this.authorName = authorName;
        this.authorSite = authorSite;
    }

    public AuthorCookie(String authorName, String cookieJson, String authorSite) {
        this.authorName = authorName;
        this.cookieJson = cookieJson;
        this.authorSite = authorSite;
    }

    private String authorName;
    public String getAuthorName () {
        return authorName;
    }
    public void setAuthorName (String authorName) {
        this.authorName = authorName;
    }

    /**
     * json格式的cookieJson
     */
    private String cookieJson;
    public String getCookieJson () {
        return cookieJson;
    }
    public void setAuthorCookieMap (Map<String, String> authorCookieMap) {
        this.cookieJson = JSON.toJSONString(authorCookieMap);;
    }

    private String authorSite;
    public String getAuthorSite () {
        return authorSite;
    }
    public void setAuthorSite (String authorSite) {
        this.authorSite = authorSite;
    }
}
