package BlogAPI.Service;

import BlogAPI.Common.Util.SecurityUtil;
import BlogAPI.Common.shiro.CustomRealm;
import BlogAPI.Entity.SysUser;
import BlogAPI.Mapper.UserDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserDao userDao;
    private final CustomRealm customRealm;
    @Autowired
    public AuthService(UserDao userDao,
                       CustomRealm customRealm) {
        this.userDao = userDao;
        this.customRealm = customRealm;
    }

    public String calculateUserPwdHash(String userName, String password) {
        try {
            return SecurityUtil.encrypt(password,
                    userDao.findByUserName(userName).getSalt());
        } catch (Exception e) {
            return "";
        }
    }
    public void login(SysUser user) {
        try {
            var defaultSecurityManager = new DefaultSecurityManager();
            defaultSecurityManager.setRealm(customRealm);

            SecurityUtils.setSecurityManager(defaultSecurityManager);
            var subject = SecurityUtils.getSubject();
            var usernamePasswordToken =
                new UsernamePasswordToken(user.getUserName(),
                        calculateUserPwdHash(user.getUserName(),
                                             user.getPwdHash()));
            subject.login(usernamePasswordToken);
            System.out.println("isAuthenticated:"
                              + subject.isAuthenticated());
        } catch (AuthenticationException e) {
            throw e;
        }
    }
    public void logout() {
        try {
            var subject = SecurityUtils.getSubject();
            subject.logout();
            System.out.println("isAuthenticated:"
                              + subject.isAuthenticated());
        } catch (AuthenticationException e) {
            throw e;
        }
    }
}
