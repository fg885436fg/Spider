package spider.demo.domain;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import spider.demo.domain.entity.GrowthData;
import spider.demo.domain.entity.SfBook;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * 对数据增长表进行增删改查
 *
 * @author lanyubing
 * @date 2018年3月16日
 */
@Mapper
@Repository
public interface GrowthDatamapper {


    /**
     * 插入整个完整的书籍增长量信息
     *
     * @param growthData 书籍增长量
     * @return
     */
    @Insert("INSERT INTO SFBOOKINC(bookName,updateDay," +
            "collectNumInc,clictNumInc," +
            "monthlyNumInc,likeNumInc,wordNumInc,sign) VALUES "
            +
            "(#{bookName},#{updateDay}," +
            "#{collectNumInc},#{clictNumInc}," +
            "#{monthlyNumInc},#{likeNumInc},#{wordNumInc},#{sign})")
    int insertAll (GrowthData growthData);

    /**
     * 批量插入完整的书籍增长量信息，通过内部类来生成动态的sql语句。
     *
     * @param growthDatas 书籍增长量信息集合
     * @return
     */

    @Insert("<script>"  +
            "INSERT INTO SFBOOKINC(bookName,updateDay," +
            "collectNumInc,clictNumInc," +
            "monthlyNumInc,likeNumInc,wordNumInc,sign,date) VALUES " +
            "<foreach collection='list' item='item' index='index'  separator=\",\">" +
            "(#{item.bookName},#{item.updateDay}," +
            "#{item.collectNumInc},#{item.clictNumInc}," +
            "#{item.monthlyNumInc},#{item.likeNumInc},#{item.wordNumInc},#{item.sign},#{item.date})" +
            "</foreach>" +
            "</script>")
    int insertIncBatch (@Param("list") List<GrowthData> growthDatas);


    /**
     * 根据书名获取一个增长数据
     * @param bookName
     * @return
     */
    @Select("SELECT * FROM SFBOOKINC WHERE bookName = #{bookName}")
    List<GrowthData> findByName (@Param("bookName") String bookName);

    /**
     * 获取近三日，以点击增长量的排序数据。
     * @param bookName
     * @return
     */
    @Select("SELECT * FROM SFBOOKINC WHERE bookName = #{bookName}")
    List<GrowthData> getBookIncSortByClick(@Param("bookName") String bookName);

    /**
     * 删除当日所有增长数据
     * @date 日期
     * @return
     */

    @Delete(" ")
    int delectBookInc(@Param("date") String date);

}
