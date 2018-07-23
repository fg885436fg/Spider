package spider.demo.domain.dao;

import spider.demo.common.Msg;
import spider.demo.domain.entity.ErrorUrl;

import java.util.List;

/**
 * 对错误URL表
 *
 * @author: lanyubing
 * @create: 2018-07-13 14:17
 **/
public interface ErrorUrlDao {

    /**
     * 创建一个错误URL记录，如果已有则返回失败
     *
     * @param url 失败的URL地址
     * @return
     */
    Msg creatErrorUrl(String url);

    /**
     * 获取所有错误请求
     *
     * @return
     */
    List<ErrorUrl> getAllErrorUrl();

    /**
     * 根据错误连接类型删除
     * @param typeName
     */
    void deleteErrorUrlByType(String typeName);
}
