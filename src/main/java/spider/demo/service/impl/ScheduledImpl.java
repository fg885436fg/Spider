package spider.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.demo.domain.entity.Author;
import spider.demo.domain.entity.SfBook;
import spider.demo.domain.mapper.AuthorMapper;
import spider.demo.domain.mapper.SfBookMapper;
import spider.demo.service.AutoSaveGrowthData;
import spider.demo.service.Reptile;
import spider.demo.service.Scheduled;
import spider.demo.tools.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author: lanyubing
 * @create: 2018-07-13 17:50
 **/
@Service
public class ScheduledImpl implements Scheduled {

    @Autowired
    SfBookMapper sfBookMapper;

    @Autowired
    private Reptile reptile;

    @Autowired
    AutoSaveGrowthData autoSaveGrowthData;

    @Autowired
    AuthorMapper authorMapper;

    @Override
    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 1 ? * 2 ")
    public void getAuthorInfoTimedTask() {
        ExecutorService executor = newFixedThreadPool(2);
        CompletableFuture.runAsync(() -> {
            reptile.getAuthorBook();
            executor.shutdown();
        }, executor);
    }

    @Override
    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 0,8 * * ?")
    public void getBookInfoTimedTask() {
        ExecutorService executor = newFixedThreadPool(2);
        //获取书籍信息
        CompletableFuture.runAsync(() -> {
            reptile.getSfbookBasicByYA();
            executor.shutdown();
        }, executor);
    }

    @Override
    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 1 * * ?")
    public void saveGrowthData() throws Exception {
        autoSaveGrowthData.saveGrowthData();
    }

    @Override
    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 2 * * ?")
    public void delectGuGuAuthor() {
        List<SfBook> sfBooks = sfBookMapper.findAll();
        DateUtil dateUtil = new DateUtil();
        for (SfBook book : sfBooks) {
            long dayNum = dateUtil.
                    getDistanceDays(dateUtil.getAnyNowDate("yyyy-MM-dd", 0), book.getUpateDate());
            if (dayNum >= GUGU_DAY && !"已完结".equals(book.getStatus())) {
                authorMapper.updateAuthorRightByBookName(book.getBookName(), 0);
            } else if(dayNum<GUGU_DAY) {
                authorMapper.updateAuthorRightByBookName(book.getBookName(), 1);
            }
        }
    }
}
