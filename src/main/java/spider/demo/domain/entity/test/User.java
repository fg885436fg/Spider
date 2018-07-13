package spider.demo.domain.entity.test;

import java.io.Serializable;

/**
 * @author: lanyubing
 * @create: 2018-07-11 10:18
 **/
public class User implements Serializable {
    private static final long serialVersionUID = -1L;
    private String username;
    private Integer age;

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}
