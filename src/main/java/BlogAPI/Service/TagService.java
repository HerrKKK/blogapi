package BlogAPI.Service;

import BlogAPI.Entity.Tag;
import BlogAPI.Mapper.TagDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TagService {
    private final TagDao tagDao;
    @Autowired
    private TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public Tag addTag(Tag tag) {
        return tagDao.save(tag);
    }
    public List<Tag> findTags(Tag tag) {
        var matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("description")
                .withIgnorePaths("folders");

        if (tag.getId() == 0) {
            matcher = matcher.withIgnorePaths("id");
        }
        return tagDao.findAll(Example.of(tag, matcher));
    }
    public Tag modifyTag(Tag tag) {
        if (tag.getId() == 0) {
            tag = findTags(tag)
                    .stream()
                    .findFirst()
                    .orElse(null);
        }
        return tagDao.save(tag);
    }
    public void removeTag(Tag tag) {
        if (tag.getId() == 0) {
            tag = findTags(tag)
                .stream()
                .findFirst()
                .orElse(null);
        }
        tagDao.delete(tag);
    }
}
