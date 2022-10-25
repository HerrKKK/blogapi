package BlogAPI.common;

import BlogAPI.BlogApiApplication;
import BlogAPI.Service.SecurityService;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class JwtTest extends TestBase {
    @Autowired
    private SecurityService util;
    @Autowired
    private UserService userService;
    @Test
    public void testJwt() {
        var user = new SysUser();
        user.setUserName("wwr");
        user = userService.findUsers(user).get(0);
        String token =
                util.encodeJWT((long)1000*60*60*24*30, user);
        util.verifyJWT(token);
        util.decodeJWT(token).entrySet().forEach((entry) -> {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        });
    }
}
