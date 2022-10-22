package BlogAPI.Controller;

import BlogAPI.Common.Model.Response;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.AuthService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    AuthController(AuthService authService) {
        this.authService = authService;
    }
    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermissions("admin")
    public Response login(SysUser user) {
        var res = new Response();

        try {
            authService.login(user);
            res.setStatus("success");
        } catch (Exception e) {
            res.setMessage(e.getMessage());
        }

        return res;
    }
}
