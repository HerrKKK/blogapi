package BlogAPI.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class FileService {
    @Value("${spring.custom.file-upload-path}")
    private String folder;
    public void upload(MultipartFile uploadFile, String path) {
        var folderPath = folder + path;
        log.info(folderPath);
        log.info(uploadFile.getOriginalFilename());
        File dirPath = new File(folderPath);
        try {
            if (dirPath.exists() || dirPath.mkdirs()) {
                uploadFile.transferTo(new File(folderPath,
                        uploadFile.getOriginalFilename()));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
