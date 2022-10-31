package BlogAPI.Controller;

import BlogAPI.Common.Model.Response;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value="/user")
public class UserController {
    private final UserService userService;
    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method=RequestMethod.POST)
    @RequiresRoles("admin")
    public Response addUser(@RequestBody SysUser user) {
        try {
            return new Response(userService.addUser(user));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    @RequiresRoles("admin")
    public Response getUser(SysUser user) {
        try {
            return new Response(userService.findUsers(user));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @RequestMapping(method=RequestMethod.PUT)
    @RequiresRoles("admin")
    public Response updateUser(@RequestBody SysUser user) {
        try {
            return new Response(userService.modifyUser(user));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    @RequestMapping(method=RequestMethod.DELETE)
    @RequiresRoles("admin")
    public Response deleteUser(@RequestBody SysUser user) {
        try {
            userService.removeUser(user);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}
