package spider.demo.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spider.demo.domain.entity.ProxyEntity;
import spider.demo.domain.mapper.AuthorCookieMapper;
import spider.demo.domain.mapper.AuthorMapper;
import spider.demo.domain.mapper.IncomeMapper;
import spider.demo.domain.entity.Author;
import spider.demo.domain.entity.AuthorCookie;
import spider.demo.service.webmagic.LybHttpClientDownloader;
import spider.demo.service.ProxyPool;
import spider.demo.service.Reptile;
import spider.demo.service.webmagic.*;
import spider.demo.tools.DateUtil;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    LybHttpClientDownloader lyb;
    @Autowired
    SfPageProcessor sfPageProcessor;
    @Autowired
    SfPageIncome sfPageIncome;
    @Autowired
    ProxyPage proxyPage;

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

    @Autowired
    ProxyPool proxyPool;

    @Autowired
    GetNotFeePage getNotFeePage;

    protected static Logger logger = LoggerFactory.getLogger(ReptileImpl.class);

    private int threadNum = 15;

    /**
     * 0/30 * * * * ?  30秒一次
     * 0 0 1 * * ?      凌晨1点一次
     */
    @Override
    public void getSfbookBasicByYA() {
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

        List<ProxyEntity> proxyEntities = proxyPool.getSomeUsebleProxy(10);
        if (proxyEntities == null) {
            return;
        }

        List<Proxy> proxies = proxyEntities.stream().map(proxyEntity -> {
            Proxy proxy = new Proxy(proxyEntity.getIp(), proxyEntity.getPort());
            return proxy;
        }).collect(Collectors.toList());
        lyb.setProxyProvider(new SimpleProxyProvider(proxies));
        Spider.create(sfPageYa).thread(threadNum).setDownloader(lyb).addUrl(url).run();
        long endTime = System.currentTimeMillis();
        logger.info(threadNum + "根线程,书籍全部爬取花费时间为：" + (endTime - startTime) + "毫秒");
    }

    @Override
    public void getAuthorBook() {
        long startTime = System.currentTimeMillis();

        List<Proxy> proxies = getProxy();
        lyb.setProxyProvider(new SimpleProxyProvider(proxies));
        String[] url = new String[10000];
        for (int i = BOOK_FIRST_NUM; i < BOOK_NUM; i++) {
            int index = i % 10000;
            url[index] = "https://api.sfacg.com/novels/" + i + "?expand=chapterCount,typeName,intro,fav,ticket,pointCount,tags,sysTag";
            if (i % 10000 == 0 && i != BOOK_FIRST_NUM) {
                Spider.create(authorPageProcessor).thread(threadNum).
                        addUrl(url).setDownloader(lyb).run();
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
    public List<Proxy> getProxy() {
        List<Proxy> proxies = new Vector<>();
        boolean isRun = true;
        while (isRun) {
            String url = "http://47.95.237.42:8080/api/get";
            Spider.create(proxyPage).thread(threadNum).addUrl(url).run();
            Proxy proxy = proxyPage.getProxy();
            if (proxy != null) {
                proxies.add(proxy);
                proxies = proxies.stream().distinct().collect(Collectors.toList());
            }
            if (proxies.size() == 10) {
                isRun = false;
            }
        }
        logger.warn("一共得到：" + proxies.size() + "个代理");
        logger.warn(proxies.toString());
        return proxies;
    }

    @Override
    public List<Proxy> getAllNoFeeProxy() {
        String url = "http://api3.xiguadaili.com/ip/?tid=558620514465611&num=50&delay=3&category=2&sortby=time&format=json";
        Spider.create(getNotFeePage).thread(1).addUrl(url).run();
        return getNotFeePage.getProxies();
    }
}
