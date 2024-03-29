package BlogAPI.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"roles"})
@EqualsAndHashCode(exclude = {"roles"})
@JsonIgnoreProperties(ignoreUnknown = true, value = {"roles"})
public class SysPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private String description;
    private String url;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<SysRole> roles = new HashSet<>();
}
