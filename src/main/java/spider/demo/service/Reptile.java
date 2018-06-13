package spider.demo.service;

/**
 * 用于启动爬取各个不同小说网站程序的接口
 *
 * @author lanyubing
 * @date 2018年2月1日
 */


public interface Reptile {

     int BOOK_NUM=152000;

    /**
     * 起始找书书号
     */
    int BOOK_FIRST_NUM =70000;

    /**
     * 抓取最近三十六月的收入
     */
    int mons = 36;

    /**
     * 使用雅白提供的接口爬取
     */
     void getSfbookBasicByYA ();

    /**
     * 爬取sf有价值的作者
    */
    void getAuthorBook ();

    /**
     * 每日凌晨0点00分
     * 爬取作者收入界面的数据
     */
    void getAuthorIncome();

    /**
     * 定时爬取
     */
    void  timedTask();
}
