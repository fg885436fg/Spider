package spider.demo.service.webmagic;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.demo.domain.Mapper.AuthorMapper;

import spider.demo.domain.entity.Author;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * 自动搜集获取作者的书籍
 *
 * @author lanyubing
 * @date 2018年2月3日
 */
@Component
public class AuthorPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).
            addHeader("Authorization", "Basic YW5kcm9pZHVzZXI6MWEjJDUxLXl0Njk7KkFjdkBxeHE=")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");


    @Autowired
    private AuthorMapper authorMapper;


    protected static Logger logger = LoggerFactory.getLogger(AuthorPageProcessor.class);

    @Override
    synchronized public void process (Page page) {


        String jsonStr = page.getJson().toString();
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        JSONObject jsonData = jsonObject.getJSONObject("data");
        long wordNum = jsonData.getLong("charCount");


        String upateDate = jsonData.getString("lastUpdateTime");
        upateDate = upateDate.substring(0, 10);
        //比较更新时间与现在的时间差，如果相差过大，则不录入。
        String date = LocalDate.now().toString();
        long day = getDistanceDays(date, upateDate);


        Author authorObject = null;
        String bookName = jsonData.getString("novelName");
        bookName = StringUtils.deleteWhitespace(bookName);

        authorObject = authorMapper.findByBookName(bookName);

        String sign = jsonData.getString("signStatus");


        //&& !"普通".equals(sign) 筛选签约或者VIP的条件。
        //只收录近45天更新字数大于50000的
        if (authorObject == null && day < 45 && wordNum > 50000) {
            authorObject = new Author();
            authorObject.setUrl(page.getUrl().toString());
            authorObject.setAuthorName(jsonData.getString("authorName"));
            authorObject.setBookName(bookName);
            authorMapper.insertAll(authorObject);

            logger.info("《" + bookName + "》在作者表中增添成功");
        } else if (authorObject != null && "普通".equals(sign) && wordNum < 50000) {


            if (authorMapper.delectByBookName(bookName) > 0) {

                logger.info("《" + bookName + "》在作者表中删除成功");

            } else {

                logger.info("《" + bookName + "》在作者表中删除失败");
            }


        }


    }

    @Override
    synchronized public Site getSite () {
        return site;
    }


    private static long getDistanceDays (String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }


}
