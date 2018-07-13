package spider.demo;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spider.demo.domain.entity.ProxyEntity;
import spider.demo.domain.entity.test.UserService;
import spider.demo.service.StringRegex;

import java.util.HashSet;

import static spider.demo.service.StringRegex.FIND_IP_PORT;

/**
 * @author: lanyubing
 * @create: 2018-07-11 10:40
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class testRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private StringRegex stringRegex;

    @Test
    public void setAndGet() {
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

    }


}
