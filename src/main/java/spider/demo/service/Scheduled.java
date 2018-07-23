package spider.demo.service;

/**
 * 定时任务
 *
 * @author: lanyubing
 * @create: 2018-07-13 16:42
 **/
public interface Scheduled {

    /**
     * 判断咕咕的时间间隔
     */
    int GUGU_DAY=15;

    /**
     * 每周一凌晨凌晨一点
     * 定时异步爬取有价值作者信息
     */
    void getAuthorInfoTimedTask();

    /**
     * 爬取书籍信息
     */
    void getBookInfoTimedTask();

    /**
     * 存储增长信息
     */
    void saveGrowthData() throws Exception;

    /**
     * 定时删除咕咕咕的作者
     */
    void delectGuGuAuthor();

    /**
     * 一天的所有任务集合
     */
    void taskAll();

    /**
     * 处理sf爬取失败的连接
     */
    void dealWithSfErrorUrl();


}
