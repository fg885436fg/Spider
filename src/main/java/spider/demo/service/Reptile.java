package spider.demo.service;

import us.codecraft.webmagic.proxy.Proxy;

import java.util.List;

/**
 * 用于启动爬取各个不同小说网站程序的接口
 *
 * @author lanyubing
 * @date 2018年2月1日
 */


public interface Reptile {

    int BOOK_NUM = 172000;

    /**
     * 起始找书书号
     */
    int BOOK_FIRST_NUM = 80000;

    /**
     * 最多处理错误连接的次数
     */
    int DEAL_WITH_ERROR_URL = 5;

    /**
     * 抓取最近三十六月的收入
     */
    int mons = 36;
    /**
     * 处理错误连接的最大次数
     */

    int DEAL_ERROR_URL_NUM = 5;

    /**
     * 使用雅白提供的接口爬取
     */
    void getSfbookBasicByYA();

    /**
     * 爬取sf有价值的作者
     */
    void getAuthorBook();

    /**
     * 每日凌晨0点00分
     * 爬取作者收入界面的数据
     */
    void getAuthorIncome();

    /**
     * 获取一个可用的Proxy
     *
     * @return
     */
    List<Proxy> getProxy();

    /**
     * 通过API获取付费的代理
     *
     * @return
     */
    List<Proxy> getAllNoFeeProxy();

    /**
     * 处理sf爬取失败的连接
     */
    void dealWithSfErrorUrl();

    void setRetryNum(int retryNum);

}
