package spider.demo.service.webmagic;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.demo.domain.AuthorMapper;
import spider.demo.domain.SfBookMapper;
import spider.demo.domain.entity.Income;
import spider.demo.domain.entity.SfBook;
import spider.demo.exception.MyException;
import spider.demo.service.impl.AutoSaveGrowthDataImpl;
import spider.demo.tools.DateUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
            .addCookie(".SFCommunity", "31EF3F56673ECDD3F4AEE2DAD9E6E41685F7024457D3240EE9FFC999689257E1D1C4250A56703D80495BE8D43675D3A28DED5747CF595BA306A130C5DEC38A796C14E88AD74EC19A84DA7238228CB339E27635FB5C2E305A10A3378005524C67");


    @Override
    synchronized public void process (Page page) {
        logger.info("开始抓取收入信息,地址是：" + page.getUrl().toString());

        List<Income> incomes = new ArrayList<>();
        Html html = page.getHtml();
        if (creatIncome(html) == null) {
            page.setSkip(true);
        }
        incomes.addAll(creatIncome(html));


        int hz = appearNumber(html.xpath("//div[@class='pagebar']").toString(), "<a href=\"/income/");


        DateUtil d = new DateUtil();
        List<String> links = new ArrayList<>();
        for (int i = 2; i <= hz; i++) {
            String rl = "http://i.sfacg.com/income/c/" + i;
            links.add(rl + "-" + d.getSfDate());
        }
        if (links.size() != 0) {
            page.addTargetRequests(links);
        }

        Income income1 = new Income();
        for (int i = 0; i < incomes.size(); i++) {
            Income income = incomes.get(i);
            income1.setIncome(income.getIncome()+income1.getIncome());
            income1.setChapterNum(income.getChapterNum()+income1.getChapterNum());
            income1.setDate(income.getDate());

        }
        System.out.println("日期："+income1.getDate());
        System.out.println("总收入"+income1.getIncome());
        System.out.println("总章数："+income1.getChapterNum());


    }

    @Override
    synchronized public Site getSite () {
        return site;
    }


    /**
     * 创建收入实体类
     *
     * @param html 抓取到的页面。
     * @return
     */
    private List<Income> creatIncome (Html html) {

        if (html.regex("定时更新").toString() == null) {

            logger.info("抓取收入页面出错，可能是Cookies过期。");
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
                    System.out.println(td.text());
                }


                if (td.hasText()) {
                    j++;
                    if (j == 1) {
                        income.setDate(td.text());
                    } else if (j == 2) {
                        income.setChapterNum(Integer.parseInt(td.text().replace("章", "")));
                    } else if (j == 3) {
                        income.setIncome(Double.parseDouble(td.text()));
                    }
                }

            }
            if(!StringUtils.isEmpty(income.getDate())) {
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
    private int appearNumber (String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }
}
