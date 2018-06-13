package spider.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.demo.service.FindForbiddenWord;
import spider.demo.service.StringRegex;

import java.util.List;

/**
 * @author: lanyubing
 * @create: 2018-06-12 17:20
 **/
@Service
public class FindForbiddenWordImpl implements FindForbiddenWord {
    @Autowired
    StringRegex stringRegex;

    @Override
    public void findForbiddenParagraph(List<String> stringList) {
        for (String word : stringList) {
            boolean wordHave = stringRegex.checkString(StringRegex.FIND_FORBIDDEN_REGEX, word);
            if (wordHave) {
                System.out.println(word);
            }
        }
    }
}
