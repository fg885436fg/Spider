package spider.demo.domain.mapper;

import org.apache.ibatis.annotations.*;

import org.springframework.stereotype.Repository;
import spider.demo.domain.entity.Author;


import java.util.List;


/**
 * 用于对存储作者与其所写书籍URL的表进行数据处理
 *
 * @author lanyubing
 * @date 2018年2月2日
 */
@Mapper
@Repository
public interface AuthorMapper {

    /**
     * 获取所有作者
     *
     * @return
     */

    @Select("SELECT\n" +
            "id,\n" +
            "authorName,\n" +
            "bookName,\n" +
            "url,\n" +
            "ok AS `right`\n" +
            "FROM\n" +
            "Author\nr")
    List<Author> findAll();

    /**
     * 根据书名查询作者
     *
     * @return
     */
    @Select("SELECT * FROM Author WHERE bookName = #{bookName}")
    Author findByBookName(@Param("bookName") String bookName);

    /**
     * 插入作者信息到作者表中
     *
     * @return
     */
    @Insert("INSERT INTO Author(authorName, bookName," +
            "url,right) VALUES"
            +
            "(#{authorName}, #{bookName}," +
            "#{url}，#{right})")
    int insertAll(Author author);

    /**
     * 根据书名删除作者
     *
     * @param bookName
     * @return
     */
    @Delete("DELETE FROM Author WHERE Author.bookName LIKE  CONCAT(CONCAT('%', #{bookName}),'%')")
    int delectByBookName(@Param("bookName") String bookName);

    @Update({
            "update Author",
            "set authorName = #{authorName},",
            "bookName = #{bookName},",
            "url = #{url},",
            "right = #{right}",
            "where bookName = #{bookName}"
    })
    int updateAuthorByBookName(Author author);

    @Update({
            "update Author "+
            "set ok = #{right} "+
            "where bookName = #{bookName}"
    })
    int updateAuthorRightByBookName(@Param("bookName") String bookName, @Param("right") int right);

}
