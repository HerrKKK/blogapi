package BlogAPI.Mapper;

import BlogAPI.Entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<SysUser, Long>,
                                 JpaSpecificationExecutor<SysUser> {
}
