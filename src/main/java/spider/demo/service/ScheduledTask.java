package spider.demo.service;

/**
 * 定时任务
 *
 * @author: lanyubing
 * @create: 2018-07-13 16:42
 **/
public interface ScheduledTask {

    /**
     * 判断鸽子作者的时间间隔
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
     * 每天根据昨日所爬取的书籍信息，判定是否太监
     */
    void delectGuGuAuthorDay();

    /**
     * 每周根据作者表信息，判定是否太监
     */
    void delectGuGuAuthorWeek();

    /**
     * 一天的所有任务集合,每天00：00分50秒执行
     */
    void runEveryDay();

    /**
     * 处理sf爬取失败的连接
     */
    void dealWithSfErrorUrl();

    /**
     * 每周日凌晨一点运行
     */
    void runEveryWeek();


}
