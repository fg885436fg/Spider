package spider.demo.domain.dao.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import spider.demo.common.Msg;
import spider.demo.domain.dao.ForbiddenWordDao;
import spider.demo.domain.entity.ForbiddenWord;
import spider.demo.domain.entity.ForbiddenWordExample;
import spider.demo.domain.mapper.ForbiddenWordMapper;
import spider.demo.tools.FileUtil;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * @author: lanyubing
 * @create: 2018-06-17 15:13
 **/
@Repository
public class ForbiddenWordDaoImpl implements ForbiddenWordDao {

    @Autowired
    ForbiddenWordMapper forbiddenWordMapper;

    @Override
    public Msg creatForbiddenWord(ForbiddenWord word) {
        ForbiddenWordExample forbiddenWordExample = new ForbiddenWordExample();
        forbiddenWordExample.or().andWordEqualTo(word.getWord());
        if (forbiddenWordMapper.countByExample(forbiddenWordExample) > 0) {
            return new Msg(Msg.CODE_FAIL, "违禁词已存在");
        }
        forbiddenWordMapper.insert(word);
        return new Msg(Msg.CODE_SUCC, "违禁词添加成功");
    }

    @Override
    public Msg getAllForbiddenWord() {
        ForbiddenWordExample forbiddenWordExample = new ForbiddenWordExample();
        forbiddenWordExample.or().andWordIsNotNull();
        List<ForbiddenWord> forbiddenWords = forbiddenWordMapper.selectByExample(forbiddenWordExample);
        Msg msg = new Msg(Msg.CODE_SUCC, "所有违禁词查询成功", JSON.toJSONString(forbiddenWords), forbiddenWords);
        return msg;
    }

}
