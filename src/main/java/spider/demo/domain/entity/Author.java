package spider.demo.domain.entity;

/**
 * 作者的实体类，包含他们所写的书
 *
 * @author lanyubing
 * @date 2018年2月2日
 */
public class Author {

    private long id;

    public long getId () {
        return id;
    }

    public void setId (long id) {
        this.id = id;
    }

    private String authorName;

    public String getAuthorName () {
        return authorName;
    }

    public void setAuthorName (String authorName) {
        this.authorName = authorName;
    }

    private String bookName;

    public String getBookName () {
        return bookName;
    }

    public void setBookName (String bookName) {
        this.bookName = bookName;
    }

    private String url;

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }
}
