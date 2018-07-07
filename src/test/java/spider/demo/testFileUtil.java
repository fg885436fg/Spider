package spider.demo;

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
        String forbiddenWord ="4打";
        System.out.println(stringRegex.checkString(StringRegex.FIND_NO_CHINESE, forbiddenWord));
    }

}
