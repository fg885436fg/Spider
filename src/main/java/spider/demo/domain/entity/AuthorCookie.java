package spider.demo.domain.entity;

/**
 * 作者的Cookie 实体类
 *
 * @author lanyubing
 * @date 2018年3月27日
 */
public class AuthorCookie {

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

    public void setCookieJson (String cookieJson) {
        this.cookieJson = cookieJson;
    }



    private String authorSite;

    public String getAuthorSite () {
        return authorSite;
    }

    public void setAuthorSite (String authorSite) {
        this.authorSite = authorSite;
    }
}
