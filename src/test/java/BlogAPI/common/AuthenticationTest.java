package BlogAPI.common;

import BlogAPI.BlogApiApplication;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class AuthenticationTest extends TestBase {
    @Autowired
    private AuthService authService;
    @Test
    public void testAuthentication() {
        var user = new SysUser();
        user.setUserName("wwr");
        user.setPwdHash("blog password");
        authService.login(user);
        authService.logout();
    }
}
