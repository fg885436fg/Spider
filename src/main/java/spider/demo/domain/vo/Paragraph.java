package spider.demo.domain.vo;

/**
 * 段落实体类
 *
 * @author: lanyubing
 * @create: 2018-06-17 17:39
 **/
public class Paragraph {

    private String content;
    /**
     * 段落的索引
     */
    private int index;

    public Paragraph(String content, int index) {
        this.content = content;
        this.index = index;
    }

    public Paragraph() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
