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

    @RequestMapping(method=RequestMethod.POST)
    @RequiresPermissions("admin")
    public Response addUser(SysUser user) {
        try {
            return new Response(userService.addUser(user));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    @RequiresPermissions("admin")
    public Response getUser(SysUser user) {
        try {
            return new Response(userService.findUsers(user));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @RequestMapping(method=RequestMethod.PUT)
    @RequiresPermissions("admin")
    public Response updateUser(SysUser user) {
        try {
            return new Response(userService.modifyUser(user));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    @RequestMapping(method=RequestMethod.DELETE)
    @RequiresPermissions("admin")
    public Response deleteUser(SysUser user) {
        try {
            userService.removeUser(user);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}
