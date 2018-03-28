package spider.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spider.demo.domain.vo.SfCookie;
import spider.demo.tools.NumProcess;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderApplicationTests {

	@Test
	public void contextLoads() {

		SfCookie sfCookie = new SfCookie();

		sfCookie.setSfCookie("{\".SFCommunity\":\"31EF3F56673ECDD3F4AEE2DAD9E6E41685F7024457D3240EE9FFC999689257E1D1C4250A56703D80495BE8D43675D3A28DED5747CF595BA306A130C5DEC38A796C14E88AD74EC19A84DA7238228CB339E27635FB5C2E305A10A3378005524C67\"}");
		System.out.println(sfCookie);


	}

}
