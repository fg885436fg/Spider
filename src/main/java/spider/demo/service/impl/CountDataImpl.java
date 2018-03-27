package spider.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.demo.domain.Mapper.GrowthDataMapper;
import spider.demo.domain.Mapper.SfBookMapper;
import spider.demo.domain.entity.SfBook;
import spider.demo.domain.entity.GrowthData;
import spider.demo.domain.vo.WhoAreYou;
import spider.demo.exception.MyException;
import spider.demo.service.CountData;

import java.util.ArrayList;
import java.util.List;

import static spider.demo.config.WhoAreYou.CLICK_INC;
import static spider.demo.config.WhoAreYou.MONTHLY_INC;
import static spider.demo.config.WhoAreYou.WORDS_INC;


/**
 * 计算数据类的实现类
 *
 * @author lanyubing1
 * @date 2018年2月3日
 */

@Service
public class CountDataImpl implements CountData {

    @Autowired
    SfBookMapper sfBookMapper;

    @Autowired
    GrowthDataMapper growthDataMapper;


    @Override
    public List<SfBook> allDateWeek (String bookName) {

        return sfBookMapper.findAllDateWeek(bookName);
    }

    @Override
    public List<SfBook> allDateMonth (String bookName) {

        return sfBookMapper.findAllDateMonth(bookName);
    }

    @Override
    public List<GrowthData> growthAllDay (String bookName) {
        List<SfBook> sfBooks = sfBookMapper.findAllDateWeek(bookName);
        return creatGrowthData(ONE_DAY, sfBooks);
    }

    @Override
    public List<GrowthData> growthAllweek (String bookName) throws Exception {


        try {

            List<SfBook> sfBooks = sfBookMapper.findAllDateWeek(bookName);
            List<GrowthData> growthDatas = creatGrowthData(WEEK_DAY, sfBooks);
            if (sfBooks.size() == 0) {
                throw new Exception();
            }
            return growthDatas;


        } catch (Exception ex) {

            MyException e = new MyException("");
            e.setStackTrace(ex.getStackTrace());
            e.setParm("查询书籍《" + bookName + "》错误");
            e.setReason("书籍不存在，或者书籍刚录入，因此只有一日数据,无法计算增长数据。");


            throw e;


        }


    }

    @Override
    public List<GrowthData> growthAllMonth (String bookName) throws Exception {
        try {

            List<SfBook> sfBooks = sfBookMapper.findAllDateMonth(bookName);
            List<GrowthData> growthDatas = creatGrowthData(MON_DAY, sfBooks);
            if (sfBooks.size() == 0) {
                throw new Exception();
            }
            return growthDatas;


        } catch (Exception ex) {

            MyException e = new MyException("");
            e.setStackTrace(ex.getStackTrace());
            e.setParm("查询书籍《" + bookName + "》错误");
            e.setReason("书籍不存在，或者书籍刚录入，只有一日数据，因此无法算出增长量。");
            throw e;


        }

    }

    @Override
    public List<GrowthData> growthAllMonthForConsole (String bookName) {

        List<SfBook> sfBooks = sfBookMapper.findAllDateMonth(bookName);
        return creatGrowthData(MON_DAY, sfBooks);
    }

    @Override
    public WhoAreYou countRank (String bookName, String parm) throws  Exception{

        List<GrowthData> growthDatas = new ArrayList<>();

        if (parm.equals(WORDS_INC)) {

            growthDatas = growthDataMapper.getBookIncSortByWord();

        } else if (parm.equals(CLICK_INC)) {

            growthDatas = growthDataMapper.getBookIncSortByClick();

        } else if (parm.equals(MONTHLY_INC)) {
             growthDatas = growthDataMapper.getBookIncSortByMonth();
        } else {

            throw  new MyException("parm","传入参数不规范");

        }





        return null;
    }


    private List<GrowthData> creatGrowthData (int dayNum, List<SfBook> sfBooks) {

        List<GrowthData> growthDatas = new ArrayList<>();

        if (dayNum > sfBooks.size() - 1) {

            dayNum = sfBooks.size() - 1;
        }

        for (int i = 0; i < dayNum; i++) {

            GrowthData growthData = new GrowthData();
            growthData.setDate(sfBooks.get(i + 1).getDate());
            growthData.setUpdateDay(sfBooks.get(i).getUpateDate());

            growthData.setBookName(sfBooks.get(i + 1).getBookName());
            //计算点击量增长
            growthData.setClictNumInc(sfBooks.get(i).getClickNum() - sfBooks.get(i + 1).getClickNum());
            //计算收藏量增长
            growthData.setCollectNumInc(sfBooks.get(i).getCollectNum() - sfBooks.get(i + 1).getCollectNum());

            //计算点赞量增长
            growthData.setLikeNumInc(sfBooks.get(i).getLikeNum() - sfBooks.get(i + 1).getLikeNum());
            //计算月票量增长
            growthData.setMonthlyNumInc(sfBooks.get(i).getMonthlyNum() - sfBooks.get(i + 1).getMonthlyNum());

            //计算字量增长
            growthData.setWordNumInc(sfBooks.get(i).getWordNum() - sfBooks.get(i + 1).getWordNum());

            growthData.setSign(sfBooks.get(i + 1).getSign());
            growthDatas.add(growthData);


        }


        return growthDatas;

    }






}
