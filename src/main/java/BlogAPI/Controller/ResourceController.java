package BlogAPI.Controller;

import BlogAPI.Common.Model.Response;
import BlogAPI.Entity.Resource;
import BlogAPI.Service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/resource")
public class ResourceController {
    private final ResourceService resourceService;
    @Autowired
    ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response addResource(@RequestBody Resource resource) {
        try {
            return new Response(resourceService.addResource(resource));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    @RequestMapping(method = RequestMethod.GET)
    public Response getResource(Resource resource) {
        try {
            return new Response(resourceService.findResources(resource));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    @RequestMapping(value = "/subResources",
                    method = RequestMethod.GET)
    public Response getSubResources(Resource resource) {
        try {
            var resources = resourceService
                    .findResources(resource);
            if (resources.size() != 1) {
                return new Response("Get "+resources.size()+" resource");
            }
            return new Response().setObj(resources.get(0).getSubResources());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    @RequestMapping(method = RequestMethod.PUT)
    public Response updateResource(@RequestBody Resource resource) {
        try {
            return new Response(resourceService.modifyResource(resource));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public Response deleteResource(@RequestBody Resource resource) {
        try {
            resourceService.removeResource(resource);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}
