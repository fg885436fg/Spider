package spider.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spider.demo.domain.mapper.AuthorCookieMapper;
import spider.demo.domain.mapper.AuthorMapper;
import spider.demo.domain.mapper.GrowthDataMapper;
import spider.demo.domain.mapper.SfBookMapper;
import spider.demo.service.AutoSaveGrowthData;
import spider.demo.service.CountData;
import spider.demo.service.IncomeService;
import spider.demo.service.Reptile;
import spider.demo.service.webmagic.AuthorPageProcessor;
import spider.demo.service.webmagic.SfPageProcessor;
import spider.demo.service.webmagic.SfPageYa;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpiderApplication.class)
@Transactional
public class TestContoller {
    @Autowired
    SfPageYa sfPageYa;

    @Autowired
    private SfBookMapper sfBookMapper;
    @Autowired
    private Reptile reptile;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private GrowthDataMapper growthDataMapper;
    @Autowired
    private CountData countData;
    @Autowired
    SfPageProcessor sfPageProcessor;
    @Autowired
    AuthorPageProcessor authorPageProcessor;
    @Autowired
    AutoSaveGrowthData autoSaveGrowthData;
    @Autowired
    AuthorCookieMapper authorCookieMapper;
    @Autowired
    IncomeService incomeService;

    @Test
    public void testAddSfAuthorCookie() {

    }



}
