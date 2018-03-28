package spider.demo.exception;


/**
 * 蓝宇冰的自定义异常
 *
 * @author lanyubing
 * @date 2018年2月22日
 */

public class MyException extends Exception {


    public MyException () {
    };

    public MyException (String parm, String reason,StackTraceElement[] stackTraceElements) {
        this.parm = parm;
        this.reason = reason;
        this.setStackTrace(stackTraceElements);
    };

    public MyException (String parm, String reason) {
        this.parm = parm;
        this.reason = reason;
    };

    //输入的参数
    private String parm;

    public String getParm () {
        return parm;
    }

    public void setParm (String parm) {
        this.parm = parm;
    }

    //可能原因
    private String reason;

    public String getReason () {
        return reason;
    }

    public void setReason (String reason) {
        this.reason = reason;
    }

    public MyException (String message) {
        super(message);
    }

}
