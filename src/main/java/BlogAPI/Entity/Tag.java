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
@ToString(exclude = {"resources"})
@EqualsAndHashCode(exclude = {"resources"})
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"articles"})
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "tags")
    Set<Resource> resources = new HashSet<>();
}
