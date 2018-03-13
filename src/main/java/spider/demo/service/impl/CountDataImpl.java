package spider.demo.service.impl;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.demo.domain.SfBookMapper;
import spider.demo.domain.entity.SfBook;
import spider.demo.domain.vo.EchartsVo;
import spider.demo.domain.vo.GrowthData;
import spider.demo.exception.MyException;
import spider.demo.service.CountData;

import java.util.ArrayList;
import java.util.List;


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
    public EchartsVo growthAllweek (String bookName) throws Exception {


        try {

            List<SfBook> sfBooks = sfBookMapper.findAllDateWeek(bookName);
            List<GrowthData> growthDatas = creatGrowthData(WEEK_DAY, sfBooks);
            return creatEchartsVo(growthDatas);


        } catch (Exception ex) {

            MyException e = new MyException("");
            e.setStackTrace(ex.getStackTrace());
            e.setParm("查询书籍《" + bookName + "》错误");
            e.setReason("书籍不存在，或者书籍刚录入，因此只有一日数据。");


            throw e;


        }


    }

    @Override
    public EchartsVo growthAllMonth (String bookName) throws Exception {
        try {

            List<SfBook> sfBooks = sfBookMapper.findAllDateMonth(bookName);
            List<GrowthData> growthDatas = creatGrowthData(MON_DAY, sfBooks);
            return creatEchartsVo(growthDatas);


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


    private EchartsVo creatEchartsVo (List<GrowthData> growthDatas) {


        int dayNum;

        EchartsVo echartsVo = new EchartsVo();
        long lowCollectNum = growthDatas.stream().min((g1, g2) -> (int) g1.getCollectNumInc() - (int) g2.getCollectNumInc()).get().getCollectNumInc();
        long lowClictNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getClictNumInc() - (int) g2.getClictNumInc()).get().getClictNumInc();
        long lowMonthlyNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getMonthlyNumInc() - (int) g2.getMonthlyNumInc()).get().getMonthlyNumInc();
        long lowtWordNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getWordNum() - (int) g2.getWordNum()).get().getWordNum();
        long lowtLikeNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getLikeNumInc() - (int) g2.getLikeNumInc()).get().getLikeNumInc();

        echartsVo.setLowtLikeNumInc(lowtLikeNumInc);
        echartsVo.setLowtWordNumInc(lowtWordNumInc);
        echartsVo.setLowMonthlyNumInc(lowMonthlyNumInc);
        echartsVo.setLowClictNumInc(lowClictNumInc);
        echartsVo.setLowCollectNum(lowCollectNum);


        dayNum = growthDatas.size();

        String[] xAxisdate = new String[dayNum];
        for (int i = 0; i < dayNum; i++) {
            xAxisdate[i] = growthDatas.get(i).getDay();

        }


        String[] clictNumInc = new String[dayNum];
        for (int i = 0; i < dayNum; i++) {
            clictNumInc[i] = String.valueOf(growthDatas.get(i).getClictNumInc());

        }

        String[] monthlyNumInc = new String[dayNum];
        for (int i = 0; i < dayNum; i++) {
            monthlyNumInc[i] = String.valueOf(growthDatas.get(i).getMonthlyNumInc());

        }


        String[] likeNumInc = new String[dayNum];
        for (int i = 0; i < dayNum; i++) {
            likeNumInc[i] = String.valueOf(growthDatas.get(i).getLikeNumInc());

        }
        String[] collectNumInc = new String[dayNum];
        for (int i = 0; i < dayNum; i++) {
            collectNumInc[i] = String.valueOf(growthDatas.get(i).getCollectNumInc());

        }

        String[] wordNumInc = new String[dayNum];
        for (int i = 0; i < dayNum; i++) {
            wordNumInc[i] = String.valueOf(growthDatas.get(i).getWordNum());

        }


        echartsVo.setxAxisdate(xAxisdate);
        echartsVo.setWordNumInc(wordNumInc);
        echartsVo.setMonthlyNumInc(monthlyNumInc);
        echartsVo.setCollectNumInc(collectNumInc);
        echartsVo.setLikeNumInc(likeNumInc);
        echartsVo.setClictNumInc(clictNumInc);

        echartsVo.setName(growthDatas.get(0).getBookName());

        return echartsVo;
    }

    public List<GrowthData> creatGrowthData (int dayNum, List<SfBook> sfBooks) {

        List<GrowthData> growthDatas = new ArrayList<>();

        if (dayNum > sfBooks.size() - 1) {

            dayNum = sfBooks.size() - 1;
        }

        for (int i = 0; i < dayNum; i++) {

            GrowthData growthData = new GrowthData();
            growthData.setDay(sfBooks.get(i + 1).getDate());

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
            growthData.setWordNum(sfBooks.get(i).getWordNum() - sfBooks.get(i + 1).getWordNum());

            growthDatas.add(growthData);


        }


        return growthDatas;

    }




}
