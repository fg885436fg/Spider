package spider.demo.exception;


/**
 * 蓝宇冰的自定义异常
 *
 * @author lanyubing
 * @date 2018年2月22日
 */

public class MyException extends Exception {


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
