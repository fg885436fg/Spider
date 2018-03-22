package spider.demo.service.impl;

import org.springframework.stereotype.Service;
import spider.demo.domain.vo.EchartsVo;
import spider.demo.domain.entity.GrowthData;
import spider.demo.service.DataHandle;

import java.util.List;

/**
 * 用于加工数据的类
 * @date 2018年3月16日
 * @author lanyubing
 */
@Service
public class DataHandleImpl implements DataHandle {
    @Override
    public EchartsVo creatEchartsVo (List<GrowthData> growthDatas) throws Exception {

        int dayNum;

        EchartsVo echartsVo = new EchartsVo();
        long lowCollectNum = growthDatas.stream().min((g1, g2) -> (int) g1.getCollectNumInc() - (int) g2.getCollectNumInc()).get().getCollectNumInc();
        long lowClictNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getClictNumInc() - (int) g2.getClictNumInc()).get().getClictNumInc();
        long lowMonthlyNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getMonthlyNumInc() - (int) g2.getMonthlyNumInc()).get().getMonthlyNumInc();
        long lowtWordNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getWordNumInc() - (int) g2.getWordNumInc()).get().getWordNumInc();
        long lowtLikeNumInc = growthDatas.stream().min((g1, g2) -> (int) g1.getLikeNumInc() - (int) g2.getLikeNumInc()).get().getLikeNumInc();

        echartsVo.setLowtLikeNumInc(lowtLikeNumInc);
        echartsVo.setLowtWordNumInc(lowtWordNumInc);
        echartsVo.setLowMonthlyNumInc(lowMonthlyNumInc);
        echartsVo.setLowClictNumInc(lowClictNumInc);
        echartsVo.setLowCollectNum(lowCollectNum);


        dayNum = growthDatas.size();

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


        echartsVo.setxAxisdate(xAxisdate);
        echartsVo.setWordNumInc(wordNumInc);
        echartsVo.setMonthlyNumInc(monthlyNumInc);
        echartsVo.setCollectNumInc(collectNumInc);
        echartsVo.setLikeNumInc(likeNumInc);
        echartsVo.setClictNumInc(clictNumInc);

        echartsVo.setName(growthDatas.get(0).getBookName());

        return echartsVo;
    }
}
