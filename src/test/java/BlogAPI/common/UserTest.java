package BlogAPI.common;

import BlogAPI.BlogApiApplication;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void testUser() {
        var user = new SysUser();

        log.info("id: " + user.getId());
        log.info("userName: " + user.getUserName());
        log.info("email: " + user.getEmail());
        log.info("pwdHash: " + user.getPwdHash());
        log.info("salt: " + user.getSalt());
        log.info("roles: " + user.getRoles());

        user.setUserName("wwr");
        var list = userService.findUsers(user);
        log.info("result size: " + list.size());
    }
}
