package spider.demo.domain.entity;

/**
 * 用于传到前端的，数据增长量VO
 *
 * @author lanyubing
 * @date 2018年2月3日
 */
public class GrowthData {


    /**
     * 所属书籍
     */
    private String bookName;

    public String getBookName () {
        return bookName;
    }

    public void setBookName (String bookName) {
        this.bookName = bookName;
    }

    /**
     * 所属的日期。
     */
    private String date;

    public String getDate () {
        return date;
    }

    public void setDate (String date) {
        this.date = date;
    }

    /**
     * 收藏增长量
     */
    private long collectNumInc;

    public long getCollectNumInc () {
        return collectNumInc;
    }

    public void setCollectNumInc (long collectNumInc) {
        this.collectNumInc = collectNumInc;
    }


    private long clictNumInc;

    public long getClictNumInc () {
        return clictNumInc;
    }

    public void setClictNumInc (long clictNumInc) {
        this.clictNumInc = clictNumInc;
    }


    private long monthlyNumInc;

    public long getMonthlyNumInc () {
        return monthlyNumInc;
    }

    public void setMonthlyNumInc (long monthlyNumInc) {
        this.monthlyNumInc = monthlyNumInc;
    }

    /**
     * 赞数增长量
     */
    private long likeNumInc;

    public long getLikeNumInc () {
        return likeNumInc;
    }

    public void setLikeNumInc (long likeNumInc) {
        this.likeNumInc = likeNumInc;
    }

    /**
     * 字数增长量
     */

    private long wordNumInc;

    public long getWordNumInc () {
        return wordNumInc;
    }

    public void setWordNumInc (long wordNumInc) {
        this.wordNumInc = wordNumInc;
    }

    /**
     * 书籍标签
     */
    private  String sign;

    public String getSign () {
        return sign;
    }

    public void setSign (String sign) {
        this.sign = sign;
    }


    /**
     * 书籍更新日期
     */
    private String updateDay;

    public String getUpdateDay () {
        return updateDay;
    }

    public void setUpdateDay (String updateDay) {
        this.updateDay = updateDay;
    }




}
