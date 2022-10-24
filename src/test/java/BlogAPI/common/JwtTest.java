package BlogAPI.common;

import BlogAPI.BlogApiApplication;
import BlogAPI.Entity.SysRole;
import BlogAPI.Service.SecurityService;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class JwtTest extends TestBase {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Test
    public void testJwt() {
        var user = new SysUser();
        user.setUserName("wwr");
        var roles = new HashSet<SysRole>();
        var newRole = new SysRole();
        newRole.setName("admin");
        roles.add(newRole);
        user.setRoles(roles);
        user = userService.findUsers(user).get(0);
        String token =
                securityService.encodeJWT((long)1000*60*60*24*30, user);
        securityService.verifyJWT(token);

        var t = securityService.decodeJWT(token);
        log.info(t.get("id").toString());
        log.info((String)t.get("userName"));
        log.info(String.valueOf(t.get("roles")));
    }
}
