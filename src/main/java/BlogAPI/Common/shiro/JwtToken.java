package BlogAPI.Common.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/*
  Just like UsernamePasswordToken,
  Normally, we have userName as principal,
  password as credential,
  SimpleAuthenticationInfo(username,
                           password,
                           getName());
  is used to check if username & password is
  same as those passed in.
  Here we do not use principal and credentials
  to verify, but verify the jwt token inside only
  SimpleAuthenticationInfo(jwt,
                           jwt,
                           getName()); always
  returns true, so we verify the jwt before.
* */
public class JwtToken implements AuthenticationToken {
    private final String jwt;

    public JwtToken(String jwt) {
        this.jwt = jwt;
    }

    @Override // username
    public Object getPrincipal() {
        return jwt;
    }

    @Override // password
    public Object getCredentials() {
        return jwt;
    }
}

