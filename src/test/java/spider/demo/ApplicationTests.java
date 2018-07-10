package spider.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spider.demo.domain.mapper.AuthorCookieMapper;
import spider.demo.domain.mapper.AuthorMapper;
import spider.demo.domain.mapper.GrowthDataMapper;
import spider.demo.domain.mapper.SfBookMapper;
import spider.demo.domain.entity.SfBook;
import spider.demo.service.AutoSaveGrowthData;
import spider.demo.service.CountData;
import spider.demo.service.Reptile;
import spider.demo.service.webmagic.AuthorPageProcessor;
import spider.demo.service.webmagic.SfPageProcessor;
import spider.demo.service.webmagic.SfPageYa;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.proxy.Proxy;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpiderApplication.class)
@Transactional
public class ApplicationTests {

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


    @Test
    public void findByName() throws Exception {

    }

    @Test
    public void findAuthor() throws Exception {

        Spider.create(authorPageProcessor).thread(1).
                addUrl("https://api.sfacg.com/novels/110000?expand=chapterCount,typeName,intro,fav,ticket,pointCount,tags,sysTag").run();

    }


    @Test
    public void getSfbookBasic() throws Exception {
//        List< SfBook> sfBooks= sfBookMapper.findByName("性转为机械少女在异界的奇妙冒险");
        String a = "https://m.sfacg.com/b/120878/";
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        m.replaceAll("").trim();


    }

    @Test
    public void sfPageProcessor() throws Exception {

    }

    @Test
    public void allDateWeek() throws Exception {
        List<SfBook> sfBooks = countData.allDateMonth("精灵女王的宠物少女");


        sfBooks.forEach(sfBook -> {

            System.out.println(sfBook.getBookName());
            System.out.println("点击增长量" + sfBook.getClickNum());
            System.out.println("收藏增长量" + sfBook.getCollectNum());
            System.out.println("赞数增长量" + sfBook.getLikeNum());
            System.out.println("月票增长量" + sfBook.getMonthlyNum());
            System.out.println("日期" + sfBook.getDate());
        });

    }


    @Test
    public void getSfbook() throws Exception {


//        LocalDate today = LocalDate.now();
//        List<String> bookNames = sfBookMapper.findAllByDate(today.minusDays(1).toString());
//        bookNames.addAll(sfBookMapper.findAllByDate(today.minusDays(2).toString()));
//        bookNames.addAll(sfBookMapper.findAllByDate(today.minusDays(3).toString()));
//
//        bookNames = bookNames.stream().distinct().collect(Collectors.toList());
//
//        int size = bookNames.size();
//        System.out.println(size + "本");
//
//        for (String bookName : bookNames) {
//
//            List<GrowthData> growthDatas = countData.growthAllweek(bookName);
//            growthDatamapper.insertIncBatch(growthDatas);
//            System.out.println(growthDatamapper.findByName(bookName).get(0).getBookName());
//        }


    }


    @Test
    public void getOneTest() throws Exception {

        List<SfBook> sfBooks = sfBookMapper.findByName("性转为叉叉叉").stream().filter(sfBook -> sfBook.getDate() == "dsadas").collect(Collectors.toList());
        if (sfBooks.size() != 0) {

        } else {
            System.out.println("成功");
        }

    }

    @Test
    @Rollback(false)
    public void getSfbookBasicByYA() {
       reptile.getSfbookBasicByYA();
        try {
            autoSaveGrowthData.saveGrowthData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}