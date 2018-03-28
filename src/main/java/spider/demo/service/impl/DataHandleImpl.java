package spider.demo.service.impl;

import org.springframework.stereotype.Service;
import spider.demo.domain.entity.Income;
import spider.demo.domain.vo.BookIncEchartsVo;
import spider.demo.domain.entity.GrowthData;
import spider.demo.domain.vo.IncomeEchartsVo;
import spider.demo.service.DataHandle;

import java.util.List;

/**
 * 用于加工数据的类
 *
 * @author lanyubing
 * @date 2018年3月16日
 */
@Service
public class DataHandleImpl implements DataHandle {
    @Override
    public BookIncEchartsVo creatEchartsVo (List<GrowthData> growthDatas) throws Exception {


        BookIncEchartsVo bookIncEchartsVo = new BookIncEchartsVo();
        long lowCollectNum = growthDatas.stream().min((g1, g2) -> (int) g1.getCollectNumInc() - (int) g2.getCollectNumInc()).get().getCollectNumInc();
        long lowClictNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getClictNumInc() - (int) g2.getClictNumInc()).get().getClictNumInc();
        long lowMonthlyNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getMonthlyNumInc() - (int) g2.getMonthlyNumInc()).get().getMonthlyNumInc();
        long lowtWordNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getWordNumInc() - (int) g2.getWordNumInc()).get().getWordNumInc();
        long lowtLikeNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getLikeNumInc() - (int) g2.getLikeNumInc()).get().getLikeNumInc();

        bookIncEchartsVo.setLowtLikeNumInc(lowtLikeNumInc);
        bookIncEchartsVo.setLowtWordNumInc(lowtWordNumInc);
        bookIncEchartsVo.setLowMonthlyNumInc(lowMonthlyNumInc);
        bookIncEchartsVo.setLowClictNumInc(lowClictNumInc);
        bookIncEchartsVo.setLowCollectNum(lowCollectNum);


        int dayNum = growthDatas.size();

        String[] xAxisdate = new String[dayNum];
        for (int i = 0; i < dayNum; i++) {
            xAxisdate[i] = growthDatas.get(i).getDate();

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
            wordNumInc[i] = String.valueOf(growthDatas.get(i).getWordNumInc());

        }


        bookIncEchartsVo.setxAxisdate(xAxisdate);
        bookIncEchartsVo.setWordNumInc(wordNumInc);
        bookIncEchartsVo.setMonthlyNumInc(monthlyNumInc);
        bookIncEchartsVo.setCollectNumInc(collectNumInc);
        bookIncEchartsVo.setLikeNumInc(likeNumInc);
        bookIncEchartsVo.setClictNumInc(clictNumInc);

        bookIncEchartsVo.setName(growthDatas.get(0).getBookName());

        return bookIncEchartsVo;
    }

    @Override
    public IncomeEchartsVo creatIncomeEchartsVo (List<Income> incomes) throws Exception {

        int dataSize = incomes.size();
        String authorName = incomes.get(0).getAuthorName();

        String[] xAxisdate = new String[dataSize];
        for (int i = 0; i < dataSize; i++) {
            xAxisdate[i] = incomes.get(i).getDate();
        }

        String[] chapterNum = new String[dataSize];
        for (int i = 0; i < dataSize; i++) {
            chapterNum[i] = String.valueOf(incomes.get(i).getChapterNum());
        }

        String[] income = new String[dataSize];
        for (int i = 0; i < dataSize; i++) {
            income[i] = String.valueOf(incomes.get(i).getIncome());
        }

        IncomeEchartsVo incomeEntity = new IncomeEchartsVo(authorName, xAxisdate, chapterNum, income);


        return incomeEntity;
    }
}
