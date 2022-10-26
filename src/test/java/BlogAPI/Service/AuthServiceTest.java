package BlogAPI.Service;

import BlogAPI.BlogApiApplication;
import BlogAPI.Entity.SysUser;
import BlogAPI.common.TestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class AuthServiceTest extends TestBase {
    @Autowired
    private AuthService authService;
    @Test
    public void testAuthentication() {
        var user = new SysUser()
                .setUserName("wwr")
                .setPwdHash("blog password");
        authService.login(user);
        authService.logout();
    }
}
