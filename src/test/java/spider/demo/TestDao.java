package spider.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spider.demo.domain.entity.ForbiddenWord;
import spider.demo.domain.entity.ForbiddenWordExample;
import spider.demo.domain.mapper.AuthorCookieMapper;
import spider.demo.domain.entity.AuthorCookie;
import spider.demo.domain.mapper.ForbiddenWordMapper;
import spider.demo.tools.DateUtil;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpiderApplication.class)
@Transactional
public class TestDao {
    @Autowired
    AuthorCookieMapper authorCookieMapper;
    @Autowired
    ForbiddenWordMapper forbiddenWordMapper;

    @Test
    public void TestSfAuthorCookie() {
        Map<String, String> authorCookieMap = new HashMap<>();
        AuthorCookie authorCookie = new AuthorCookie("A", "SF");
        authorCookieMap.put(".SFCommunity", "咕咕咕咕");
        authorCookie.setAuthorCookieMap(authorCookieMap);
        System.out.println(authorCookieMapper.insertCookieMapper(authorCookie));
        System.out.println(authorCookieMapper.getByAuthorName("A").getAuthorName());
    }

    @Test
    @Rollback(false)
    public void TestForbiddenWordMapper() {
        String[] words = {"发情","樱桃","八九"};
        for (int i = 0; i < words.length; i++) {
            ForbiddenWord forbiddenWord = new ForbiddenWord();
            DateUtil dateUtil = new DateUtil();
            Date date = dateUtil.getNowDate();

            forbiddenWord.setDate(date);
            forbiddenWord.setWord(words[i]);
            int result = forbiddenWordMapper.insert(forbiddenWord);
            System.out.println(result);
        }

    }
}
