package spider.demo.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import spider.demo.service.impl.ReptileImpl;
import spider.demo.tools.Get;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * 全局异常处理
 * @author lanyubing
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    protected static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MyException.class)
    public ModelAndView defaultErrorHandler (HttpServletRequest req, MyException e) throws Exception {
        ModelAndView mav = new ModelAndView();


        StackTraceElement[] stackTraceElements;
        stackTraceElements = Arrays.copyOfRange(e.getStackTrace(), 0, 5);


        mav.addObject("exception", e);
        mav.addObject("parm", e.getParm());
        mav.addObject("message", Get.getExcrptionInfo(e) );
        mav.addObject("arrays", stackTraceElements);

        logger.error(e.getParm());
        logger.error(e.getReason());
        logger.error(Get.getExcrptionInfo(e));
        logger.error("");
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("myerror");
        return mav;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler (HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();


        StackTraceElement[] stackTraceElements;
        stackTraceElements = Arrays.copyOfRange(e.getStackTrace(), 0, 5);


        mav.addObject("exception", e);
        mav.addObject("parm", e.getCause().getMessage());
        mav.addObject("arrays", stackTraceElements);
        mav.addObject("message", Get.getExcrptionInfo(e) );


        logger.error(Get.getExcrptionInfo(e));
        logger.error("");
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }


}

