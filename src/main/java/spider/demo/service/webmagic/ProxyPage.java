package spider.demo.service.webmagic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;

import java.util.List;
import java.util.Vector;

/**
 * @author: lanyubing
 * @create: 2018-07-08 09:24
 **/
@Component
public class ProxyPage implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3);
    private Proxy proxy;

    @Override
    public void process(Page page) {
        String jsonStr = page.getJson().toString();
        JSONObject dateObject = JSON.parseObject(jsonStr).getJSONObject("data");
        this.proxy = new Proxy(dateObject.getString("ip"), dateObject.getInteger("port"));
        page.setSkip(true);
    }

    public Proxy getProxy() {
        return proxy;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
