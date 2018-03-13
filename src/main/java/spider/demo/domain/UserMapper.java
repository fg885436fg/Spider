package spider.demo.domain;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {


    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age})")
    int insert (@Param("name") String name, @Param("age") Integer age);

}