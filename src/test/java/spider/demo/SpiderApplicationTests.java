package spider.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spider.demo.domain.vo.SfCookie;
import spider.demo.tools.DateUtil;
import spider.demo.tools.NumProcess;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderApplicationTests {

    @Test
    public void contextLoads () {

        DateUtil d = new DateUtil();
        d.getAnyDate("yyyy-MM-dd", 1);
        System.out.println(d.getAnyDate("yyyy-MM-dd", 1).toString());


    }

}
