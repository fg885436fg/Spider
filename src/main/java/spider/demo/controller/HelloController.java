package spider.demo.controller;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import spider.demo.domain.vo.EchartsVo;
import spider.demo.service.CountData;
import spider.demo.service.DataHandle;

/**
 * 查询书籍的控制层入口
 * @author lanyubing
 */

@RestController
public class HelloController {
    @Autowired
    private CountData countData;

    @Autowired
    private DataHandle dataHandle;

    @RequestMapping("/bookname" )
    public ModelAndView getBook (String bookname) throws Exception {

        if (StringUtil.isBlank(bookname)) {
            bookname = "性转为机械少女在异界的奇妙冒险 ";
        }


        EchartsVo echartsVo = dataHandle.creatEchartsVo( countData.growthAllweek(bookname));
        ModelAndView modelAndView = new ModelAndView("echarts");
        modelAndView.addObject("date", echartsVo);
        return modelAndView;
    }


}