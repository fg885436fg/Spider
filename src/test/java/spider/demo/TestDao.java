package spider.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spider.demo.common.Msg;
import spider.demo.domain.dao.ErrorUrlDao;
import spider.demo.domain.dao.ForbiddenWordDao;
import spider.demo.domain.entity.*;
import spider.demo.domain.mapper.AuthorCookieMapper;
import spider.demo.domain.mapper.ErrorUrlMapper;
import spider.demo.domain.mapper.ForbiddenWordMapper;
import spider.demo.tools.DateUtil;

import javax.crypto.MacSpi;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpiderApplication.class)
@Transactional
public class TestDao {
    @Autowired
    AuthorCookieMapper authorCookieMapper;
    @Autowired
    ForbiddenWordMapper forbiddenWordMapper;
    @Autowired
    ForbiddenWordDao forbiddenWordDao;
    @Autowired
    ErrorUrlMapper errorUrlMapper;
    @Autowired
    ErrorUrlDao errorUrlDao;

    @Test
    public void TestSfAuthorCookie() {
//        Map<String, String> authorCookieMap = new HashMap<>();
//        AuthorCookie authorCookie = new AuthorCookie("A", "SF");
//        authorCookieMap.put(".SFCommunity", "咕咕咕咕");
//        authorCookie.setAuthorCookieMap(authorCookieMap);
//        System.out.println(authorCookieMapper.insertCookieMapper(authorCookie));
//        System.out.println(authorCookieMapper.getByAuthorName("A").getAuthorName());
    }

    @Test
    @Rollback(false)
    public void TestForbiddenWordMapper() {
        List<ErrorUrl> errorUrls = new ArrayList<>();
        DateUtil dateUtil = new DateUtil();
        errorUrls = errorUrlDao.getAllErrorUrl();
        errorUrls.forEach(errorUrl -> {
            System.out.println(errorUrl.getDate().toString());
        });
        errorUrls = errorUrlDao.getAllErrorUrl().stream().
                filter(errorUrl -> errorUrl.getDate().toString().equals(dateUtil.getNowFreeFormatterDate("yyyy-MM-dd").toString()))
                .collect(Collectors.toList());
        System.out.println(errorUrls);
    }
}
