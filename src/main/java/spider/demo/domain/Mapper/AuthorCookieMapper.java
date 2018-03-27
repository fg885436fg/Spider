package spider.demo.domain.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import spider.demo.domain.entity.AuthorCookie;
import spider.demo.domain.entity.SfBook;

/**
 * 对作者Cookie表进行增删改查
 *
 * @author lanyubing
 * @date 2018年3月27日
 */
public interface AuthorCookieMapper {


    //增

    /**
     * 插入一个作者Cookie
     *
     * @param authorCookie 作者Cookie
     * @return
     */
    @Insert("INSERT INTO authorCookie(authorName,cookieJson," +
            "authorSite) VALUES "
            +
            "(#{authorName},#{cookieJson}," +
            "#{authorSite})")
    int insertCookieMapper (AuthorCookie authorCookie);


    //删


    /**
     * 根据作者名删除表
     *
     * @param authorName
     * @return
     */
    @Delete("DELETE\n" +
            "FROM\n" +
            "authorcookie\n" +
            "WHERE\n" +
            "authorcookie.authorName =#{authorName}")
    int delectByAuthorName (@Param("authorName") String authorName);


    //改


    //查

    /**根据作者名查询
     *
     * @param authorName
     * @return
     */
    @Select("SELECT\n" +
            "*\n" +
            "FROM \n" +
            "authorcookie\n" +
            "WHERE\n" +
            "authorcookie.authorName =#{authorName}")
    int getByAuthorName (@Param("authorName") String authorName);


}
