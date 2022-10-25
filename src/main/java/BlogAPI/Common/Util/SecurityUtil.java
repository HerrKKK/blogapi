package BlogAPI.Common.Util;

import org.springframework.util.DigestUtils;

import java.util.UUID;

public class SecurityUtil {
    public static String generateSalt() {
        return DigestUtils
              .md5DigestAsHex(UUID.randomUUID()
                             .toString()
                             .replaceAll("-", "")
                             .getBytes());
    }
    public static String encrypt(String password, String salt) {
        return DigestUtils
              .md5DigestAsHex((DigestUtils
                              .md5DigestAsHex(password.getBytes())
                             + salt).getBytes());
    }
}
