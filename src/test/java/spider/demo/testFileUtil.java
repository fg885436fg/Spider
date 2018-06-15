package spider.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spider.demo.service.FindForbiddenWord;
import spider.demo.tools.DateUtil;
import spider.demo.tools.FileUtil;

import java.io.File;
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
    FindForbiddenWord findForbiddenWord;

    @Test
    public void readParagraphs() {
        String[] books = {"英雄1.txt", "英雄2.txt", "英雄3.txt", "英雄4.txt", "英雄5.txt", "英雄6.txt"};
        String path = "C:\\Users\\Administrator\\Desktop\\";
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < books.length; i++) {
            strings.addAll(FileUtil.readParagraphs(path + books[i]));
        }
        strings = findForbiddenWord.findForbiddenParagraph(strings);
        strings.forEach(s -> {
            System.out.println(s);
        });
    }

    @Test
    public void testUtil() {
        DateUtil dateUtil = new DateUtil();
        Date date = dateUtil.getNowDate();
    }
}
