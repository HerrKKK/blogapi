package BlogAPI.Service;

import BlogAPI.BlogApiApplication;
import BlogAPI.Entity.Content;
import BlogAPI.Entity.Folder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class ResourceServiceTest {
    @Autowired
    private ResourceService resourceService;

    @Test
    public void addResourcesTest() {
        var folder = new Folder().setTitle("Tech");

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
    public void addFolderTest() throws Exception {
        var folder = resourceService
                .findResources(new Folder().setTitle("Tech"))
                .stream()
                .findFirst()
                .orElse(null);
        if (folder == null) {
            throw new Exception("tech is null");
        }
        String[] techSubFolderNames = {
            "C++", "Java", "Javascript", "Python"
        };
        for (var name : techSubFolderNames) {
            var newFolder = new Folder()
                        .setTitle(name)
                        .setParent(folder);
            resourceService.addResource(newFolder);
        }
    }
    @Test
    public void removeFolderTest() {
        var folder = new Folder().setTitle("Tech");
        log.info(String.valueOf(folder.getId()));
        resourceService.removeResource(folder);
    }
}
