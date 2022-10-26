package BlogAPI.Entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String createdTime;
    private String modifiedTime;
    private String content;

    @Column(unique = true)
    private String url;
    @ManyToOne
    private SysUser author;
    @ManyToOne(fetch = FetchType.EAGER)
    private Folder parent;
    @OneToMany(mappedBy="parent",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<Folder> subFolders = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Tag> tags = new HashSet<>();
}
