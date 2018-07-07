package spider.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spider.demo.common.Msg;
import spider.demo.service.ForbiddenWordService;

/**
 * @author: lanyubing
 * @create: 2018-06-18 17:21
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpiderApplication.class)
@Transactional
public class testService {
    @Autowired
    ForbiddenWordService forbiddenWordService;

    @Test
    public void testService() {
        String text = "迷药迷药迷药迷药迷药迷药母猪母猪母猪母猪母猪母猪母猪母猪母猪母猪母猪";
        System.out.println("违禁词转换前：");
        System.out.println(text);
        String parting = ".";
        Msg msg = forbiddenWordService.ForbiddenWordConvertToPingYing(text);
        StringBuffer test = (StringBuffer) msg.getDataO();
        System.out.println("违禁词转换后：");
        System.out.println(test);
    }
}
