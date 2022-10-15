package BlogAPI.Mapper;

import BlogAPI.Entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<SysRole, Long>,
                                 JpaSpecificationExecutor<SysRole> {
}
