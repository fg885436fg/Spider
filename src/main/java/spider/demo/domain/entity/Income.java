package spider.demo.domain.entity;

/**
 * 收入的实体类
 * @date 2018年3月23日
 * @author lanyubing
 */
public class Income {




    public Income(){}
    public Income(String date, long chapterNum, double income){
        this.date = date;
        this.chapterNum =chapterNum;
        this.income = income;

    }

    /**
     *  日期时间
     */
    private String date;
    public String getDate () {
        return date;
    }

    public void setDate (String date) {
        this.date = date;
    }

    /**
     *章节数
     */
    private long chapterNum;

    public long getChapterNum () {
        return chapterNum;
    }

    public void setChapterNum (long chapterNum) {
        this.chapterNum = chapterNum;
    }


    /**
     * 收入
     */
    private double income;

    public double getIncome () {
        return income;
    }

    public void setIncome (double income) {
        this.income = income;
    }


    /**
     * 作者名
     */
    private String authorName;

    public String getAuthorName () {
        return authorName;
    }

    public void setAuthorName (String authorName) {
        this.authorName = authorName;
    }
}
