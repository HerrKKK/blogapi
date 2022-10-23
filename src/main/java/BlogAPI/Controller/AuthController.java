package BlogAPI.Controller;

import BlogAPI.Common.Model.Response;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response login(@RequestBody SysUser user) {
        var res = new Response();
        log.info(user.getUserName());
        log.info(user.getPwdHash());

        try {
            authService.login(user);
            res.setStatus("success");
        } catch (Exception e) {
            res.setStatus("failure");
            res.setMessage(e.getMessage());
        }

        return res;
    }
}
