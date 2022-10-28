package BlogAPI.Service;

import BlogAPI.Entity.Content;
import BlogAPI.Entity.Resource;
import BlogAPI.Mapper.ResourceDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class ResourceService {
    final private ResourceDao resourceDao;
    final private SimpleDateFormat simpleDateFormat;
    @Autowired
    ResourceService(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
        this.simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
    }

    public Resource addResource(Resource resource) {
        // url format: '/xxxx/xxx'
        Resource parent = findResources(resource.getParent())
                       .stream()
                       .findFirst()
                       .orElse(null);
        if (parent == null) {
            parent = findResources(new Resource()
                        .setUrl(""))
                        .get(0);// root folder
        }

        resource.setParent(parent);
        resource.setCreatedTime(simpleDateFormat.format(new Date()));

        var name = resource instanceof Content?
                        UUID.randomUUID().toString():
                        resource.getTitle();
        resource.setUrl(parent.getUrl() + '/' + name);

        // test if url is unique first
        return resourceDao.save(resource);
    }
    public List<Resource> findResources(Resource resource) {
        if (resource == null) {
            return new ArrayList<>();
        }
        var matcher = ExampleMatcher
                                    .matching()
                                    .withIgnorePaths("subFolders");
        if (resource.getId() == 0) {
            matcher = matcher.withIgnorePaths("id");
        }
        if (resource.getTags().size() == 0) {
            matcher = matcher.withIgnorePaths("tags");
        }

        return resourceDao.findAll(Example.of(resource, matcher));
    }
    public Resource modifyResource(Resource resource) {
        if (resource.getId() == 0) {
            return null;
        }
        resource.setModifiedTime(simpleDateFormat.format(new Date()));
        return resourceDao.save(resource);
    }
    public void removeResource(Resource resource) {
        var url = resource.getUrl();
        if (url != null && url.equals("")) {
            return;
        }
        if (resource.getId() != 0) {
            resourceDao.delete(resource);
            return;
        }
        resourceDao.deleteAll(findResources(resource));
    }
}
