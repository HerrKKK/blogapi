package BlogAPI.Service;

import BlogAPI.Mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
public class SecurityService {
    private final UserDao userDao;
    @Autowired
    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
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
}
