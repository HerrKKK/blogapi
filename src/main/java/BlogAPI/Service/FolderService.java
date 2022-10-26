package BlogAPI.Service;

import BlogAPI.Entity.Folder;
import BlogAPI.Mapper.FolderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FolderService {
    final private FolderDao folderDao;
    private SimpleDateFormat simpleDateFormat;
    @Autowired
    FolderService(FolderDao folderDao) {
        this.folderDao = folderDao;
        this.simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
    }
    @Value("${spring.custom.file-upload-path}")

    public Folder addFolder(Folder folder) {
        // url format: '/xxxx/xxx/'
        var parent = folder.getParent();

        var url = new StringBuilder(folder.getTitle())
                                  .append('/');
        folder.setUrl(folder.getTitle());
        if (parent != null) {
            url.insert(0, parent.getUrl());
        }
        folder.setUrl(url.toString());
        folder.setCreatedTime(simpleDateFormat.format(new Date()));

        // test if url is unique first
        var ret = folderDao.save(folder);
        if (parent != null) {
            parent.getSubFolders().add(folder);
            folderDao.save(parent);
        }
        return ret;
    }
    public List<Folder> findFolders(Folder folder) {
        var matcher = ExampleMatcher
                                    .matching()
                                    .withIgnorePaths("subFolders");
        if (folder.getId() == 0) {
            matcher = matcher.withIgnorePaths("id");
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
        var folderList = findFolders(folder);

        for (var f : folderList) {
            folderDao.deleteAll(f.getSubFolders());
            folderDao.delete(f);
        }
    }
}
