package BlogAPI.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private Object obj;
    private String message;
    private String status;
}
