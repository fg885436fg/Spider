package spider.demo.domain.vo;

import spider.demo.tools.NumProcess;

/**
 * 你算老几的实体类
 *
 * @author lanyubing
 * @date 2018年3月26日
 */
public class WhoAreYou {

    /**
     * 和你一起的家伙一共有多少位？
     */
    private double sortNum;

    public double getSortNum () {
        return sortNum;
    }

    public void setSortNum (double sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * 看看你算老几？
     */
    private double rank;

    public double getRank () {
        return rank;
    }

    public void setRank (double rank) {
        this.rank = rank;
    }

    /**
     * 在你前面的猴子占人数的百分比？
     */
    private double fuckRate;

    public double getFuckRate () {
        NumProcess num = new NumProcess();

        return num.halfUp(rank / sortNum);
    }

    public void setFuckRate (double fuckRate) {
        this.fuckRate = fuckRate;
    }
}
