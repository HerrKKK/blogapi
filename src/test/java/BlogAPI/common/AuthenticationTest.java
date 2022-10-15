package BlogAPI.common;

import BlogAPI.BlogApiApplication;
import BlogAPI.Common.shiro.CustomRealm;
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
    private CustomRealm customRealm;
    @Autowired
    private UserService userService;

    @Test
    public void testAuthentication() {

        var defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        var subject = SecurityUtils.getSubject();
        var usernamePasswordToken = new UsernamePasswordToken("wwr",
                userService.calculateUserPwdHash("wwr",
                                                      "153226"));
        try {
            subject.login(usernamePasswordToken);
            System.out.println("isAuthenticated:" + subject.isAuthenticated());
            subject.checkRoles("admin");
            // subject.checkPermission("admin");
        } catch (AuthenticationException e) {
            System.out.println("isAuthenticated:" + subject.isAuthenticated());
        }
    }
}
