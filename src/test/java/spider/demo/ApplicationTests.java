package spider.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spider.demo.domain.AuthorMapper;
import spider.demo.domain.SfBookMapper;
import spider.demo.domain.entity.SfBook;
import spider.demo.domain.vo.GrowthData;
import spider.demo.service.CountData;
import spider.demo.service.Reptile;
import spider.demo.service.webmagic.AuthorPageProcessor;
import spider.demo.service.webmagic.SfPageProcessor;
import spider.demo.service.webmagic.SfPageYa;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private CountData countData;

    @Autowired
    SfPageProcessor sfPageProcessor;

    @Autowired
    AuthorPageProcessor authorPageProcessor;


    @Test
    @Rollback
    public void findByName () throws Exception {

    }

    @Test
    public void findAuthor () throws Exception {

        Spider.create(authorPageProcessor).thread(1).
                addUrl("https://api.sfacg.com/novels/110000?expand=chapterCount,typeName,intro,fav,ticket,pointCount,tags,sysTag").run();

    }


    @Test
    public void getSfbookBasic () throws Exception {
//        List< SfBook> sfBooks= sfBookMapper.findByName("性转为机械少女在异界的奇妙冒险");


        String a = "https://m.sfacg.com/b/120878/";

        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        m.replaceAll("").trim();


    }

    @Test
    public void sfPageProcessor () throws Exception {

        //      reptile.getSfbookBasicByYA();

        //  reptile.getAuthorBook();
    }

    @Test
    public void allDateWeek () throws Exception {
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
    @Rollback
    public void getSfbook () throws Exception {


//        List<SfBook> sfBooks = sfBookMapper.findByName("精灵女王的宠物少女");
 /*   List<SfBook> sfBooks = sfBookMapper.findByName("网游之与魔共舞");

        sfBooks.forEach(sfBook -> {
            System.out.println("书名：" + sfBook.getBookName());
            System.out.println("点击：" + sfBook.getClickNum());
            System.out.println("收藏：" + sfBook.getCollectNum());
            System.out.println("日期：" + sfBook.getDate());

        });*/

        List<GrowthData> growthDatas = countData.growthAllMonthForConsole("精灵女王的宠物少女");


        growthDatas.forEach(growthData -> {

            System.out.println("书名：" + growthData.getBookName());
            System.out.println("2018-02-06至" + growthData.getDay());
            System.out.println("字数增长量" + growthData.getWordNum());
            System.out.println("点击增长量" + growthData.getClictNumInc());
            System.out.println("月票增长量" + growthData.getMonthlyNumInc());
            System.out.println("收藏增长量" + growthData.getCollectNumInc() + "\n");

        });

        growthDatas = countData.growthAllMonthForConsole("用剑的魔法师");
        growthDatas.forEach(growthData -> {

            System.out.println("书名：" + growthData.getBookName());
            System.out.println(growthData.getDay());
            System.out.println("字数增长量" + growthData.getWordNum());
            System.out.println("点击增长量" + growthData.getClictNumInc());
            System.out.println("月票增长量" + growthData.getMonthlyNumInc());
            System.out.println("收藏增长量" + growthData.getCollectNumInc() + "\n");

        });
        growthDatas = countData.growthAllMonthForConsole("网游之与魔共舞");
        growthDatas.forEach(growthData -> {

            System.out.println("书名：" + growthData.getBookName());
            System.out.println(growthData.getDay());
            System.out.println("字数增长量" + growthData.getWordNum());
            System.out.println("点击增长量" + growthData.getClictNumInc());
            System.out.println("月票增长量" + growthData.getMonthlyNumInc());
            System.out.println("收藏增长量" + growthData.getCollectNumInc() + "\n");

        });


    }


    @Test
    public void getOneTest () {
        Spider.create(sfPageYa).thread(1).
                addUrl("https://api.sfacg.com/novels/" + 118281 + "" +
                        "?expand=chapterCount,typeName,intro,fav,ticket,pointCount,tags,sysTag").run();
    }


}