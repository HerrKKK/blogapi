package BlogAPI.Common.Util;

import BlogAPI.Entity.SysUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;

import java.util.*;

@Service
public class JwtUtil {
    @Value("${spring.custom.jwt-key}")
    private String SecretKey;
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private static String base64EncodedKey;

    @Autowired
    public void setBase64EncodedKey() {
        base64EncodedKey = Base64.encodeBase64String(SecretKey.getBytes());
    }

    public String encode(long ttlMillis, SysUser sysUser) {

        var claims = new HashMap<String, Object>();
        if (sysUser != null) {
            claims.put("id", sysUser.getId());
            claims.put("userName", sysUser.getUserName());
            claims.put("roles", sysUser.getRoles());
        }

        long nowMillis = System.currentTimeMillis();
        var now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString()) // jti
                .setIssuedAt(now) // iat
                .setSubject("wwr") // iss
                .signWith(signatureAlgorithm,
                          base64EncodedKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            var exp = new Date(expMillis); // exp
            builder.setExpiration(exp);
        }

        return builder.compact();
    }
    public Claims decode(String jwtToken) {
        return Jwts.parser()
                   .setSigningKey(base64EncodedKey)
                   .parseClaimsJws(jwtToken)
                   .getBody();
    }

    public boolean verify(String jwtToken) {
        JWTVerifier verifier =
                JWT.require(Algorithm
                           .HMAC256(Base64
                                   .decodeBase64(base64EncodedKey)))
                           .build();
        verifier.verify(jwtToken); // throw
        return true;
    }

    /*
    public static void main(String[] args) {
        JwtUtil util = new JwtUtil("tom", SignatureAlgorithm.HS256);

        // Base64.decodeBase64(byte[])
        Map<String, Object> map = new HashMap<>();
        map.put("username", "tom");
        map.put("password", "123456");
        map.put("age", 20);

        String jwtToken = util.encode(30000, map);
        util.isVerify(jwtToken);
        System.out.println("&#x5408;&#x6CD5;");

        util.decode(jwtToken).entrySet().forEach((entry) -> {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        });
    }*/
}

