package spider.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.demo.domain.mapper.AuthorCookieMapper;
import spider.demo.domain.entity.AuthorCookie;
import spider.demo.exception.MyException;
import spider.demo.service.IncomeService;
import spider.demo.service.Reptile;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现类
 *
 * @author lanyubing
 * @date 2018年4月20日
 */
@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    AuthorCookieMapper authorCookieMapper;

    @Autowired
    Reptile reptile;

    @Override
    public void addSfAuthorCookie(String authorName, String cookie, String site) throws Exception {
        Map<String, String> authorCookieMap = new HashMap<>();
        if ("SF".equals(site)) {
            authorCookieMap.put(".SFCommunity", cookie);
        }
        AuthorCookie authorCookie = new AuthorCookie(authorName, site);
        authorCookie.setAuthorCookieMap(authorCookieMap);
        authorCookieMapper.delectByAuthorName(authorName);
        if (authorCookieMapper.insertCookieMapper(authorCookie) != 1) {
            throw new MyException("authorName:"+authorName+" cookie:"+" site:"+site,"参数可能出错，确定cookie是否正确");
        }
        reptile.getAuthorIncome();
    }
}
