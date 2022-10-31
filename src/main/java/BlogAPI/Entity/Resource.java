package BlogAPI.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"parent"})
@EqualsAndHashCode(exclude = {"parent"})
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"subResources"})
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
    @ManyToOne(cascade=CascadeType.PERSIST,
               fetch=FetchType.EAGER)
    private Resource parent;
    @OneToMany(mappedBy="parent",
               cascade=CascadeType.REMOVE,
               fetch=FetchType.LAZY)
    private Set<Resource> subResources = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Tag> tags = new HashSet<>();
}
