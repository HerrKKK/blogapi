package BlogAPI.Controller;

import BlogAPI.Entity.TestEntity;
import BlogAPI.Model.Response;
import BlogAPI.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/test")
public class TestController {
    private final TestService testService;

    @Autowired
    TestController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping(value="/new",method=RequestMethod.GET)
    public Response newTest() {
        System.out.println("test api");
        var res = new Response();

        try {
            var testEntity = new TestEntity();
            testEntity.setName("wwr");
            testEntity.setAge(26);

            testService.AddTestEntity(testEntity);
            res.setObj(testEntity);
            res.setStatus("success");
        } catch (Exception e) {
            res.setMessage(e.getMessage());
            res.setStatus("failure");
        }

        return res;
    }

}
