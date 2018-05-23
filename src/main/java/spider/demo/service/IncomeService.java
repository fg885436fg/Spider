package spider.demo.service;

/**
 * 处理输入收入的业务接口
 * @date 2018年4月20日
 * @author lanyubing
 */
public interface IncomeService {
    /**
     * 存储SF作者的实体类。
     * @param authorName 作者命
     * @param cookie
     * @param site
     * @return
     * @throws Exception
     */
    void addSfAuthorCookie(String authorName,String cookie,String site) throws Exception;
}

