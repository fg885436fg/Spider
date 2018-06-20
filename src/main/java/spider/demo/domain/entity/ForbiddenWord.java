package spider.demo.domain.entity;

import java.util.Date;

/**
 * 违禁词实体类
 */
public class ForbiddenWord {
    private Integer id;

    private String word;

    private Date date;

    private String ip;

    public ForbiddenWord() {
    }

    public ForbiddenWord(String word, Date date, String ip) {
        this.word = word;
        this.date = date;
        this.ip = ip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}