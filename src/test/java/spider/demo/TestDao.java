package spider.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spider.demo.domain.mapper.AuthorCookieMapper;
import spider.demo.domain.entity.AuthorCookie;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpiderApplication.class)
@Transactional
public class TestDao {
    @Autowired
    AuthorCookieMapper authorCookieMapper;

    @Test
    public void TestSfAuthorCookie() {
        Map<String, String> authorCookieMap = new HashMap<>();
        AuthorCookie authorCookie = new AuthorCookie("A", "SF");
        authorCookieMap.put(".SFCommunity", "咕咕咕咕");
        authorCookie.setAuthorCookieMap(authorCookieMap);
        System.out.println(authorCookieMapper.insertCookieMapper(authorCookie));
        System.out.println(authorCookieMapper.getByAuthorName("A").getAuthorName());
    }
}
