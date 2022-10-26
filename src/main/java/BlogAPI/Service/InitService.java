package BlogAPI.Service;

import BlogAPI.Entity.Folder;
import BlogAPI.Entity.SysRole;
import BlogAPI.Entity.SysUser;
import BlogAPI.Mapper.FolderDao;
import BlogAPI.Mapper.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Slf4j
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
    private final FolderDao folderDao;
    @Autowired
    public InitService(UserDao userDao,
                       RoleService roleService,
                       FolderDao folderDao) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.folderDao = folderDao;
    }

    @Override
    public void run(ApplicationArguments args) {
        addAdmin();
        addRootPath();
    }
    private void addAdmin() {
        var admin = new SysUser()
                .setUserName(adminUsername)
                .setEmail(adminEmail)
                .setPwdHash(adminPwdHash)
                .setSalt(adminSalt);
        var role = new SysRole().setName("admin");

        try {
            admin.getRoles().add(roleService.addRole(role));
        } catch (Exception e) {
            log.info("admin role existed");
            return;
        }
        userDao.save(admin);
    }
    private void addRootPath() {
        try {
            folderDao.save(new Folder().setUrl(""));
        } catch (Exception e) {
            log.info("root path existed");
        }
    }
}
