package spider.demo.service;

import spider.demo.domain.entity.Income;
import spider.demo.domain.vo.BookIncEchartsVo;
import spider.demo.domain.entity.GrowthData;
import spider.demo.domain.vo.IncomeEchartsVo;

import java.util.List;

/**
 * 处理各种数据，以符合报表格式要求。
 * 并与控制层对接
 *
 * @author lanyubing
 * @date 2018年3月7日
 */
public interface DataHandle {

    /**
     * 把增长数据集合，加工为符合报表格式的集合
     *
     * @param growthDatas 增长数据
     * @return 符合报表格式的数据
     * @throws Exception 统一处理异常
     */
     BookIncEchartsVo creatEchartsVo (List<GrowthData> growthDatas) throws Exception;


    /**
     * 把收入集合转换加工为符合报表格式的实体类
     * @param incomes 收入集合
     * @return
     * @throws Exception 统一处理异常
     */
    IncomeEchartsVo creatIncomeEchartsVo(List<Income> incomes) throws  Exception;








}
