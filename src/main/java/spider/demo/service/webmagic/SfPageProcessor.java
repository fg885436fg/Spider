package spider.demo.service.webmagic;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.demo.domain.mapper.SfBookMapper;
import spider.demo.domain.entity.SfBook;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.time.LocalDate;

/**
 * 每日爬取sf书籍页面，存储书籍基本信息
 *
 * @author lanyubing
 * @date 2018年2月1日
 */
@Component
public class SfPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Autowired
    private SfBookMapper sfBookMapper;

    @Override
    public void process (Page page) {
        Html html = page.getHtml();
        String wordNum = html.xpath("//span[@class='book_info3']/text()").regex("\\/.*字").regex("[0-9]{4,8}").toString().replace(" ", "");
        String clickNum = html.xpath("//span[@class='book_info3']/text()").regex("\\/ [1-9]\\d* ").regex("[1-9]\\d*|0$").toString().replace(" ", "");
        String upateDate = "20" + html.xpath("//span[@class='book_info3']/text()").regex("\\d{0,4}-\\d{2}-\\d{2}").toString().replace(" ", "");
        String monthlyNum = html.xpath("//ul[@class='book_interact']/li[4]/small/text()").toString().replace(" ", "");
        String collectNum = html.xpath("//ul[@class='book_interact']/li[1]/small/text()").toString().replace(" ", "");
        String likeNum = html.xpath("//ul[@class='book_interact']/li[2]/small/text()").toString().replace(" ", "");
        String date = LocalDate.now().toString();
        String status = html.xpath("//div[@class='book_info2']/span[2]/text()").toString().replace(" ", "");
        String bookName = html.xpath("//span[@class='book_newtitle']/text()").toString().replace(" ", "");
        String sign = html.xpath("//div[@class='book_info2']/span[3]/text()").toString().replace(" ", "");
        if (StringUtil.isBlank(sign)) {
            sign = "无";
        }
        SfBook sfBook = new SfBook(bookName, Long.valueOf(collectNum), Long.valueOf(clickNum),
                Long.valueOf(monthlyNum), Long.valueOf(likeNum), date, upateDate, status, Long.valueOf(wordNum),sign);

        sfBook.setSign(sign);

        SfBook book = sfBookMapper.findByNameAndDate(bookName, date);

        if (book == null) {
            sfBookMapper.insertAll(sfBook);
        }
    }

    @Override
    public Site getSite () {
        return site;
    }
}
