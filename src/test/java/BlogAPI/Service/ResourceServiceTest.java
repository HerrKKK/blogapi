package BlogAPI.Service;

import BlogAPI.BlogApiApplication;
import BlogAPI.Entity.Content;
import BlogAPI.Entity.Folder;
import BlogAPI.Entity.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class ResourceServiceTest {
    @Autowired
    private ResourceService resourceService;

    @Test
    public void addResourcesTest() {
        var folder = new Folder().setTitle("folder");

        folder = resourceService.addResource(folder);

        var article = new Content()
                .setContent("test".getBytes())
                .setTitle("article")
                .setParent(folder);
        folder.getSubResources().add(article);
        resourceService.modifyResource(folder);
        resourceService.addResource(article);
    }
    @Test
    public void removeFolderTest() {
        var folder = new Folder().setTitle("folder");
        log.info(String.valueOf(folder.getId()));
        resourceService.removeResource(folder);
    }
    @Test
    public void getAllFoldersTest() {
        var folders = resourceService
                .findResources(new Folder().setUrl(""));
        log.info(folders.get(0).toString());
    }
}
