package BlogAPI.Common.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    public Response() { status = "failure"; }
    public Response(Object obj) {
        this.obj = obj;
        status = "success";
    }
    private Object obj;
    private String message;
    private String status;
}
