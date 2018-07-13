package spider.demo.domain.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import spider.demo.common.Msg;
import spider.demo.domain.dao.ErrorUrlDao;
import spider.demo.domain.entity.ErrorUrl;
import spider.demo.domain.entity.ErrorUrlExample;
import spider.demo.domain.mapper.ErrorUrlMapper;
import spider.demo.service.StringRegex;
import spider.demo.tools.DateUtil;


import java.util.*;

import static spider.demo.service.impl.StringRegexImpl.URL_MAP;

/**
 * @author: lanyubing
 * @create: 2018-07-13 14:20
 **/
@Repository
public class ErrorUrlDaoImpl implements ErrorUrlDao {

    @Autowired
    StringRegex stringRegex;
    @Autowired
    ErrorUrlMapper errorUrlMapper;

    @Override
    public Msg creatErrorUrl(String url) {
        DateUtil dateUtil = new DateUtil();
        ErrorUrl errorUrl = new ErrorUrl();
        errorUrl.setUrl(url);
        errorUrl.setDate(dateUtil.getNowFreeFormatterDate("yyyy-MM-dd"));
        Set keys = URL_MAP.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String val =iterator.next();
            if (stringRegex.checkString(val, url)) {
                errorUrl.setType(URL_MAP.get(val));
            }
        }
        ErrorUrlExample errorUrlExample = new ErrorUrlExample();
        errorUrlExample.createCriteria().andUrlEqualTo(url).andDateEqualTo(errorUrl.getDate());
        if (errorUrlMapper.selectByExample(errorUrlExample).size() > 0) {
            errorUrlMapper.updateByExampleSelective(errorUrl, errorUrlExample);
            return new Msg(Msg.CODE_SUCC, "已存在，现已更新成功");
        } else {
            errorUrlMapper.insert(errorUrl);
            return new Msg(Msg.CODE_SUCC, "创建成功");
        }
    }
}
