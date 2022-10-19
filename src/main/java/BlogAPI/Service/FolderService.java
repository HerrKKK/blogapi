package BlogAPI.Service;

import BlogAPI.Entity.Folder;
import BlogAPI.Mapper.FolderDao;
import org.springframework.beans.factory.annotation.Autowired;

public class FolderService {
    final private FolderDao folderDao;
    @Autowired
    FolderService(FolderDao folderDao) {
        this.folderDao = folderDao;
    }

    Folder addFolder(Folder folder, Folder parent) {
        if (parent != null || parent.getId() != 0) {
            parent.getSubFolders().add(folder);
        }

        folderDao.save(parent);
        return folderDao.save(folder);
    }
}
