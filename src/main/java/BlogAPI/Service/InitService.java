package BlogAPI.Service;

import BlogAPI.Entity.SysUser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class InitService implements ApplicationRunner {
    private final UserService userService;

    public InitService(UserService userService) {
        this.userService = userService;
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
        try {
            userService.addUser(admin);
        } catch (Exception e) {
            System.out.println("admin existed");
        }
    }
}
