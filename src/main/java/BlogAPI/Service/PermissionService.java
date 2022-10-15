package BlogAPI.Service;

import BlogAPI.Entity.SysPermission;
import BlogAPI.Entity.SysRole;
import BlogAPI.Mapper.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.util.List;

public class PermissionService {
    private final PermissionDao permissionDao;
    @Autowired
    public PermissionService(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    public SysPermission newPermission(SysPermission permission) {
        return permissionDao.save(permission);
    }

    public List<SysPermission> getPermissions(SysPermission permission) {
        return permissionDao.findAll(Example.of(permission));
    }

    public SysPermission updatePermission(SysPermission permission) {
        return permissionDao.save(permissionDao
                            .findAll(Example.of(permission))
                            .get(0));
    }
    public void deletePermission(SysPermission permission) {
        permissionDao.delete(permissionDao
                     .findAll(Example.of(permission))
                     .get(0));
    }
}
