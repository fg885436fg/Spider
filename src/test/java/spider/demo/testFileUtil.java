package spider.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spider.demo.service.FindForbiddenWord;
import spider.demo.tools.FileUtil;

import java.io.File;
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
    FindForbiddenWord findForbiddenWord ;
    @Test
    public void readParagraphs() {
        List<String> strings = FileUtil.readParagraphs("C:\\Users\\Administrator\\Desktop\\测试1.txt");
        findForbiddenWord.findForbiddenParagraph(strings);
    }
}
