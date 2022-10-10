package BlogAPI.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class Content extends Folder {
    private String createdTime;
    private String modifiedTime;
    private String content;
    private String url;
    @OneToOne
    private SysUser author;
    @ManyToMany
    Set<Tag> tags = new HashSet<>();
}
