package spider.demo.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spider.demo.common.Msg;
import spider.demo.domain.vo.WhoAreYou;
import spider.demo.service.ForbiddenWordService;
import spider.demo.service.StringRegex;
import spider.demo.tools.IpUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 违禁词控制类
 *
 * @author: lanyubing
 * @create: 2018-06-20 09:33
 **/
@RestController
@RequestMapping("/forbidden")
public class ForbiddenWordController {

    @Autowired
    ForbiddenWordService forbiddenWordService;

    @Autowired
    StringRegex stringRegex;

    @PostMapping("/replace")
    public ResponseEntity replaceForbiddenWord(@RequestParam(value = "txt",required = false) String txt) throws Exception {
        int MAX_WORD_NUM = 5000;
        Msg msg = null;
        if (txt.length() > MAX_WORD_NUM) {
            msg = new Msg(Msg.CODE_FAIL, "上传文章大小长度不能超过5000");
        } else {
            msg = forbiddenWordService.ForbiddenWordConvertToPingYing(txt);
        }
        return ResponseEntity.ok(JSON.toJSONString(msg));
    }

    @PostMapping("/creat")
    public ResponseEntity creatForbiddenWord(@RequestParam(value = "word") String forbiddenWord, HttpServletRequest request) throws Exception {
        Msg msg = null;
        if (stringRegex.checkString(StringRegex.FIND_NO_CHINESE, forbiddenWord)||StringUtils.isEmpty(forbiddenWord)) {
            msg = new Msg(Msg.CODE_FAIL, "不能输入非中文!");
        } else {
            msg = forbiddenWordService.creatFindForbiddenWord(forbiddenWord, IpUtil.getIpAddr(request));
        }
        return  ResponseEntity.ok(JSON.toJSONString(msg));
    }

    @GetMapping("/get")
    public String getAllForbiddenWord() throws Exception {
        Msg msg = forbiddenWordService.getAllForbiddenWord();
        return JSON.toJSONString(msg);
    }

    @RequestMapping("")
    public ModelAndView forbiddenWordPage() throws Exception {
        ModelAndView modelAndView = new ModelAndView("forbidden/forbiddenword");
        return modelAndView;
    }


}
