package spider.demo.service.webmagic;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.demo.domain.mapper.SfBookMapper;
import spider.demo.domain.entity.SfBook;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.time.LocalDate;

/**
 * 每日爬取sf书籍页面，存储书籍基本信息
 *
 * @author lanyubing
 * @date 2018年2月1日
 */
@Component
public class SfPageYa implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(3000).setCharset("UTF-8").
            addHeader("Authorization", "Basic YW5kcm9pZHVzZXI6MWEjJDUxLXl0Njk7KkFjdkBxeHE=")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
    @Autowired
    private SfBookMapper sfBookMapper;

    @Override
    public void process(Page page) {
        if (!page.isDownloadSuccess()) {
            return;
        }
        ;
        String jsonStr = page.getJson().toString();
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        JSONObject jsonData = jsonObject.getJSONObject("data");
        String sign = jsonData.getString("signStatus");
        long wordNum = jsonData.getLong("charCount");
        String bookName = jsonData.getString("novelName");
        bookName = StringUtils.deleteWhitespace(bookName);
        long monthlyNum = jsonData.getJSONObject("expand").getLong("ticket");
        long likeNum = jsonData.getJSONObject("expand").getLong("fav");
        String status = "连载中";
        Boolean isFinish = jsonData.getBoolean("isFinish");
        if (isFinish) {
            status = "已完结";
        }
        long clickNum = jsonData.getLong("viewTimes");
        long collectNum = jsonData.getLong("markCount");
        String upateDate = jsonData.getString("lastUpdateTime");
        upateDate = upateDate.substring(0, 10);
        String date = LocalDate.now().toString();
        SfBook sfBook = new SfBook(bookName, collectNum, clickNum,
                monthlyNum, likeNum, date, upateDate, status, wordNum, sign);
        SfBook book = sfBookMapper.findByNameAndDate(bookName, date);
        if (book == null) {
            sfBookMapper.insertAll(sfBook);
        } else {
            System.out.println("书籍：《" + book.getBookName() + "》今日已经爬取过");
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
