package BlogAPI.Common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest,
                                      ServletResponse servletResponse,
                                      Object mappedValue)
                    throws Exception {
        log.info("isAccessAllowed");
        // return true to access, false to call onAccessDenied
        var request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authentication");
        log.info("JwtToken in head {}", jwt);
        JwtToken jwtToken = new JwtToken(jwt);

        try {
            getSubject(servletRequest,
                       servletResponse).login(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            onLoginFail(servletResponse);
        }
        return false;
    }
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest,
                                     ServletResponse servletResponse) {
        // should jump to password login
        log.warn("onAccessDenied");
        return false;
    }
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
    }
}
