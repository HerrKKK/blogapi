package BlogAPI.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Setter
@Getter
public class Content extends Folder {
    private String createdTime;
    private String modifiedTime;
    private String content;
    private String url;
    @OneToOne
    private SysUser author;
}
