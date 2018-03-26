package spider.demo.domain.entity;

/**
 * 收入d的jij实体类
 * @date 2018年3月23日
 * @author lanyubing
 */
public class Income {




    public Income(){}
    public Income(String date, int chapterNum, double income){
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
    private int chapterNum;

    public int getChapterNum () {
        return chapterNum;
    }

    public void setChapterNum (int chapterNum) {
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
}
