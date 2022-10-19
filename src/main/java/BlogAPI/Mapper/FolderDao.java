package BlogAPI.Mapper;

import BlogAPI.Entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderDao extends JpaRepository<Folder, Long>,
                                    JpaSpecificationExecutor<Folder> {
}