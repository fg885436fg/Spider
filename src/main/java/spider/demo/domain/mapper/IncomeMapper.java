package spider.demo.domain.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import spider.demo.domain.entity.Income;

import java.util.List;

/**
 * 对收入进行增删改查
 * @author lanyubing
 * @date 2018年3月27日
 */
@Mapper
@Repository
public interface IncomeMapper {

    //增

    /**
     * 批量插入收入数据，通过内部类来生成动态的sql语句。
     *
     * @param incomes 书籍增长量信息集合
     * @return
     */

    @Insert("<script>" +
            "INSERT INTO income(date,chapterNum," +
            "income,authorName) VALUES " +
            "<foreach collection='list' item='item' index='index'  separator=\",\">" +
            "(#{item.date},#{item.chapterNum}," +
            "#{item.income},#{item.authorName})" +
            "</foreach>" +
            "</script>")
    int insertIncBatch (@Param("list") List<Income> incomes);







    //删

    /**
     * 根据作者名删除表
     *
     * @param authorName
     * @return
     */
    @Delete("DELETE\n" +
            "FROM\n" +
            "income\n" +
            "WHERE\n" +
            "income.authorName =#{authorName}")
    int delectByAuthorName (@Param("authorName") String authorName);



    //查


    /**
     * 根据作者名查询全部收入信息。
     *
     * @param authorName
     * @return
     */
    @Select("SELECT\n" +
            "*\n" +
            "FROM \n" +
            "income\n" +
            "WHERE\n" +
            "income.authorName =#{authorName}")
    List<Income> getByAuthorName (@Param("authorName") String authorName);
    /**
     * 根据作者名与时间查询以时间升序的收入信息。
     *
     * @param authorName 作者名
     * @param date 日期
     * @return
     */
    @Select("SELECT DISTINCT\n" +
            "*"+
            "FROM\n" +
            "income\n" +
            "WHERE\n" +
            "income.authorName = #{authorName} AND\n" +
            "income.date LIKE  CONCAT(CONCAT('%', #{date}),'%')\n" +
            "ORDER BY\n" +
            "income.date ASC\n")
    List<Income> getByAuthorNameAndDate (@Param("authorName") String authorName,@Param("date") String date );
















    //改
}
