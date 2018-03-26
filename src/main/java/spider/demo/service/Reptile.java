package spider.demo.service;

/**
 * 用于启动爬取各个不同小说网站程序的接口
 *
 * @author lanyubing
 * @date 2018年2月1日
 */


public interface Reptile {


    final int BOOK_NUM=152000;

    /**
     * 起始找书书号
     */
    final int BOOK_FIRST_NUM =60000;
    /**
     * 爬取sf网站的基本信息（除了简介之外），用于比较每日数据增长
     */
    public void getSfbookBasic ();


    /**
     * 使用雅白提供的接口爬取
     */
    public void getSfbookBasicByYA ();



    /**
     * 爬取sf有价值的作者
    */
    public void getAuthorBook ();

    /**
     * 每日凌晨0点00分
     * 爬取作者收入界面的数据
     */
    void getAuthorIncome();








}
