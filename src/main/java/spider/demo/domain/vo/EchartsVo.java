package spider.demo.domain.vo;


/**
 * 折线图的对象
 *
 * @author lanyubing
 * @date 2018年2月5日
 */
public class EchartsVo {
//' ['点击增长量','收藏增长量','月票增长量','字数增长量','赞数增长量']


    private String name;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    /**
     * Y轴，一般是日期
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
     * 点击增加量
     * 具体数据
     * data:[1, -2, 2, 5, 3, 2, 0],
     */

    private String[] clictNumInc;

    public String[] getClictNumInc () {
        return clictNumInc;
    }

    public void setClictNumInc (String[] clictNumInc) {
        this.clictNumInc = clictNumInc;
    }

    /**
     * 月票增长量
     */
    private String[] monthlyNumInc;

    public String[] getMonthlyNumInc () {
        return monthlyNumInc;
    }

    public void setMonthlyNumInc (String[] monthlyNumInc) {
        this.monthlyNumInc = monthlyNumInc;
    }


    /**
     * 赞数增长量
     */
    private String[] likeNumInc;

    public String[] getLikeNumInc () {
        return likeNumInc;
    }

    public void setLikeNumInc (String[] likeNumInc) {
        this.likeNumInc = likeNumInc;
    }


    /**
     * 收藏增长量
     */
    private String[] collectNumInc;

    public String[] getCollectNumInc () {
        return collectNumInc;
    }

    public void setCollectNumInc (String[] collectNumInc) {
        this.collectNumInc = collectNumInc;
    }


    /**
     * 字数增长量
     */

    private String[] wordNumInc;

    public String[] getWordNumInc () {
        return wordNumInc;
    }

    public void setWordNumInc (String[] wordNumInc) {
        this.wordNumInc = wordNumInc;
    }


    /**
     * 可能最不喜欢看到的东西...，它是一周最低数据。
     * <p>
     * {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
     * 值，X 横坐标，纵坐标
     */

    private long lowCollectNum;

    public long getLowCollectNum () {
        return lowCollectNum;
    }

    public void setLowCollectNum (long lowCollectNum) {
        this.lowCollectNum = lowCollectNum;
    }

    private long lowClictNumInc;

    public long getLowClictNumInc () {
        return lowClictNumInc;
    }

    public void setLowClictNumInc (long lowClictNumInc) {
        this.lowClictNumInc = lowClictNumInc;
    }

    private long lowMonthlyNumInc;

    public long getLowMonthlyNumInc () {
        return lowMonthlyNumInc;
    }

    public void setLowMonthlyNumInc (long lowMonthlyNumInc) {
        this.lowMonthlyNumInc = lowMonthlyNumInc;
    }

    private long lowtWordNumInc;

    public long getLowtWordNumInc () {
        return lowtWordNumInc;
    }

    public void setLowtWordNumInc (long lowtWordNumInc) {
        this.lowtWordNumInc = lowtWordNumInc;
    }

    private long lowtLikeNumInc;

    public long getLowtLikeNumInc () {
        return lowtLikeNumInc;
    }

    public void setLowtLikeNumInc (long lowtLikeNumInc) {
        this.lowtLikeNumInc = lowtLikeNumInc;
    }
}

