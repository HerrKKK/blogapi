package BlogAPI.Service;

import BlogAPI.Entity.SysUser;
import BlogAPI.Mapper.RoleDao;
import BlogAPI.Entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleDao roleDao;
    @Autowired
    RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public SysRole newRole(SysRole sysRole) {
        return roleDao.save(sysRole);
    }

    public List<SysRole> getRoles(SysRole sysRole) {
        var matcher = ExampleMatcher.matching();
        if (sysRole.getId() == 0) {
            matcher = matcher.withIgnorePaths("id");
        }
        if (sysRole.getPermissions().size() == 0) {
            matcher = matcher.withIgnorePaths("permissions");
        }
        return roleDao.findAll(Example.of(sysRole, matcher));
    }

    public SysRole updateRole(SysRole sysRole) {
        if (sysRole.getId() == 0) {
            sysRole.setId(roleDao
                         .findAll(Example.of(sysRole))
                         .get(0)
                         .getId());
        }
        return roleDao.save(sysRole);
    }
    public void deleteRole(SysRole sysRole) {
        roleDao.delete(roleDao
               .findAll(Example.of(sysRole))
               .get(0));
    }
}
