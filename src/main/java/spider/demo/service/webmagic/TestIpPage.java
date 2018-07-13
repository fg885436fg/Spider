package spider.demo.service.webmagic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.demo.service.StringRegex;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author: lanyubing
 * @create: 2018-07-12 12:03
 **/
@Component
public class TestIpPage implements PageProcessor {

    @Autowired
    StringRegex stringRegex;
    private String ip;

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(30000).
            addHeader("Authorization", "Basic YW5kcm9pZHVzZXI6MWEjJDUxLXl0Njk7KkFjdkBxeHE=")
            .addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:58.0) Gecko/20100101 Firefox/58.0");

    @Override
    public void process(Page page) {
        String a = page.getHtml().xpath("/html/body/center/text()").toString();
        this.ip = stringRegex.returnRegexString(StringRegex.FIND_IP, a);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public String getIp() {
        return ip;
    }
}
