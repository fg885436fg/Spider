package spider.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import spider.demo.domain.entity.ProxyEntity;
import spider.demo.service.webmagic.LybHttpClientDownloader;
import spider.demo.service.ProxyPool;
import spider.demo.service.StringRegex;
import spider.demo.service.webmagic.TestIpPage;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import static spider.demo.service.StringRegex.FIND_IP_PORT;

/**
 * @author: lanyubing
 * @create: 2018-07-12 10:36
 **/
@Service
public class ProxyPoolImpl implements ProxyPool {

    private static Logger logger = LoggerFactory.getLogger(ProxyPoolImpl.class);

    @Autowired
    TestIpPage testIpPage;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRegex stringRegex;

    @Override
    public List<ProxyEntity> getAllProxy() {
        Set keys = redisTemplate.keys("*");
        Iterator<String> it = keys.iterator();
        List<ProxyEntity> proxys = new ArrayList<>();
        while (it.hasNext()) {
            String key = it.next();
            if (stringRegex.checkString(FIND_IP_PORT, key)) {
                Object o = redisTemplate.opsForValue().get(key);
                if (o instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) o;
                    ProxyEntity proxyEntity = jsonObject.toJavaObject(ProxyEntity.class);
                    proxys.add(proxyEntity);
                }
            }
        }
        return proxys;
    }

    @Override
    public boolean checkoutProxy(ProxyEntity proxy) {
        String url = "http://2018.ip138.com/ic.asp";
        LybHttpClientDownloader lyb = new LybHttpClientDownloader();
        Proxy proxy1 = new Proxy(proxy.getIp(), proxy.getPort());
        lyb.setProxyProvider(SimpleProxyProvider.from(proxy1));
        Spider.create(testIpPage).thread(1).setDownloader(lyb).addUrl(url).run();
        if (proxy.getIp().equals(testIpPage.getIp()) && lyb.getIsSucc()) {
            return true;
        }
        return false;
    }

    @Override
    public List<ProxyEntity> getSomeUsebleProxy(int num) {
        List<ProxyEntity> proxyEntities = getAllProxy();
        List<ProxyEntity> tempList = new ArrayList<>();
        for (int i = 0; i < proxyEntities.size(); i++) {
            if (checkoutProxy(proxyEntities.get(i))) {
                tempList.add(proxyEntities.get(i));
                if (tempList.size() == num) {
                    return tempList;
                }
            }
        }
        if (tempList.size() > 0) {
            return tempList;
        } else {
            logger.error("getSomeUsebleProxy 错误==>");
            logger.error("找不到可用的代理");
            return null;
        }
    }
}
