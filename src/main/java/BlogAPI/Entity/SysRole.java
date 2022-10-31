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
@Accessors(chain = true)
@ToString(exclude = {"users"})
@EqualsAndHashCode(exclude = {"users"})
@JsonIgnoreProperties(ignoreUnknown = true, value = {"users"})
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<SysUser> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,
                cascade=CascadeType.ALL)
    private Set<SysPermission> permissions = new HashSet<>();
}
