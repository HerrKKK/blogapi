package BlogAPI.Service;

import BlogAPI.Entity.Folder;
import BlogAPI.Mapper.FolderDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        folder.setUrl(parent.getUrl() + '/' + folder.getTitle());
        folder.setCreatedTime(simpleDateFormat.format(new Date()));
        log.info(folder.getUrl());
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
        if (folder.getUrl().equals("")) {
            return;
        }
        folderDao.deleteAll(findFolders(folder));
    }
}
