package BlogAPI.Controller;

import BlogAPI.Common.Model.Response;
import BlogAPI.Entity.Folder;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.AuthService;
import BlogAPI.Service.FolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/folder")
public class FolderController {
    private final FolderService folderService;
    @Autowired
    FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response addFolder(@RequestBody Folder folder) {
        try {
            return new Response(folderService.addFolder(folder));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    @RequestMapping(method = RequestMethod.GET)
    public Response getFolder(@RequestBody Folder folder) {
        try {
            return new Response(folderService.findFolders(folder));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    @RequestMapping(method = RequestMethod.PUT)
    public Response updateFolder(@RequestBody Folder folder) {
        try {
            return new Response(folderService.modifyFolder(folder));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public Response deleteFolder(@RequestBody Folder folder) {
        try {
            folderService.removeFolder(folder);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}
