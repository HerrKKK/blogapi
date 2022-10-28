package BlogAPI.Service;

import BlogAPI.BlogApiApplication;
import BlogAPI.Entity.Content;
import BlogAPI.Entity.Dir;
import BlogAPI.Entity.Folder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class FolderServiceTest {
    @Autowired
    private FolderService folderService;

    @Test
    public void addFolderTest() {
        var folder = new Dir().setTitle("folder");

        folder = folderService.addFolder(folder);

        var article = new Content()
                .setContent("test".getBytes())
                .setTitle("article")
                .setParent(folder);
        folder.getSubFolders().add(article);
        folderService.modifyFolder(folder);
        folderService.addFolder(article);
    }
    @Test
    public void removeFolderTest() {
        var folder = new Dir().setTitle("folder");
        folderService.removeFolder(folder);
    }
    @Test
    public void getFolders() {
        var folders = folderService
                .findFolders(new Dir());
        for (var folder : folders) {
            log.info(String.valueOf(folder.getId()));
        }
    }
}
