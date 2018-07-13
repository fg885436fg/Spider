package spider.demo.domain.entity;

/**
 * 作者的实体类，包含他们所写的书
 *
 * @author lanyubing
 * @date 2018年2月2日
 */
public class Author {

    private long id;
    private String authorName;
    private String bookName;
    private String url;
    /**
     * 1表示爬取
     * 0表示不爬取
     */
    private int right;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
