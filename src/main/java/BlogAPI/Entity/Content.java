package BlogAPI.Entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Content extends Resource {
    @Lob
    private byte[] content;
}
