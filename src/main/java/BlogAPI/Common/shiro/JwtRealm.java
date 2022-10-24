package BlogAPI.Common.shiro;

import BlogAPI.Mapper.UserDao;
import BlogAPI.Service.SecurityService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class JwtRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SecurityService securityService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    @Override
    @SuppressWarnings("unchecked")
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        String jwtToken = (String) principalCollection.getPrimaryPrincipal();
        var roleList = (List<Map<String,Object>>) securityService
                                                 .decodeJWT(jwtToken)
                                                 .get("roles");
        var permissions = new HashSet<String>();
        var roles = new HashSet<String>();

        for (Map<String,Object> role : roleList) {
            roles.add((String)role.get("name"));
            for (var permission : (List<Map<String,Object>>)role.get("permissions")) {
                permissions.add((String) permission.get("name"));
            }
        }

        var simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String jwtToken = (String) authenticationToken.getPrincipal();

        securityService.verifyJWT(jwtToken);

        var userName = (String) securityService
                               .decodeJWT(jwtToken)
                               .get("userName");
        if (userDao.findByUserName(userName) == null) {
            return null;
        }

        return new SimpleAuthenticationInfo(jwtToken,
                                            jwtToken,
                                            getName());
    }
}
