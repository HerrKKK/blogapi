package BlogAPI.Service;

import BlogAPI.BlogApiApplication;
import BlogAPI.common.TestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class FileServiceTest extends TestBase {
    @Autowired
    private FileService fileService;
    @Test
    public void testAuthentication() {
        var file = new MockMultipartFile("real_test",
                "testName","png",
                new byte[128]);
        String folderPath = "\\test_path\\";
        fileService.upload(file, folderPath);
    }
}
