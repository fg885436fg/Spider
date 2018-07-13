package spider.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spider.demo.tools.DateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderApplicationTests {

    @Test
    public void contextLoads () {

        DateUtil d = new DateUtil();
        d.getAnyNowDate("yyyy-MM-dd", 1);
        System.out.println(d.getAnyNowDate("yyyy-MM-dd", 1).toString());


    }

}
