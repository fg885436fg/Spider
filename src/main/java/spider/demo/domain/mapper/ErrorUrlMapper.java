package spider.demo.domain.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;
import spider.demo.domain.entity.ErrorUrl;
import spider.demo.domain.entity.ErrorUrlExample;

import java.util.List;
@Mapper
@Repository
public interface ErrorUrlMapper {
    @SelectProvider(type = ErrorUrlSqlProvider.class, method = "countByExample")
    long countByExample(ErrorUrlExample example);

    @DeleteProvider(type = ErrorUrlSqlProvider.class, method = "deleteByExample")
    int deleteByExample(ErrorUrlExample example);

    @Delete({
            "delete from error_url",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into error_url (id, url, ",
            "type, date)",
            "values (#{id,jdbcType=INTEGER}, #{url,jdbcType=VARCHAR}, ",
            "#{type,jdbcType=VARCHAR}, #{date,jdbcType=TIMESTAMP})"
    })
    int insert(ErrorUrl record);

    @InsertProvider(type = ErrorUrlSqlProvider.class, method = "insertSelective")
    int insertSelective(ErrorUrl record);

    @SelectProvider(type = ErrorUrlSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "date", property = "date", jdbcType = JdbcType.TIMESTAMP)
    })
    List<ErrorUrl> selectByExample(ErrorUrlExample example);

    @Select({
            "select",
            "id, url, type, date",
            "from error_url",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "date", property = "date", jdbcType = JdbcType.TIMESTAMP)
    })
    ErrorUrl selectByPrimaryKey(Integer id);

    @UpdateProvider(type = ErrorUrlSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ErrorUrl record, @Param("example") ErrorUrlExample example);

    @UpdateProvider(type = ErrorUrlSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") ErrorUrl record, @Param("example") ErrorUrlExample example);

    @UpdateProvider(type = ErrorUrlSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ErrorUrl record);

    @Update({
            "update error_url",
            "set url = #{url,jdbcType=VARCHAR},",
            "type = #{type,jdbcType=VARCHAR},",
            "date = #{date,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ErrorUrl record);
}