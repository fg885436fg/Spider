package spider.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.demo.common.Msg;
import spider.demo.domain.dao.ForbiddenWordDao;
import spider.demo.domain.entity.ForbiddenWord;
import spider.demo.domain.vo.Paragraph;
import spider.demo.exception.GlobalExceptionHandler;
import spider.demo.service.ForbiddenWordService;
import spider.demo.service.StringRegex;
import spider.demo.tools.DateUtil;
import spider.demo.tools.ExceptionGet;
import spider.demo.tools.FileUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

/**
 * @author: lanyubing
 * @create: 2018-06-12 17:20
 **/
@Service
public class ForbiddenWordServiceImpl implements ForbiddenWordService {
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Autowired
    StringRegex stringRegex;

    @Autowired
    ForbiddenWordDao forbiddenWordDao;

    @Override
    public List<String> findForbiddenParagraph(List<String> stringList) {
        List<String> strings = new ArrayList<>();
        for (String word : stringList) {
            boolean wordHave = stringRegex.checkString(StringRegex.FIND_FORBIDDEN_REGEX, word);
            if (wordHave) {
                strings.add(word);
            }
        }
        return strings;
    }

    @Override
    public Msg creatFindForbiddenWord(String word, String ip) {
        DateUtil dateUtil = new DateUtil();
        ForbiddenWord forbiddenWord = new ForbiddenWord(word, dateUtil.getNowDate(), ip);
        return forbiddenWordDao.creatForbiddenWord(forbiddenWord);
    }


    @Override
    public Msg replaceForbiddenWord(String text, String parting) {
        List<String> paragraphs = FileUtil.readParagraphsFromString(text);
        List<CompletionStage<Paragraph>> completionStages = new ArrayList<>();
        for (int i = 0; i < paragraphs.size(); i++) {
            String paragraph = paragraphs.get(i);
            Paragraph p = new Paragraph(paragraph, i);
            completionStages.add(replaceForbiddenWordInParagraph(parting, p));
        }

        List<Paragraph> paragraphList = new ArrayList<>();
        for (CompletionStage<Paragraph> stage : completionStages) {
            paragraphList.add(stage.toCompletableFuture().join());
        }
        StringBuffer result = new StringBuffer();
        paragraphList.sort((o1, o2) -> (o1.getIndex() - o2.getIndex()));
        for (Paragraph paragraph : paragraphList) {
            result.append(paragraph.getContent());
        }
        Msg msg = new Msg(Msg.CODE_SUCC, "和谐完毕", JSON.toJSONString(result), result);
        return msg;

    }

    @Override
    public Msg ForbiddenWordConvertToPingYing(String text) {
        List<String> paragraphs = FileUtil.readParagraphsFromString(text);
        List<CompletionStage<Paragraph>> completionStages = new ArrayList<>();
        for (int i = 0; i < paragraphs.size(); i++) {
            String paragraph = paragraphs.get(i);
            Paragraph p = new Paragraph(paragraph, i);
            completionStages.add(fWordConvertToPyInParagraph(p));
        }

        List<Paragraph> paragraphList = new ArrayList<>();
        for (CompletionStage<Paragraph> stage : completionStages) {
            paragraphList.add(stage.toCompletableFuture().join());
        }
        StringBuffer result = new StringBuffer();
        paragraphList.sort((o1, o2) -> (o1.getIndex() - o2.getIndex()));
        for (Paragraph paragraph : paragraphList) {
            result.append("&nbsp&nbsp&nbsp&nbsp");
            result.append(paragraph.getContent());
            result.append("<br>");
        }
        Msg msg = new Msg(Msg.CODE_SUCC, "和谐完毕", JSON.toJSONString(result), result);
        return msg;
    }

    @Override
    public Msg getAllForbiddenWord() {
        Msg msg = forbiddenWordDao.getAllForbiddenWord();
        List<ForbiddenWord> forbiddenWords = (List<ForbiddenWord>) msg.getDataO();
        msg.setData(JSON.toJSONStringWithDateFormat(forbiddenWords, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue));
        return msg;
    }

    /**
     * 将段落中的违禁词变成拼音
     *
     * @param paragraph
     * @return
     */
    private CompletionStage<Paragraph> fWordConvertToPyInParagraph(Paragraph paragraph) {
        Msg msg = forbiddenWordDao.getAllForbiddenWord();
        List<ForbiddenWord> forbiddenWords = (List<ForbiddenWord>) msg.getDataO();
        for (ForbiddenWord forbiddenWord : forbiddenWords) {
            StringBuffer sb = new StringBuffer(forbiddenWord.getWord());
            String pingYin = null;
            try {
                pingYin = PinyinHelper.convertToPinyinString(sb.substring(0, 1).toString(), "", PinyinFormat.WITH_TONE_MARK);
            } catch (Exception e) {
                logger.error("fWordConvertToPyInParagraph 错误 ==>");
                logger.error("参数:"+sb.toString());
                logger.error(ExceptionGet.getExcrptionInfo(e));
                e.printStackTrace();
            }
            sb.replace(0, 1, pingYin);
            String content = paragraph.getContent();
            if (content.indexOf(forbiddenWord.getWord()) != -1) {
                content = paragraph.getContent().replace(forbiddenWord.getWord(), sb.toString());
                paragraph.setContent(content);
            }
        }
        return CompletableFuture.completedFuture(paragraph);
    }

    /**
     * 在一个段落里查找其中的违禁词
     *
     * @param parting
     * @param paragraph
     * @return
     */
    private CompletionStage<Paragraph> replaceForbiddenWordInParagraph(String parting, Paragraph paragraph) {
        Msg msg = forbiddenWordDao.getAllForbiddenWord();
        List<ForbiddenWord> forbiddenWords = (List<ForbiddenWord>) msg.getDataO();
        for (ForbiddenWord forbiddenWord : forbiddenWords) {
            StringBuffer sb = new StringBuffer(forbiddenWord.getWord());
            sb.insert(1, parting);
            String content = paragraph.getContent();
            if (content.indexOf(forbiddenWord.getWord()) != -1) {
                content = paragraph.getContent().replace(forbiddenWord.getWord(), sb.toString());
                paragraph.setContent(content);
            }
        }
        return CompletableFuture.completedFuture(paragraph);
    }

}
