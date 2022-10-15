package BlogAPI.Mapper;

import BlogAPI.Entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionDao extends JpaRepository<SysPermission, Long>,
                                       JpaSpecificationExecutor<SysPermission> {
}