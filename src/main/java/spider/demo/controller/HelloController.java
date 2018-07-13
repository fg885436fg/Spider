package spider.demo.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import spider.demo.common.Msg;
import spider.demo.domain.entity.ForbiddenWord;
import spider.demo.domain.vo.BookIncEchartsVo;
import spider.demo.domain.vo.IncomeEchartsVo;
import spider.demo.domain.vo.WhoAreYou;
import spider.demo.exception.MyException;
import spider.demo.service.*;
import spider.demo.tools.IpUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    IncomeService incomeService;
    @Autowired
    private DataHandle dataHandle;
    @Autowired
    private ForbiddenWordService forbiddenWordService;
    @Autowired
    private Reptile reptile;
    @Autowired
    private ProxyPool proxyPool;

    /**
     * 查询书籍增长信息入口
     *
     * @param bookname 书名
     * @return
     * @throws Exception
     */

    @RequestMapping("/bookname")
    public ModelAndView getBook(String bookname, Integer weekNum) throws Exception {
        if (StringUtil.isBlank(bookname) || weekNum == null) {
            bookname = "性转为机械少女在异界的奇妙冒险 ";
            weekNum = 1;
        }
        bookname = StringUtils.deleteWhitespace(bookname);
        BookIncEchartsVo bookIncEchartsVo = dataHandle.creatWeekEchartsVo(countData.growthAllMonth(bookname), weekNum);
        ModelAndView modelAndView = new ModelAndView("bookIncEcharts");
        modelAndView.addObject("date", bookIncEchartsVo);
        Msg msg = forbiddenWordService.getAllForbiddenWord();
        List<ForbiddenWord> forbiddenWords = (List<ForbiddenWord>) msg.getDataO();
        modelAndView.addObject("forbiddenWordList", forbiddenWords);
        return modelAndView;
    }

    @RequestMapping("/rank")
    public ModelAndView getRank(String rankBookName, String parm, boolean vip) throws Exception {
        WhoAreYou whoAreYou = countData.countRank(rankBookName, parm, vip);
        ModelAndView modelAndView = new ModelAndView("rank");
        whoAreYou.setFuckRate(whoAreYou.getFuckRate());
        modelAndView.addObject("data", whoAreYou);
        modelAndView.addObject("bookName", rankBookName);
        return modelAndView;
    }


    @RequestMapping("/income")
    public ModelAndView getIncome(String authorName, Integer mons) throws Exception {
        if (StringUtil.isBlank(authorName)) {
            authorName = "兰玉边 ";
            mons = 1;
        }
        IncomeEchartsVo incomeEchartsVo = dataHandle.creatIncomeEchartsVo(countData.getMonIncome(authorName, mons));
        ModelAndView modelAndView = new ModelAndView("IncomeEcharts");
        modelAndView.addObject("date", incomeEchartsVo);
        return modelAndView;
    }

    @RequestMapping("/writeincome")
    public ModelAndView writeIncome(String authorName, String cookie, String site) throws Exception {
        if (StringUtil.isBlank(authorName) || StringUtil.isBlank(cookie) || StringUtil.isBlank(site)) {
            throw new MyException("authorName:" + authorName + " Cookie:" + cookie, "参数为空" + " site:" + site);
        }
        incomeService.addSfAuthorCookie(authorName, cookie, site);
        IncomeEchartsVo incomeEchartsVo = dataHandle.creatIncomeEchartsVo(countData.getMonIncome("兰玉边", 0));
        ModelAndView modelAndView = new ModelAndView("IncomeEcharts");
        modelAndView.addObject("date", incomeEchartsVo);
        return modelAndView;
    }
    @RequestMapping("/test")
    public String test() throws Exception {
        return  proxyPool.getSomeUsebleProxy(5).toString();
    }
    @RequestMapping("/testProxy")
    public String testProxy( HttpServletRequest request) throws Exception {
        System.out.println("检测到IP为"+IpUtil.getIpAddr(request));
        return "测试完成";
    }

}