package BlogAPI.Entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String email;
    private String pwdHash;
    private String salt;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<SysRole> roles = new HashSet<>();
}
