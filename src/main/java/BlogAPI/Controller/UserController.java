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
@RequestMapping(value="/user")
public class UserController {
    private final UserService userService;
    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method= RequestMethod.POST)
    @RequiresPermissions("admin")
    public Response addUser(SysUser user) {
        var res = new Response();

        try {
            res.setObj(userService.addUser(user));
            res.setStatus("success");
        } catch (Exception e) {
            res.setMessage(e.getMessage());
        }

        return res;
    }

    @RequestMapping(method= RequestMethod.GET)
    @RequiresPermissions("admin")
    public Response getUser(SysUser user) {
        var res = new Response();

        try {
            res.setObj(userService.getUsers(user));
            res.setStatus("success");
        } catch (Exception e) {
            res.setMessage(e.getMessage());
        }

        return res;
    }

    @RequestMapping(method= RequestMethod.PUT)
    @RequiresPermissions("admin")
    public Response updateUser(SysUser user) {
        var res = new Response();

        try {
            res.setObj(userService.updateUser(user));
            res.setStatus("success");
        } catch (Exception e) {
            res.setMessage(e.getMessage());
        }

        return res;
    }
    @RequestMapping(method= RequestMethod.DELETE)
    @RequiresPermissions("admin")
    public Response deleteUser(SysUser user) {
        var res = new Response();

        try {
            userService.deleteUser(user);
            res.setStatus("success");
        } catch (Exception e) {
            res.setMessage(e.getMessage());
        }

        return res;
    }
}
