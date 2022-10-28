package BlogAPI.Service;

import BlogAPI.Entity.Content;
import BlogAPI.Entity.Folder;
import BlogAPI.Mapper.FolderDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class FolderService {
    final private FolderDao folderDao;
    final private SimpleDateFormat simpleDateFormat;
    @Autowired
    FolderService(FolderDao folderDao) {
        this.folderDao = folderDao;
        this.simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
    }

    public Folder addFolder(Folder folder) {
        // url format: '/xxxx/xxx'
        Folder parent = findFolders(folder.getParent())
                       .stream()
                       .findFirst()
                       .orElse(null);
        if (parent == null) {
            parent = findFolders(new Folder()
                        .setUrl(""))
                        .get(0);// root folder
        }

        folder.setParent(parent);
        folder.setCreatedTime(simpleDateFormat.format(new Date()));

        var name = folder instanceof Content?
                        UUID.randomUUID().toString():
                        folder.getTitle();
        folder.setUrl(parent.getUrl() + '/' + name);

        // test if url is unique first
        return folderDao.save(folder);
    }
    public List<Folder> findFolders(Folder folder) {
        if (folder == null) {
            return new ArrayList<>();
        }
        var matcher = ExampleMatcher
                                    .matching()
                                    .withIgnorePaths("subFolders");
        if (folder.getId() == 0) {
            matcher = matcher.withIgnorePaths("id");
        }
        if (folder.getTags().size() == 0) {
            matcher = matcher.withIgnorePaths("tags");
        }

        return folderDao.findAll(Example.of(folder, matcher));
    }
    public Folder modifyFolder(Folder folder) {
        if (folder.getId() == 0) {
            return null;
        }
        folder.setModifiedTime(simpleDateFormat.format(new Date()));
        return folderDao.save(folder);
    }
    public void removeFolder(Folder folder) {
        var url = folder.getUrl();
        if (url != null && url.equals("")) {
            return;
        }
        if (folder.getId() != 0) {
            folderDao.delete(folder);
            return;
        }
        folderDao.deleteAll(findFolders(folder));
    }
}
