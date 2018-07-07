package spider.demo.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spider.demo.domain.mapper.AuthorCookieMapper;
import spider.demo.domain.mapper.AuthorMapper;
import spider.demo.domain.mapper.IncomeMapper;
import spider.demo.domain.entity.Author;
import spider.demo.domain.entity.AuthorCookie;
import spider.demo.service.Reptile;
import spider.demo.service.webmagic.AuthorPageProcessor;
import spider.demo.service.webmagic.SfPageIncome;
import spider.demo.service.webmagic.SfPageProcessor;
import spider.demo.service.webmagic.SfPageYa;
import spider.demo.tools.DateUtil;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * 实现类。
 *
 * @author lanyubing
 * @date 2018年2月1日
 */
@Service
public class ReptileImpl implements Reptile {
    @Autowired
    SfPageProcessor sfPageProcessor;
    @Autowired
    SfPageIncome sfPageIncome;

    @Autowired
    SfPageYa sfPageYa;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    AuthorPageProcessor authorPageProcessor;

    @Autowired
    AuthorCookieMapper authorCookieMapper;

    @Autowired
    IncomeMapper incomeMapper;


    protected static Logger logger = LoggerFactory.getLogger(ReptileImpl.class);


    /**
     * 0/30 * * * * ?  30秒一次
     * 0 0 1 * * ?      凌晨1点一次
     */
    @Override
    public void getSfbookBasicByYA() {
        int threadNum = 20;
        long startTime = System.currentTimeMillis();
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

        Spider.create(sfPageYa).thread(threadNum).addUrl(url).run();
        long endTime = System.currentTimeMillis();
        logger.info(threadNum + "根线程,书籍全部爬取花费时间为：" + (endTime - startTime) + "毫秒");

    }

    @Override
    public void getAuthorBook() {
        long startTime = System.currentTimeMillis();
        String[] url = new String[10000];
        for (int i = BOOK_FIRST_NUM; i < BOOK_NUM; i++) {
            int index = i % 10000;
            url[index] = "https://api.sfacg.com/novels/" + i + "?expand=chapterCount,typeName,intro,fav,ticket,pointCount,tags,sysTag";
            if (i % 10000 == 0 && i != BOOK_FIRST_NUM) {
                Spider.create(authorPageProcessor).thread(10).
                        addUrl(url).run();
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("10根线程,SF作者全部爬取花费时间为：" + (endTime - startTime) + "毫秒");
    }

    @Override
    public void getAuthorIncome() {
        List<AuthorCookie> authorCookies = authorCookieMapper.getAll();
        for (AuthorCookie authorCookie : authorCookies) {
            sfPageIncome.authorName = authorCookie.getAuthorName();
            incomeMapper.delectByAuthorName(authorCookie.getAuthorName());
            DateUtil d = new DateUtil();
            String[] urls = new String[mons];
            for (int i = 0; i < mons; i++) {
                urls[i] = "http://i.sfacg.com/income/c/1-" + d.getAnyMonDate("M-YYYY", i);
            }
            Spider.create(sfPageIncome).thread(10).addUrl(urls).run();
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ? ")
    public void getBookInfoTimedTask() {
        ExecutorService executor = newFixedThreadPool(2);
        //获取作书籍信息
        CompletableFuture.runAsync(() ->{
            getSfbookBasicByYA();
            executor.shutdown();
        },executor);
    }

    @Override
    @Scheduled(cron = "0 0 1 ? * 2 ")
    public void getAuthorInfoTimedTask() {
        ExecutorService executor = newFixedThreadPool(2);
        CompletableFuture.runAsync(() ->{
            getAuthorBook();
            executor.shutdown();
        },executor);
    }


}
