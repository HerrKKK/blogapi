package BlogAPI.Service;

import BlogAPI.Entity.SysPermission;
import BlogAPI.Mapper.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    private final PermissionDao permissionDao;
    @Autowired
    public PermissionService(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    public SysPermission addPermission(SysPermission permission) {
        return permissionDao.save(permission);
    }
    public List<SysPermission> findPermissions(SysPermission permission) {
        return permissionDao.findAll(Example.of(permission));
    }
    public SysPermission modifyPermission(SysPermission permission) {
        return permissionDao.save(permissionDao
                            .findAll(Example.of(permission))
                            .get(0));
    }
    public void removePermission(SysPermission permission) {
        permissionDao.delete(permissionDao
                     .findAll(Example.of(permission))
                     .get(0));
    }
}
