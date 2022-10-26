package BlogAPI.Service;

import BlogAPI.Entity.Folder;
import BlogAPI.Mapper.FolderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {
    final private FolderDao folderDao;
    @Autowired
    FolderService(FolderDao folderDao) {
        this.folderDao = folderDao;
    }

    Folder addFolder(Folder folder) {
        var parent = folder.getParent();
        if (parent != null && parent.getId() != 0) {
            parent.getSubFolders().add(folder);
            folderDao.save(parent);
        }

        return folderDao.save(folder);
    }
    List<Folder> findFolder(Folder folder) {
        var matcher = ExampleMatcher
                                    .matching()
                                    .withIgnorePaths("subFolders");
        if (folder.getId() == 0) {
            matcher = matcher.withIgnorePaths("id");
        }
        return folderDao.findAll(Example.of(folder, matcher));
    }
    Folder modifyFolder(Folder folder) {
        if (folder.getId() == 0) {
            return null;
        }
        return folderDao.save(folder);
    }
    void removeFolder(Folder folder) {
        var folderList = findFolder(folder);

        for (var f : folderList) {
            folderDao.deleteAll(f.getSubFolders());
            folderDao.delete(f);
        }
    }
}
