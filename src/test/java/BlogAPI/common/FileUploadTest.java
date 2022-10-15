package BlogAPI.common;

import BlogAPI.BlogApiApplication;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.FileService;
import BlogAPI.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
public class FileUploadTest extends TestBase {
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
