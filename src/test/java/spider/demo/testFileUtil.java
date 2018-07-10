package spider.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spider.demo.service.ForbiddenWordService;
import spider.demo.service.StringRegex;
import spider.demo.tools.DateUtil;
import spider.demo.tools.FileUtil;
import us.codecraft.webmagic.proxy.Proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: lanyubing
 * @create: 2018-05-16 14:57
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpiderApplication.class)
@Transactional
public class testFileUtil {

    @Autowired
    ForbiddenWordService forbiddenWordService;

    @Autowired
    StringRegex stringRegex;

    @Test
    public void readParagraphs() {
        String[] books = {"英雄1.txt", "英雄2.txt", "英雄3.txt", "英雄4.txt", "英雄5.txt", "英雄6.txt"};
        String path = "C:\\Users\\Administrator\\Desktop\\";
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < books.length; i++) {
            strings.addAll(FileUtil.readParagraphs(path + books[i]));
        }
        strings = forbiddenWordService.findForbiddenParagraph(strings);
        strings.forEach(s -> {
            System.out.println(s);
        });
    }

    @Test
    public void testUtil() {
        String test = null;
        try {
            test = PinyinHelper.convertToPinyinString("得到", "", PinyinFormat.WITHOUT_TONE);
        } catch (PinyinException e) {
            e.printStackTrace();
        }
        System.out.println(test);
    }

    @Test
    public void testStringRegex(){
        String testStr = "{\"success\":true,\"msg\":\"获取成功\",\"data\":{\"ip\":\"139.129.99.9\",\"port\":3128,\"location\":\"北京\",\"agentType\":\"高匿\",\"lastValidateTime\":1531011084583,\"usable\":true}}";
        JSONObject dateObject = JSON.parseObject(testStr).getJSONObject("data");
        Proxy proxy = new Proxy(dateObject.getString("ip"), dateObject.getInteger("port"));
        System.out.println(proxy);
    }

}
