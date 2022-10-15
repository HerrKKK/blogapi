package BlogAPI.Service;

import BlogAPI.Entity.SysRole;
import BlogAPI.Entity.SysUser;
import BlogAPI.Mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserDao userDao;
    private final RoleService roleService;
    @Autowired
    public UserService(UserDao userDao,
                       RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
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
    public SysUser addUser(SysUser user) {
        user.setSalt(generateSalt());
        user.setPwdHash(encrypt(user.getPwdHash(), user.getSalt()));
        return userDao.save(user);
    }
    public List<SysUser> getUsers(SysUser user) {
        return userDao.findAll(Example.of(user));
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
