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


    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GrowthData that = (GrowthData) o;

        if (collectNumInc != that.collectNumInc) return false;
        if (clictNumInc != that.clictNumInc) return false;
        if (monthlyNumInc != that.monthlyNumInc) return false;
        if (likeNumInc != that.likeNumInc) return false;
        if (wordNumInc != that.wordNumInc) return false;
        if (bookName != null ? !bookName.equals(that.bookName) : that.bookName != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (sign != null ? !sign.equals(that.sign) : that.sign != null) return false;
        return updateDay != null ? updateDay.equals(that.updateDay) : that.updateDay == null;
    }

    @Override
    public int hashCode () {
        int result = bookName != null ? bookName.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) (collectNumInc ^ (collectNumInc >>> 32));
        result = 31 * result + (int) (clictNumInc ^ (clictNumInc >>> 32));
        result = 31 * result + (int) (monthlyNumInc ^ (monthlyNumInc >>> 32));
        result = 31 * result + (int) (likeNumInc ^ (likeNumInc >>> 32));
        result = 31 * result + (int) (wordNumInc ^ (wordNumInc >>> 32));
        result = 31 * result + (sign != null ? sign.hashCode() : 0);
        result = 31 * result + (updateDay != null ? updateDay.hashCode() : 0);
        return result;
    }
}
