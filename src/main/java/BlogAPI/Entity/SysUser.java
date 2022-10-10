package BlogAPI.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String userName;
    private String email;
    private String pwdHash;
    private String salt;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<SysRole> roles = new HashSet<>();
}
