package spider.demo.service.webmagic;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.demo.domain.Mapper.AuthorCookieMapper;
import spider.demo.domain.Mapper.IncomeMapper;
import spider.demo.domain.entity.AuthorCookie;
import spider.demo.domain.entity.Income;
import spider.demo.domain.vo.SfCookie;
import spider.demo.tools.DateUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 每日爬取sf作者结算
 *
 * @author lanyubing
 * @date 2018年2月1日
 */
@Component
public class SfPageIncome implements PageProcessor {
    protected static Logger logger = LoggerFactory.getLogger(SfPageIncome.class);

    public String authorName;
    @Autowired
    AuthorCookieMapper authorCookieMapper;

    @Autowired
    IncomeMapper incomeMapper;

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);


    @Override
    synchronized public void process(Page page) {
        logger.info("开始抓取收入信息,地址是：" + page.getUrl().toString());
        List<Income> incomes = new ArrayList<>();
        Html html = page.getHtml();
        List<Income> temp = creatIncome(html);
        if (temp == null) {
            return;
        }
        incomes.addAll(temp);
        int hz = appearNumber(html.xpath("//div[@class='pagebar']").toString(), "<a href=\"/income/");
        List<String> links = new ArrayList<>();
        StringBuilder url = new StringBuilder(page.getUrl().toString());
        for (int i = 2; i <= hz && i < 5; i++) {
            links.add(url.replace(28, 29, String.valueOf(i)).toString());
        }
        if (links.size() != 0) {
            page.addTargetRequests(links);
        }
        if (incomes.size() != 0) {
            incomeMapper.insertIncBatch(incomes);
        }
    }

    @Override
    synchronized public Site getSite() {
        site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        AuthorCookie authorCookie = authorCookieMapper.getByAuthorName(authorName);
        SfCookie sfCookie = new SfCookie();
        sfCookie.setSfCookie(authorCookie.getCookieJson());
        for (String key : sfCookie.getSfCookie().keySet()) {
            site.addCookie(key, sfCookie.getSfCookie().get(key));
        }
        return site;
    }


    /**
     * 创建收入实体类
     *
     * @param html 抓取到的页面。
     * @return
     */
    private List<Income> creatIncome(Html html) {
        if (html.regex("定时更新").toString() == null) {
            logger.info("抓取收入页面出错，可能是Cookies过期。");
            logger.info("authorName:" + authorName);
            logger.info("_________________");
            logger.info(html.toString());
            logger.info("_________________");
            return null;
        }
        Document document = html.getDocument();
        Elements incomeList = document.getElementsByTag("tbody").get(0).children();
        List<Income> incomes = new ArrayList<>();
        for (int i = 0; i < incomeList.size(); i++) {
            Element tr = incomeList.get(i);
            int j = 0;
            Income income = new Income();
            for (Element td : tr.children()) {
                if (td.hasAttr("width")) {
                    continue;
                }
                if (td.hasText()) {
                    j++;
                    if (j == 1) {
                        income.setDate(td.text());
                    } else if (j == 2) {
                        income.setChapterNum(Long.valueOf(td.text().replace("章", "")));
                    } else if (j == 3) {
                        income.setIncome(Double.parseDouble(td.text()));
                    }
                }

            }
            income.setAuthorName(authorName);
            //UserInfo
            if (!StringUtils.isEmpty(income.getDate())) {
                incomes.add(income);
            }

        }
        return incomes;
    }


    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText  源字符串
     * @param findText 要查找的字符串
     * @return
     */
    private int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }
}
