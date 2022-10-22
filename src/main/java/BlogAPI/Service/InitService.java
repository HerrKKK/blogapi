package BlogAPI.Service;

import BlogAPI.Entity.SysRole;
import BlogAPI.Entity.SysUser;
import BlogAPI.Mapper.UserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class InitService implements ApplicationRunner {
    @Value("${spring.custom.admin-username}")
    private String adminUsername;
    @Value("${spring.custom.admin-password-hash}")
    private String adminPwdHash;
    @Value("${spring.custom.admin-password-salt}")
    private String adminSalt;
    @Value("${spring.custom.admin-email}")
    private String adminEmail;

    private final UserDao userDao;
    private final RoleService roleService;
    public InitService(UserDao userDao,
                       RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("boot up");
        addAdmin();
    }
    public void addAdmin() {
        var admin = new SysUser();
        admin.setUserName(adminUsername);
        admin.setEmail(adminEmail);
        admin.setPwdHash(adminPwdHash);
        admin.setSalt(adminSalt);
        var role = new SysRole();
        role.setName("admin");

        try {
            admin.getRoles().add(roleService.addRole(role));
        } catch (Exception e) {
            System.out.println("admin role existed");
        }
        try {
            userDao.save(admin);
        } catch (Exception e) {
            System.out.println("admin existed");
        }
    }
}
