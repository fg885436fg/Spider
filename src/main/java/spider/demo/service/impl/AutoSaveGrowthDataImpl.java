package spider.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spider.demo.domain.mapper.GrowthDataMapper;
import spider.demo.domain.mapper.SfBookMapper;
import spider.demo.domain.entity.GrowthData;
import spider.demo.service.AutoSaveGrowthData;
import spider.demo.service.CountData;
import spider.demo.tools.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自动存储增长数据
 *
 * @author lanyubing
 * @date 2018年3月21日
 */
@Service
public class AutoSaveGrowthDataImpl implements AutoSaveGrowthData {

    @Autowired
    private SfBookMapper sfBookMapper;

    @Autowired
    private CountData countData;

    @Autowired
    private GrowthDataMapper growthDataMapper;

    protected static Logger logger = LoggerFactory.getLogger(AutoSaveGrowthDataImpl.class);

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void saveGrowthData () throws Exception {
        LocalDate today = LocalDate.now();
        logger.info("开始存储书籍的增长数据");

        List<String> dates = new ArrayList<>();

        for (int i = 0; i < MON_DAY; i++) {

            dates.add(today.minusDays(i).toString());
        }

        DateUtil d = new DateUtil();
        growthDataMapper.delectBookInc(d.getAnyDate("yyyy-MM-dd",1));

        // 获取近一个月内更新的小说名
        List<String> bookNames = new ArrayList<>();
        for (int i = 0; i < MON_DAY; i++) {
            bookNames.addAll(sfBookMapper.findBookNameBatchByUpdate(dates)) ;
        }

        bookNames =  bookNames.stream().distinct().collect(Collectors.toList());
        //分段存储
        List<GrowthData> growthDatas = new ArrayList<>();
        int index = 0;
        List<GrowthData> temp = new ArrayList<>();
        for (String bookName : bookNames) {
            index++;
            temp.addAll(countData.growthAllDay(bookName));
            if ((index % 100 == 0 && index!=0)||index == bookNames.size()  ) {
                growthDataMapper.insertIncBatch(temp);
                growthDatas.addAll(temp);
                temp.clear();
            }
        }
        logger.info("存储了" + growthDatas.size() + "本书的增长数据");
    }
}
