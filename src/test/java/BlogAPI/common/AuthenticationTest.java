package BlogAPI.common;

import BlogAPI.BlogApiApplication;
import BlogAPI.Common.shiro.CustomRealm;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class AuthenticationTest extends TestBase {
    @Autowired
    private UserService userService;
    @Test
    public void testAuthentication() {
        var user = new SysUser();
        user.setUserName("wwr");
        user.setPwdHash("153226");
        userService.login(user);
        userService.logout();
    }
}
