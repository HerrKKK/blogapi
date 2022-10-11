package BlogAPI.Service;

import BlogAPI.Entity.SysUser;
import BlogAPI.Mapper.UserDao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class InitService implements ApplicationRunner {
    private final UserDao userDao;

    public InitService(UserDao userDao) {
        this.userDao = userDao;
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
        admin.setSalt(SecurityService.generateSalt());
        admin.setPwdHash(SecurityService.encrypt("153226", admin.getSalt()));
        try {
            userDao.save(admin);
        } catch (Exception e) {
            System.out.println("admin existed");
        }
    }
}
