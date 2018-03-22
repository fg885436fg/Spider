package spider.demo.service;

import spider.demo.domain.vo.EchartsVo;
import spider.demo.domain.entity.GrowthData;

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
     EchartsVo creatEchartsVo (List<GrowthData> growthDatas) throws Exception;














}
