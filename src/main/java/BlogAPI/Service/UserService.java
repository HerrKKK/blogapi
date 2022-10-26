package BlogAPI.Service;

import BlogAPI.Common.Util.SecurityUtil;
import BlogAPI.Entity.SysRole;
import BlogAPI.Entity.SysUser;
import BlogAPI.Mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public SysUser addUser(SysUser user) {
        user.setSalt(SecurityUtil.generateSalt());
        user.setPwdHash(SecurityUtil.encrypt(user.getPwdHash(),
                                             user.getSalt()));
        return userDao.save(user);
    }
    public List<SysUser> findUsers(SysUser user) {
        var matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("pwdHash")
                .withIgnorePaths("salt");
        if (user.getId() == 0) {
            matcher = matcher.withIgnorePaths("id");
        }
        if (user.getRoles().size() == 0) {
            matcher = matcher.withIgnorePaths("roles");
        }
        return userDao.findAll(Example.of(user, matcher));
    }
    public SysUser modifyUser(SysUser user) {
        if (user.getId() == 0) {
            user = findUsers(user)
                    .stream()
                    .findFirst()
                    .orElse(null);
        }
        if (user == null) {
            return null;
        }
        return userDao.save(user);
    }
    public void removeUser(SysUser user) {
        user = user = findUsers(user)
                .stream()
                .findFirst()
                .orElse(null);
        if (user != null) {
            userDao.delete(user);
        }
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
