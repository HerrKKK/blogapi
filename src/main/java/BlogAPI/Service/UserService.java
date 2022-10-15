package BlogAPI.Service;

import BlogAPI.Common.shiro.CustomRealm;
import BlogAPI.Entity.SysRole;
import BlogAPI.Entity.SysUser;
import BlogAPI.Mapper.UserDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserDao userDao;
    private final RoleService roleService;
    private final CustomRealm customRealm;
    @Autowired
    public UserService(UserDao userDao,
                       RoleService roleService,
                       CustomRealm customRealm) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.customRealm = customRealm;
    }
    public static String generateSalt() {
        return DigestUtils
              .md5DigestAsHex(UUID.randomUUID()
                             .toString()
                             .replaceAll("-", "")
                             .getBytes());
    }
    public static String encrypt(String password, String salt) {
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        return DigestUtils.md5DigestAsHex((password + salt).getBytes());
    }
    public String calculateUserPwdHash(String userName, String password) {
        try {
            return encrypt(password,
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
            var usernamePasswordToken = new UsernamePasswordToken(user.getUserName(),
                                             calculateUserPwdHash(user.getUserName(),
                                                                  user.getPwdHash()));
            subject.login(usernamePasswordToken);
            System.out.println("isAuthenticated:" + subject.isAuthenticated());
        } catch (AuthenticationException e) {
            throw e;
        }
    }
    public void logout() {
        try {
            var subject = SecurityUtils.getSubject();
            subject.logout();
            System.out.println("isAuthenticated:" + subject.isAuthenticated());
        } catch (AuthenticationException e) {
            throw e;
        }
    }
    public SysUser addUser(SysUser user) {
        user.setSalt(generateSalt());
        user.setPwdHash(encrypt(user.getPwdHash(), user.getSalt()));
        return userDao.save(user);
    }
    public List<SysUser> getUsers(SysUser user) {
        var matcher = ExampleMatcher.matching();
        if (user.getId() == 0) {
            matcher = matcher.withIgnorePaths("id");
        }
        if (user.getRoles().size() == 0) {
            matcher = matcher.withIgnorePaths("roles");
        }
        return userDao.findAll(Example.of(user, matcher));
    }
    public SysUser updateUser(SysUser user) {
        if (user.getId() == 0) {
            user.setId(userDao
                      .findByUserNameOrEmail(user.getUserName(),
                                             user.getEmail())
                      .getId());
        }
        return userDao.save(user);
    }
    public void deleteUser(SysUser user) {
        userDao.delete(userDao
                      .findByIdOrUserNameOrEmail(user.getId(),
                                                 user.getUserName(),
                                                 user.getEmail()));
    }
    public void addRoleToUser(SysUser user, SysRole role) {
        user.getRoles().add(role);
        userDao.save(user);
    }
    public void removeRoleFromUser(SysUser user, SysRole role) {
        user.getRoles().remove(role);
        userDao.save(user);
    }
}
