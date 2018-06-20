package spider.demo.service;

import spider.demo.common.Msg;

import java.util.List;

/**
 * 根据星号，查询违禁词所在句子
 *
 * @author: lanyubing
 * @create: 2018-06-08 18:14
 **/

public interface ForbiddenWordService {

    /**
     * 返回违禁词所在的段落
     *
     * @param stringList
     */
    List<String> findForbiddenParagraph(List<String> stringList);

    /**
     * 创建一个违禁词
     *
     * @param word
     * @param ip
     * @return
     */
    Msg creatFindForbiddenWord(String word, String ip);

    /**
     * 用于分割字符中的违禁词
     *
     * @param text    输入的字符串内容
     * @param parting 分割字符串的符号
     * @return
     */
    Msg replaceForbiddenWord(String text, String parting);

    /**
     * 违禁词的首字将变成拼音
     *
     * @param text    输入的字符串内容
     * @return
     */
    Msg ForbiddenWordConvertToPingYing(String text);

    /**
     *
     * 获取屏蔽词列表
     *
     * @return
     */
    Msg getAllForbiddenWord();
}
