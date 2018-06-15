package spider.demo.service;

import java.util.List;

/**
 * 根据星号，查询违禁词所在句子
 *
 * @author: lanyubing
 * @create: 2018-06-08 18:14
 **/

public interface FindForbiddenWord {

    /**
     * 返回违禁词所在的段落
     *
     * @param stringList
     */
    List<String> findForbiddenParagraph(List<String> stringList);

}
