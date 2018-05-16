package spider.demo.domain.Mapper;

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
public interface GrowthDataMapper {


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
     *
     * @param growthDatas 书籍增长量信息集合
     * @return
     */

    @Insert("<script>" +
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


    //以下为查

    /**
     * 根据书名获取一个增长数据
     *
     * @param bookName
     * @return
     */
    @Select("SELECT * FROM SFBOOKINC WHERE bookName = #{bookName}")
    List<GrowthData> findByName (@Param("bookName") String bookName);

    /**
     * 获取最新，以点击增长量的排序数据（由大至小）。
     *
     * @return
     */
    @Select("SELECT *\n" +
            "FROM SFBOOKINC\n" +
            "WHERE SFBOOKINC.date=#{date}\n" +
            "ORDER BY\n" +
            "SFBOOKINC.clictNumInc DESC\n")
    List<GrowthData> getBookIncSortByClick (@Param("date") String date);

    /**
     * 获取最新，以月票增长量的排序数据（由大至小）。
     *
     * @return
     */
    @Select("SELECT *\n" +
            "FROM SFBOOKINC\n" +
            "WHERE SFBOOKINC.date=#{date}\n" +
            "ORDER BY\n" +
            "SFBOOKINC.monthlyNumInc DESC\n")
    List<GrowthData> getBookIncSortByMonth (@Param("date") String date);

    /**
     * 获取最新，以字数增长量的排序数据（由大至小）。
     *
     * @return
     */
    @Select("SELECT *\n" +
            "FROM SFBOOKINC\n" +
            "WHERE SFBOOKINC.date=#{date}\n" +
            "ORDER BY\n" +
            "SFBOOKINC.wordNumInc DESC\n")
    List<GrowthData> getBookIncSortByWord (@Param("date") String date);


    /**
     * 获取最新，以点击增长量的排序数据（由大至小）。
     *不包含普通作品
     * @return
     */
    @Select("SELECT *\n" +
            "FROM SFBOOKINC\n" +
            "WHERE\n" +
            "SFBOOKINC.sign <> '普通'\n" +
            " AND SFBOOKINC.date=#{date}\n" +
            "ORDER BY\n" +
            "SFBOOKINC.clictNumInc DESC\n")
    List<GrowthData> getVipBookIncSortByClick (@Param("date") String date);

    /**
     * 获取最新，以月票增长量的排序数据（由大至小）。
     *不包含普通作品
     * @return
     */
    @Select("SELECT *\n" +
            "FROM SFBOOKINC\n" +
            "WHERE\n" +
            "SFBOOKINC.sign <> '普通' \n" +
            " AND SFBOOKINC.date=#{date}\n" +
            "ORDER BY\n" +
            "SFBOOKINC.wordNumInc DESC\n")
    List<GrowthData> getVipBookIncSortByMonth (@Param("date") String date);

    /**
     * 获取最新，以字数增长量的排序数据（由大至小）。
     *不包含普通作品
     * @return
     */
    @Select("SELECT *\n" +
            "FROM SFBOOKINC\n" +
            "WHERE\n" +
            "SFBOOKINC.sign <> '普通'\n" +
            " AND SFBOOKINC.date=#{date}\n" +
            "ORDER BY\n" +
            "SFBOOKINC.wordNumInc DESC\n")
    List<GrowthData> getVipBookIncSortByWord (@Param("date") String date);


    //以下为删

    /**
     * 删除当日所有增长数据
     *
     * @return
     * @param  date 日期
     */

    @Delete("DELETE\n" +
            "FROM\n" +
            "SFBOOKINC\n" +
            "WHERE\n" +
            "SFBOOKINC.date =#{date} ")
    int delectBookInc (@Param("date") String date);

}
