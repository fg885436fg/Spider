package spider.demo.domain.mapper;



import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;
import spider.demo.domain.entity.ForbiddenWord;
import spider.demo.domain.entity.ForbiddenWordExample;

import java.util.List;
@Mapper
@Repository
public interface ForbiddenWordMapper {
    @SelectProvider(type=ForbiddenWordSqlProvider.class, method="countByExample")
    long countByExample(ForbiddenWordExample example);

    @DeleteProvider(type=ForbiddenWordSqlProvider.class, method="deleteByExample")
    int deleteByExample(ForbiddenWordExample example);

    @Delete({
        "delete from forbidden_word",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into forbidden_word (id, word, ",
        "date)",
        "values (#{id,jdbcType=INTEGER}, #{word,jdbcType=VARCHAR}, ",
        "#{date,jdbcType=DATE})"
    })
    int insert(ForbiddenWord record);

    @InsertProvider(type=ForbiddenWordSqlProvider.class, method="insertSelective")
    int insertSelective(ForbiddenWord record);

    @SelectProvider(type=ForbiddenWordSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="word", property="word", jdbcType=JdbcType.VARCHAR),
        @Result(column="date", property="date", jdbcType=JdbcType.DATE)
    })
    List<ForbiddenWord> getByExample(ForbiddenWordExample example);

    @Select({
        "select",
        "id, word, date",
        "from forbidden_word",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="word", property="word", jdbcType=JdbcType.VARCHAR),
        @Result(column="date", property="date", jdbcType=JdbcType.DATE)
    })
    ForbiddenWord getByPrimaryKey(Integer id);

    @UpdateProvider(type=ForbiddenWordSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ForbiddenWord record, @Param("example") ForbiddenWordExample example);

    @UpdateProvider(type=ForbiddenWordSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ForbiddenWord record, @Param("example") ForbiddenWordExample example);

    @UpdateProvider(type=ForbiddenWordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ForbiddenWord record);

    @Update({
        "update forbidden_word",
        "set word = #{word,jdbcType=VARCHAR},",
          "date = #{date,jdbcType=DATE}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ForbiddenWord record);
}