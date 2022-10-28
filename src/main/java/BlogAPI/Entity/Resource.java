package BlogAPI.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String createdTime;
    private String modifiedTime;

    @Column(unique = true)
    private String url;
    @ManyToOne
    private SysUser author;
    @ManyToOne(fetch=FetchType.EAGER)
    private Resource parent;
    @OneToMany(mappedBy="parent",
               cascade=CascadeType.REMOVE,
               fetch=FetchType.EAGER)
    private Set<Resource> subResources = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Tag> tags = new HashSet<>();
}
