package spider.demo.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.demo.domain.Mapper.GrowthDataMapper;
import spider.demo.domain.Mapper.IncomeMapper;
import spider.demo.domain.Mapper.SfBookMapper;
import spider.demo.domain.entity.Income;
import spider.demo.domain.entity.SfBook;
import spider.demo.domain.entity.GrowthData;
import spider.demo.domain.vo.WhoAreYou;
import spider.demo.exception.MyException;
import spider.demo.service.CountData;
import spider.demo.tools.DateUtil;

import java.util.ArrayList;
import java.util.HashSet;
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

    @Autowired
    IncomeMapper incomeMapper;


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

            MyException e = new MyException("查询书籍《" + bookName + "》错误",
                    "书籍不存在，或者书籍刚录入，因此只有一日数据,无法计算增长数据。");
            e.setStackTrace(ex.getStackTrace());
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
    public WhoAreYou countRank (String bookName, String parm, boolean vip) throws Exception {

        List<GrowthData> growthDatas = new ArrayList<>();
        DateUtil d = new DateUtil();
        String date = d.getAnyDate("yyyy-MM-dd",1);
//        //TODO 测试日期
//        date = "2018-03-27";
        if (parm.equals(WORDS_INC)) {

            if (vip) {
                growthDatas = growthDataMapper.getVipBookIncSortByWord(date);
            } else {
                growthDatas = growthDataMapper.getBookIncSortByWord(date);
            }


        } else if (parm.equals(CLICK_INC)) {

            if (vip) {
                growthDatas = growthDataMapper.getVipBookIncSortByMonth(date);
            } else {
                growthDatas = growthDataMapper.getBookIncSortByMonth(date);
            }

            growthDatas = growthDataMapper.getBookIncSortByClick(date);

        } else if (parm.equals(MONTHLY_INC)) {

            if (vip) {
                growthDatas = growthDataMapper.getVipBookIncSortByMonth(date);

            } else {

                growthDatas = growthDataMapper.getBookIncSortByMonth(date);
            }

        } else {

            throw new MyException(parm, "传入参数不规范");
        }


        return creatRank(growthDatas, bookName,parm);
    }


    private WhoAreYou creatRank (List<GrowthData> growthDatas, String bookName,String parm) throws Exception {
        try {
            GrowthData growthData = new GrowthData();
            growthData = growthDatas.stream().filter(growth -> growth.getBookName().equals(bookName))
                    .findFirst().get();

            int rank = growthDatas.indexOf(growthData);
            WhoAreYou whoAreYou = new WhoAreYou(growthDatas.size(), rank);
            whoAreYou.setType(parm);
            return whoAreYou;

        } catch (Exception e) {

            throw new MyException("查询书籍《" + bookName + "》排位信息错误",
                    "书籍不存在。可能原因有：1.书籍刚录入，无法计算增长数据。\n 2.只计算最近一个月更新的小说。",
                    e.getStackTrace());
        }

    }


    @Override
    public List<Income> getMonIncome (String authorName, int mons) {
        DateUtil d = new DateUtil();
        d.getAnyMonDate("yyyy-MM", mons);
        return incomeMapper.getByAuthorNameAndDate(authorName, d.getAnyMonDate("yyyy-MM", mons));

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
