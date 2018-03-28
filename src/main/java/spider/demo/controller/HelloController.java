package spider.demo.controller;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import spider.demo.domain.vo.BookIncEchartsVo;
import spider.demo.domain.vo.IncomeEchartsVo;
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
    public ModelAndView getBook (String bookname) throws Exception {

        if (StringUtil.isBlank(bookname)) {
            bookname = "性转为机械少女在异界的奇妙冒险 ";
        }

        BookIncEchartsVo bookIncEchartsVo = dataHandle.creatEchartsVo(countData.growthAllweek(bookname));
        ModelAndView modelAndView = new ModelAndView("bookIncEcharts");
        modelAndView.addObject("date", bookIncEchartsVo);
        return modelAndView;
    }

    @RequestMapping("/income")
    public ModelAndView getIncome (String authorName, Integer mons) throws Exception {

        if (StringUtil.isBlank(authorName)) {
            authorName = "兰玉边 ";
            mons = 1;
        }
        IncomeEchartsVo incomeEchartsVo =
                dataHandle.creatIncomeEchartsVo(countData.getMonIncome(authorName,   mons));


        ModelAndView modelAndView = new ModelAndView("IncomeEcharts");
        modelAndView.addObject("date", incomeEchartsVo);
        return modelAndView;
    }


}