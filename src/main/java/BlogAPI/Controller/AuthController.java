package BlogAPI.Controller;

import BlogAPI.Common.Model.Response;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value="/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Response login(@RequestBody SysUser user) {
        try {
            authService.login(user);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public Response logout(@RequestBody SysUser user) {
        try {
            authService.login(user);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}
