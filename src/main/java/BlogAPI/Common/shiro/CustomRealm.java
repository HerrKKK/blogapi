package BlogAPI.Common.shiro;

import BlogAPI.Mapper.UserDao;
import BlogAPI.Service.UserService;
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
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
                PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        var user = userDao.findByUserName(userName);
        if (user != null) {
            for (var role : user.getRoles()) {
                roles.add(role.getName());
            }
        }

        // permissions.add("admin");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
                        AuthenticationToken authenticationToken)
                                             throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        var sysUser = userDao.findByUserName(userName);
        if (null == sysUser) {
            return null;
        }

        return new SimpleAuthenticationInfo(userName,
                                            sysUser.getPwdHash(),
                                            getName());
    }
}
