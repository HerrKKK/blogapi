package BlogAPI.common;

import BlogAPI.BlogApiApplication;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void testUser() {
        var user = new SysUser();
        System.out.println(user.getId());
        System.out.println(user.getUserName());
        System.out.println(user.getEmail());
        System.out.println(user.getPwdHash());
        System.out.println(user.getSalt());
        System.out.println(user.getRoles());

        user.setUserName("wwr");
        var list = userService.getUsers(user);
        System.out.println(list.size());
    }
}
