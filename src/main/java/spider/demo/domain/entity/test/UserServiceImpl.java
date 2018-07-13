package spider.demo.domain.entity.test;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author: lanyubing
 * @create: 2018-07-11 10:27
 **/
@Service
public class UserServiceImpl implements UserService {
    @Override
    @Cacheable(value = "user", key = "'user_'+#username")
    public User getUser(String userName) {
        System.out.println(userName + "进入实现类获取数据！");
        return new User("Ttomm", 22);
    }
}
