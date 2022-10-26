package BlogAPI.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String createdTime;
    private String modifiedTime;
    private String content;
    private String url;
    @OneToOne
    private SysUser author;
    @OneToOne
    private Folder parent;
    @OneToMany
    private Set<Folder> subFolders = new HashSet<>();
    @ManyToMany
    Set<Tag> tags = new HashSet<>();
}
