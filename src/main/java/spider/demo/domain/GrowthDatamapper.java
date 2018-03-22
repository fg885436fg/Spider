package spider.demo.domain;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import spider.demo.domain.entity.GrowthData;
import spider.demo.domain.entity.SfBook;

import java.util.List;

/**
 * 对数据增长表进行增删改查
 * @date 2018年3月16日
 * @author lanyubing
 */
@Mapper
@Repository
public interface GrowthDatamapper {


    /**
     * 插入整个完整的书籍增长量信息
     *
     * @param  growthData 书籍增长量
     * @return
     */
    @Insert("INSERT INTO SFBOOKINC(bookName,updateDay," +
            "collectNumInc,clictNumInc," +
            "monthlyNumInc,likeNumInc,wordNumInc,sign) VALUES "
            +
            "(#{bookName},#{updateDay}," +
            "#{collectNumInc},#{clictNumInc}," +
            "#{monthlyNumInc},#{likeNumInc},#{wordNumInc},#{sign})" )
    int insertAll (GrowthData growthData);

    @Select("SELECT * FROM SFBOOKINC WHERE bookName = #{bookName}")
    List<GrowthData> findByName (@Param("bookName") String bookName);

}
