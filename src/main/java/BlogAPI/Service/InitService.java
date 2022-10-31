package BlogAPI.Service;

import BlogAPI.Entity.Folder;
import BlogAPI.Entity.Resource;
import BlogAPI.Entity.SysRole;
import BlogAPI.Entity.SysUser;
import BlogAPI.Mapper.ResourceDao;
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
    private final ResourceDao resourceDao;
    @Autowired
    public InitService(UserDao userDao,
                       ResourceDao resourceDao) {
        this.userDao = userDao;
        this.resourceDao = resourceDao;
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
        admin.getRoles().add(new SysRole().setName("admin"));

        try {
            userDao.save(admin);
        } catch (Exception e) {
            log.info("admin role existed");
        }
    }
    private void addRootPath() {
        try {
            resourceDao.save(new Folder().setUrl("/blogs")
                                         .setParent(new Folder()
                                                 .setUrl("")));
        } catch (Exception e) {
            log.info("root path existed");
        }
    }
}
