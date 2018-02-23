package spider.demo.domain.entity;




/**
 * sf小说实体类。
 *
 * @author lanyubing
 * @date 2018年2月1日
 */
public class SfBook {

    public SfBook (String bookName, long collectNum, long clickNum, long monthlyNum, long likeNum, String date, String upateDate, String status,long wordNum,String sign) {
        this.bookName = bookName;
        this.collectNum = collectNum;
        this.clickNum = clickNum;
        this.monthlyNum = monthlyNum;
        this.likeNum = likeNum;
        this.date = date;
        this.upateDate = upateDate;
        this.status = status;
        this.wordNum=wordNum;
        this.sign =sign;
    }




    public SfBook () {
    }

    private long id;

    public long getId () {
        return id;
    }

    public void setId (long id) {
        this.id = id;
    }

    //书名
    private String bookName;

    public String getBookName () {
        return bookName;
    }

    public void setBookName (String bookName) {
        this.bookName = bookName;
    }

    //收藏数
    private long collectNum;

    public long getCollectNum () {
        return collectNum;
    }

    public void setCollectNum (long collectNum) {
        this.collectNum = collectNum;
    }


    //点击数
    private long clickNum;

    public long getClickNum () {
        return clickNum;
    }

    public void setClickNum (long clickNum) {
        this.clickNum = clickNum;
    }

    //月票数
    private long monthlyNum;

    public long getMonthlyNum () {
        return monthlyNum;
    }

    public void setMonthlyNum (long monthlyNum) {
        this.monthlyNum = monthlyNum;
    }

    //点赞数
    private long likeNum;

    public long getLikeNum () {
        return likeNum;
    }

    public void setLikeNum (long likeNum) {
        this.likeNum = likeNum;
    }

    //日期
    private String date;

    public String getDate () {
        return date;
    }

    public void setDate (String date) {
        this.date = date;
    }

    //小说更新日期
    private String upateDate;

    public String getUpateDate () {
        return upateDate;
    }

    public void setUpateDate (String upateDate) {
        this.upateDate = upateDate;
    }

    //小说状态
    private String status;

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    /**
     * 小说字数
     */
    private long wordNum;

    public long getWordNum () {
        return wordNum;
    }

    public void setWordNum (long wordNum) {
        this.wordNum = wordNum;
    }


    /**
     * 是否签约或者VIP
     */

    private  String  sign;

    public String getSign () {
        return sign;
    }

    public void setSign (String sign) {
        this.sign = sign;
    }




}
