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
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        return DigestUtils.md5DigestAsHex((password + salt).getBytes());
    }
}
