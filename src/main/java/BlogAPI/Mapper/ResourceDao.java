package BlogAPI.Mapper;

import BlogAPI.Entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceDao extends JpaRepository<Resource, Long>,
                                    JpaSpecificationExecutor<Resource> {
}