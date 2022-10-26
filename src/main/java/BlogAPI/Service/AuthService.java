package BlogAPI.Service;

import BlogAPI.Common.Util.SecurityUtil;
import BlogAPI.Common.shiro.CustomRealm;
import BlogAPI.Entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    private final UserService userService;
    private final CustomRealm customRealm;
    @Autowired
    public AuthService(UserService userService,
                       CustomRealm customRealm) {
        this.userService = userService;
        this.customRealm = customRealm;
    }

    public void login(SysUser user) throws AuthenticationException {
        user = userService.findUsers(user)
                          .stream()
                          .findFirst()
                          .orElse(null);
        if (user == null) {
            throw new AuthenticationException("no such user");
        }

        var defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        var subject = SecurityUtils.getSubject();
        var usernamePasswordToken =
            new UsernamePasswordToken(user.getUserName(),
                        SecurityUtil.encrypt(user.getPwdHash(),
                                             user.getSalt()));
        subject.login(usernamePasswordToken);
        log.info("isAuthenticated:" + subject.isAuthenticated());
    }
    public void logout() throws AuthenticationException {
        var subject = SecurityUtils.getSubject();
        subject.logout();
        log.info("isAuthenticated:" + subject.isAuthenticated());
    }
}
