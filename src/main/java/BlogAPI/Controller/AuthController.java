package BlogAPI.Controller;

import BlogAPI.Common.Model.Response;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/auth")
public class AuthController {
    private final UserService userService;
    @Autowired
    AuthController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermissions("admin")
    public Response login(SysUser user) {
        var res = new Response();

        try {
            userService.login(user);
            res.setStatus("success");
        } catch (Exception e) {
            res.setMessage(e.getMessage());
        }

        return res;
    }
}
