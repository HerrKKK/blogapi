package BlogAPI.Common.shiro;

import BlogAPI.Mapper.UserDao;
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

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
                            PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        var roles = new HashSet<String>();
        var permissions = new HashSet<String>();

        var user = userDao.findByUserName(userName);
        if (user != null) {
            for (var role : user.getRoles()) {
                roles.add(role.getName());
                for (var permission : role.getPermissions()) {
                    permissions.add(permission.getName());
                }
            }
        }

        // permissions.add("admin");

        var simpleAuthorizationInfo = new SimpleAuthorizationInfo();
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
