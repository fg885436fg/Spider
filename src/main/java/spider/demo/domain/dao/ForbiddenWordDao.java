package spider.demo.domain.dao;

import org.springframework.stereotype.Repository;
import spider.demo.common.Msg;
import spider.demo.domain.entity.ForbiddenWord;

/**
 * 违禁词dao类
 *
 * @author: lanyubing
 * @create: 2018-06-17 15:03
 **/

public interface ForbiddenWordDao {

    /**
     * 创建一个屏蔽词记录，如果已有则返回失败
     *
     * @param word
     * @return
     */
    Msg creatForbiddenWord(ForbiddenWord word);

    /**
     * 返回所有的违禁词
     *
     * @return
     */
    Msg getAllForbiddenWord();

}
