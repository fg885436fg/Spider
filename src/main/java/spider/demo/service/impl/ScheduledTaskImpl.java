package spider.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spider.demo.domain.entity.Author;
import spider.demo.domain.entity.ProxyEntity;
import spider.demo.domain.entity.SfBook;
import spider.demo.domain.mapper.AuthorMapper;
import spider.demo.domain.mapper.SfBookMapper;
import spider.demo.service.AutoSaveGrowthData;
import spider.demo.service.ProxyPool;
import spider.demo.service.Reptile;
import spider.demo.service.ScheduledTask;
import spider.demo.service.webmagic.DeleceSfGuGuBook;
import spider.demo.service.webmagic.LybHttpClientDownloader;
import spider.demo.tools.DateUtil;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author: lanyubing
 * @create: 2018-07-13 17:50
 **/
@Service
public class ScheduledTaskImpl implements ScheduledTask {

    private static Logger logger = LoggerFactory.getLogger(ScheduledTaskImpl.class);



    @Autowired
    LybHttpClientDownloader lyb;

    @Autowired
    SfBookMapper sfBookMapper;

    @Autowired
    private Reptile reptile;

    @Autowired
    DeleceSfGuGuBook deleceSfGuGuBook;

    @Autowired
    AutoSaveGrowthData autoSaveGrowthData;

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    ProxyPool proxyPool;

    int threadNum = 10;

    @Override
    public void getAuthorInfoTimedTask() {
        ExecutorService executor = newFixedThreadPool(2);
        CompletableFuture.runAsync(() -> {
            reptile.getAuthorBook();
            executor.shutdown();
        }, executor);
    }

    @Override
    public void getBookInfoTimedTask() {
        logger.warn("开始执行爬取基本书籍任务");
        reptile.getSfbookBasicByYA();
    }

    @Override
    public void saveGrowthData() throws Exception {
        logger.warn("开始存储每日增长信息");
        autoSaveGrowthData.saveGrowthData();
    }

    @Override
    public void delectGuGuAuthorDay() {
        DateUtil dateUtil = new DateUtil();
        String date = dateUtil.getAnyNowDate("yyyy-MM-dd ", 1);
        List<SfBook> sfBooks = sfBookMapper.findBookBydate(date);
        for (SfBook book : sfBooks) {
            long dayNum = dateUtil.getDistanceDays(date, book.getUpateDate());
            if (dayNum >= GUGU_DAY && !"已完结".equals(book.getStatus())) {
                logger.warn("《" + book.getBookName() + "》被判定为暂时太监");
                authorMapper.updateAuthorRightByBookName(book.getBookName(), 0);
            }
        }
    }

    @Override
    public void delectGuGuAuthorWeek() {
        List<Author> authors = authorMapper.findAll();
        String[] url = new String[authors.size()];
        for (int i = 0; i < authors.size(); i++) {
            ///筛选出地址书号
            String bookNo = authors.get(i).getUrl();
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(bookNo);
            bookNo = m.replaceAll("").trim();
            url[i] = "https://api.sfacg.com/novels/" + bookNo + "?expand=chapterCount,typeName,intro,fav,ticket,pointCount,tags,sysTag";
        }
        List<ProxyEntity> proxyEntities = proxyPool.getSomeUsebleProxy(10);
        if (proxyEntities == null) {
            return;
        }
        List<Proxy> proxies = proxyEntities.stream().map(proxyEntity -> {
            Proxy proxy = new Proxy(proxyEntity.getIp(), proxyEntity.getPort());
            return proxy;
        }).collect(Collectors.toList());
        lyb.setProxyProvider(new SimpleProxyProvider(proxies));
        Spider.create(deleceSfGuGuBook).thread(threadNum).setDownloader(lyb).addUrl(url).run();
    }

    @Override
    @Scheduled(cron = "3 * * * * ?")
//    @Scheduled(cron = "55 0 0 * * ?")
    public void runEveryDay() {
        logger.info("每日例行任务开始执行中....");
        getBookInfoTimedTask();
        try {
            saveGrowthData();
            dealWithSfErrorUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dealWithSfErrorUrl() {
        logger.info("开始处理错误连接");
        reptile.dealWithSfErrorUrl();
    }

    @Override
    @Scheduled(cron = "0 0 1 ? * 1 ")
    public void runEveryWeek() {
        delectGuGuAuthorWeek();
    }
}
