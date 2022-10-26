package BlogAPI.Service;

import BlogAPI.BlogApiApplication;
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
        var folder = new Folder().setTitle("folder");

        folder = folderService.addFolder(folder);

        var article = new Folder()
                .setTitle("test_article")
                .setParent(folder);
        folderService.addFolder(article);

        var article2 = new Folder()
                .setTitle("test_article2")
                .setParent(article);
        folderService.addFolder(article2);
    }
    @Test
    public void removeFolderTest() {
        var folder = new Folder().setUrl("/folder");
        folderService.removeFolder(folder);
    }
}
