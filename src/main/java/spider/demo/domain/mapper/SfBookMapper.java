package spider.demo.domain.mapper;

import org.apache.ibatis.annotations.*;

import spider.demo.config.SimpleSelectInExtendedLanguageDriver;
import spider.demo.domain.entity.SfBook;

import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 对存储着书籍信息的表进行操作
 *
 * @author lanyubing
 * @date 2018年2月3日
 */
@Mapper
@Repository
public interface SfBookMapper {

    @Select("SELECT * FROM SFBOOK")
    List<SfBook> findAll ();

    @Select("SELECT * FROM SFBOOK WHERE bookName = #{bookName}")
    List<SfBook> findByName (@Param("bookName") String bookName);

    @Select("SELECT DISTINCT * FROM  SFBOOK WHERE bookName = #{bookName} AND date = #{date}")
    SfBook findByNameAndDate (@Param("bookName") String bookName, @Param("date") String date);

    /**
     * 获取指定书籍最近一周的所有数据
     *
     * @param bookName
     * @return
     */
    @Select("SELECT DISTINCT " +
            "*\n" +
            "FROM\n" +
            "SFBOOK\n" +
            "WHERE\n" +
            "SFBOOK.bookName =  #{bookName}\n" +
            "ORDER BY\n" +
            "SFBOOK.date DESC\n" +
            "LIMIT 0,100")
    List<SfBook> findAllDateWeek (@Param("bookName") String bookName);

    /**
     * 根据日期获取书名
     * @param date
     * @return
     */
    @Select("SELECT\n" +
            "SFBOOK.bookName\n" +
            "FROM\n" +
            "SFBOOK\n" +
            "WHERE\n" +
            "SFBOOK.upateDate = #{date} AND\n" +
            "SFBOOK.sign <> '普通'\n")
    List<String> findAllByDate(@Param("date") String date);

    /**
     * 根据爬取日期获得书籍信息
     * @param date 日期
     * @return
     */
    @Select("SELECT DISTINCT\n" +
            "*\n" +
            "FROM\n" +
            "SFBOOK\n" +
            "WHERE\n" +
            "SFBOOK.date = #{date}")
    List<SfBook> findBookBydate (@Param("date") String date);

    /**
     * 根据更新日期批量获取书名
     * @param dates 日期集合
     * @return
     */
    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select("SELECT  DISTINCT\n" +
            "SFBOOK.bookName\n" +
            "FROM\n" +
            "SFBOOK\n" +
            "WHERE\n" +
            "SFBOOK.sign != '普通'\n" +
            " AND "+
            "SFBOOK.upateDate IN (#{dates})")
    List<String> findBookNameBatchByUpdate (@Param("dates") List<String> dates);

    /**
     * 获取最近一个月的所有数据
     *
     * @param bookName
     * @return
     */
    @Select("SELECT DISTINCT " +
            "*\n" +
            "FROM\n" +
            "SFBOOK\n" +
            "WHERE\n" +
            "SFBOOK.bookName =  #{bookName}\n" +
            "ORDER BY\n" +
            "SFBOOK.date DESC\n" +
            "LIMIT 0,100")
    List<SfBook> findAllDateMonth (@Param("bookName") String bookName);


    /**
     * 插入整个完整的书籍信息
     *
     * @param sfBook 书籍对象
     * @return
     */
    @Insert("INSERT INTO SFBOOK(bookName,collectNum," +
            "clickNum,monthlyNum," +
            "likeNum,date,upateDate,status,wordNum,sign) VALUES "
            +
            "(#{bookName},#{collectNum}," +
            "#{clickNum},#{monthlyNum}," +
            "#{likeNum},#{date},#{upateDate},#{status},#{wordNum},#{sign})" )
    int insertAll (SfBook sfBook);


    /**
     * 用于测试的，只插入书名字段
     *
     * @param bookName
     * @return
     */

    @Insert("INSERT INTO SFBOOK(bookName) VALUES(#{bookName})")
    int insert (@Param("bookName") String bookName);

    /**
     * 用于删除书籍
     * @param bookName
     * @return
     */

    @Delete("DELETE FROM SFBOOK WHERE SFBOOK.bookName LIKE  CONCAT(CONCAT('%', #{bookName}),'%')")
    int delectByBookName (@Param("bookName") String bookName);


}
