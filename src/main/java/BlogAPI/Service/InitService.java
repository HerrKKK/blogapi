package BlogAPI.Service;

import BlogAPI.Entity.SysRole;
import BlogAPI.Entity.SysUser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class InitService implements ApplicationRunner {
    private final UserService userService;
    private final RoleService roleService;
    public InitService(UserService userService,
                       RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @Override
    public void run(ApplicationArguments args) {
        System.out.println("boot up");
        addAdmin();
    }
    public void addAdmin() {
        var admin = new SysUser();
        admin.setUserName("wwr");
        admin.setEmail("iswangwr@gmail.com");
        admin.setPwdHash("153226");
        var role = new SysRole();
        role.setName("admin");

        try {
            admin.getRoles().add(roleService.addRole(role));
        } catch (Exception e) {
            System.out.println("admin role existed");
        }
        try {
            userService.addUser(admin);
        } catch (Exception e) {
            System.out.println("admin existed");
        }
    }
}
