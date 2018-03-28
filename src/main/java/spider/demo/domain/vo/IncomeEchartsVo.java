package spider.demo.domain.vo;

/**
 * 收入的图表实体类
 * @date 2018年3月28日
 * @author lanyubing
 */
public class IncomeEchartsVo {


    public IncomeEchartsVo () {
    }

    public IncomeEchartsVo (String authorName, String[] xAxisdate, String[] chapterNum, String[] income) {
        this.authorName = authorName;
        this.xAxisdate = xAxisdate;
        this.chapterNum = chapterNum;
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

    /**
     * X轴，一般是日期
     * data : ['2018-','2018-','2018-','2018-','2018-','2018-','2018-']
     */
    private String[] xAxisdate;


    public String[] getxAxisdate () {
        return xAxisdate;
    }

    public void setxAxisdate (String[] xAxisdate) {
        this.xAxisdate = xAxisdate;
    }

    /**
     * 章数增长量
     */
    private String[] chapterNum;

    public String[] getChapterNum () {
        return chapterNum;
    }

    public void setChapterNum (String[] chapterNum) {
        this.chapterNum = chapterNum;
    }

    /**
     * 收入
     */
    private String[] income;

    public String[] getIncome () {
        return income;
    }

    public void setIncome (String[] income) {
        this.income = income;
    }
}
