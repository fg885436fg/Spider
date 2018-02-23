package spider.demo.service;

import spider.demo.domain.entity.SfBook;
import spider.demo.domain.vo.EchartsVo;
import spider.demo.domain.vo.GrowthData;
import spider.demo.exception.MyException;

import java.util.List;

/**
 * 用于统计书籍增长数据
 * @author lanyubing
 * @date 2018年2月2日
 */
public interface CountData {


    /**
     * 获取一日的常量
     */
    int ONE_DAY = 1;
    /**
     * 获取一周的常量
     */
    int WEEK_DAY = 7;
    /**
     * 获取一月的常量
     */
    int MON_DAY = 30;


    /**
     * 统计最近七日，所有的数据
     *
     * @param bookName 书名
     */
    List<SfBook> allDateWeek(String bookName);

    /**
     * 最近一个月，所有的数据
     *
     * @param bookName 书名
     */
    List<SfBook> allDateMonth(String bookName);


    /**
     * 最近1日的数据增长/减少量
     *
     * @param bookName 书名
     */
    List<GrowthData> growthAllDay(String bookName);

    /**
     * 最近七日的数据增长/减少量
     * @param bookName 书名
     */
   EchartsVo growthAllweek(String bookName) throws MyException;

    /**
     * 最近一个月的数据增长/减少量
     * @param bookName 书名
     */
    EchartsVo  growthAllMonth(String bookName) throws MyException;



    /**
     * 最近一个月的数据增长/减少量,用于在控制台显示
     * @param bookName 书名
     */
    List<GrowthData> growthAllMonthForConsole(String bookName);









}
