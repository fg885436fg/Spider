package spider.demo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 *
 * @author: lanyubing
 * @create: 2018-06-17 15:08
 **/
public class Msg {
    public static final String CODE_SUCC = "success";
    public static final String CODE_FAIL = "fail";

    /**
     * 返回的状态码
     */
    private String code;

    /**
     * 返回的描述或提示
     */
    private String desc;

    /**
     * 返回的数据，json格式
     */
    private String data;

    @JsonIgnore
    private Object dataO;
    /**
     * 详细解释
     */
    private String explanation;

    public Msg(String code, String desc, String data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public Msg(String code, String desc) {
        this.code = code;
        this.desc = desc;
        this.data = "";
    }

    public Msg(String code, String desc, String data, Object dataO) {
        this.code = code;
        this.desc = desc;
        this.data = data;
        this.dataO = dataO;
    }

    public Msg(String code, String desc, String data, Object dataO, String explanation) {
        this.code = code;
        this.desc = desc;
        this.data = data;
        this.dataO = dataO;
        this.explanation = explanation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Object getDataO() {
        return dataO;
    }

    public void setDataO(Object dataO) {
        this.dataO = dataO;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
