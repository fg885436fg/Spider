package spider.demo.domain.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import spider.demo.domain.entity.AuthorCookie;

import java.util.List;

/**
 * 对作者Cookie表进行增删改查
 *
 * @author lanyubing
 * @date 2018年3月27日
 */
@Mapper
@Repository
public interface AuthorCookieMapper {


    //增

    /**
     * 插入一个作者Cookie
     *
     * @param authorCookie 作者Cookie
     * @return
     */
    @Insert("INSERT INTO authorcookie(authorName,cookieJson," +
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

    /**
     * 根据作者名查询
     *
     * @param authorName
     * @return
     */
    @Select("SELECT\n" +
            "authorcookie.authorName,\n" +
            "authorcookie.cookieJson,\n" +
            "authorcookie.authorSite\n" +
            "FROM\n" +
            "authorcookie\n" +
            "WHERE\n" +
            "authorcookie.authorName = #{authorName}")
    AuthorCookie getByAuthorName (@Param("authorName") String authorName);

    /**
     * 获取全部作者cookie
     *
     * @return
     */
    @Select("SELECT\n" +
            "authorcookie.authorName,\n" +
            "authorcookie.cookieJson,\n" +
            "authorcookie.authorSite\n" +
            "FROM \n" +
            "authorcookie\n")
    List<AuthorCookie> getAll ();


}
