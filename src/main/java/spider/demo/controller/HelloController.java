package spider.demo.controller;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import spider.demo.domain.vo.BookIncEchartsVo;
import spider.demo.domain.vo.IncomeEchartsVo;
import spider.demo.domain.vo.WhoAreYou;
import spider.demo.service.CountData;
import spider.demo.service.DataHandle;

/**
 * 查询书籍的控制层入口
 *
 * @author lanyubing
 */

@RestController
public class HelloController {
    @Autowired
    private CountData countData;


    @Autowired
    private DataHandle dataHandle;

    /**
     * 查询书籍增长信息入口
     *
     * @param bookname 书名
     * @return
     * @throws Exception
     */

    @RequestMapping("/bookname")
    public ModelAndView getBook (String bookname ,Integer weekNum) throws Exception {

        if (StringUtil.isBlank(bookname)||weekNum ==null ) {
            bookname = "性转为机械少女在异界的奇妙冒险 ";
            weekNum=1;
        }

        BookIncEchartsVo bookIncEchartsVo = dataHandle.creatWeekEchartsVo(countData.growthAllMonth(bookname),weekNum);
        ModelAndView modelAndView = new ModelAndView("bookIncEcharts");
        modelAndView.addObject("date", bookIncEchartsVo);
        return modelAndView;
    }

    @RequestMapping("/rank")
    public ModelAndView getRank (String rankBookName, String parm, boolean vip) throws Exception {


        WhoAreYou whoAreYou = countData.countRank(rankBookName, parm, vip);

        ModelAndView modelAndView = new ModelAndView("rank");
        whoAreYou.setFuckRate(whoAreYou.getFuckRate());
        modelAndView.addObject("data", whoAreYou);
        modelAndView.addObject("bookName",rankBookName);
        return modelAndView;
    }


    @RequestMapping("/income")
    public ModelAndView getIncome (String authorName, Integer mons) throws Exception {

        if (StringUtil.isBlank(authorName)) {
            authorName = "兰玉边 ";
            mons = 1;
        }
        IncomeEchartsVo incomeEchartsVo =
                dataHandle.creatIncomeEchartsVo(countData.getMonIncome(authorName, mons));


        ModelAndView modelAndView = new ModelAndView("IncomeEcharts");
        modelAndView.addObject("date", incomeEchartsVo);
        return modelAndView;
    }


}