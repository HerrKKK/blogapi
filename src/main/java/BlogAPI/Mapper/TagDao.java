package BlogAPI.Mapper;

import BlogAPI.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDao extends JpaRepository<Tag, Long>,
                                JpaSpecificationExecutor<Tag> {
}
