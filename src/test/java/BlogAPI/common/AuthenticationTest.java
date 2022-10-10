package BlogAPI.common;

import BlogAPI.BlogApiApplication;
import BlogAPI.Common.shiro.CustomRealm;
import BlogAPI.Entity.SysUser;
import BlogAPI.Mapper.UserDao;
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
    private UserDao userDao;
    @Autowired
    private CustomRealm customRealm;

    @Test
    public void testAuthentication() {
        addUser();

        var defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        var subject = SecurityUtils.getSubject();
        var usernamePasswordToken = new UsernamePasswordToken("wwr", "123456");
        try {
            subject.login(usernamePasswordToken);
            System.out.println("isAuthenticated:" + subject.isAuthenticated());
            subject.checkRoles("admin");
            subject.checkPermission("admin");
        } catch (AuthenticationException e) {
            System.out.println("isAuthenticated:" + subject.isAuthenticated());
        } finally {
            removeUser();
        }
    }

    private void addUser() {
        var newUser = new SysUser();
        newUser.setUserName("wwr");
        newUser.setPwdHash("123456");
        userDao.save(newUser);
    }

    private void removeUser() {
        userDao.delete(userDao.findByUserName("wwr"));
    }
}
