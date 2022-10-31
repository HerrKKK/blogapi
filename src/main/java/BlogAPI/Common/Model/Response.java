package BlogAPI.Common.Model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Response {
    public Response() { status = "success"; }
    public Response(Object obj) {
        this.obj = obj;
        status = "success";
    }
    public Response(String message) {
        this.message = message;
        status = "failure";
    }
    private Object obj;
    private String message;
    private String status;
}
