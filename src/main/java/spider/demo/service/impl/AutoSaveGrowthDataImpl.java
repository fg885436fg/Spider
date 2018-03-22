package spider.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spider.demo.domain.GrowthDatamapper;
import spider.demo.domain.SfBookMapper;
import spider.demo.domain.entity.GrowthData;
import spider.demo.service.AutoSaveGrowthData;
import spider.demo.service.CountData;

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
    private GrowthDatamapper growthDatamapper;

    protected static Logger logger = LoggerFactory.getLogger(AutoSaveGrowthDataImpl.class);

    @Override
    @Scheduled(cron = "0 0/1 * * * ?")
    public void saveGrowthData () throws Exception {
        LocalDate today = LocalDate.now();
        logger.info("开始存储书籍的增长数据");

        List<String> dates = new ArrayList<>();

        for (int i = 0; i < MON_DAY; i++) {

            dates.add(today.minusDays(i).toString());
        }


        // 获取近一个月内更新的小说名
        List<String> bookNames = new ArrayList<>();
        for (int i = 0; i < MON_DAY; i++) {

            bookNames = sfBookMapper.findBookNameBatchByDate(dates);

        }
        for (String bookName : bookNames) {

            List<GrowthData> growthDatas = countData.growthAllweek(bookName);

            for (GrowthData growthData : growthDatas) {
                growthDatamapper.insertAll(growthData);
            }


        }

        logger.info("存储了"+bookNames.size()+"本书的增长数据");


    }
}