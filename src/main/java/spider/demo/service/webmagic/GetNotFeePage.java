package spider.demo.service.webmagic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lanyubing
 * @create: 2018-07-13 09:54
 **/
@Component
public class GetNotFeePage implements PageProcessor {

    private List<Proxy> proxies;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1500).
            addHeader("Authorization", "Basic YW5kcm9pZHVzZXI6MWEjJDUxLXl0Njk7KkFjdkBxeHE=")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

    @Override
    public void process(Page page) {
        JSONArray jsonArray = JSONArray.parseArray(page.getJson().toString());
        List<Proxy> proxies = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String host = jsonArray.getJSONObject(i).getString("host");
            Integer ip = jsonArray.getJSONObject(i).getInteger("port");
            Proxy proxy = new Proxy(host,ip);
            proxies.add(proxy);
        }
        this.proxies = proxies;
    }

    @Override
    public Site getSite() {
        return site;
    }

    public List<Proxy> getProxies() {
        return proxies;
    }
}
