package spider.demo.service;

/**
 * 自动存储书籍增长数据接口
 *
 * @author lanyubing
 * @date 2018年3月21日 16:13:32
 */
public interface AutoSaveGrowthData {

    //一月的日期
    int MON_DAY = 30;

    /**
     * 自动存储近日更新的书籍
     */
    void saveGrowthData () throws Exception;


}
