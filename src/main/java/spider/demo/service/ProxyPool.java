package spider.demo.service;

import spider.demo.domain.entity.ProxyEntity;
import spider.demo.exception.MyException;

import java.util.List;

/**
 * 关于代理词的一系列操作
 *
 * @author: lanyubing
 * @create: 2018-07-12 10:29
 **/
public interface ProxyPool {

    /**
     * 获取所有的代理
     *
     * @return
     */
    List<ProxyEntity> getAllProxy();

    /**
     * 验证这个代理是否可用
     *
     * @param proxy
     * @return
     */
    boolean checkoutProxy(ProxyEntity proxy);

    /**
     * 得到可用的代理
     *
     * @param num 指定要获得多少条代理
     * @return
     */
    List<ProxyEntity> getSomeUsebleProxy(int num);
}
