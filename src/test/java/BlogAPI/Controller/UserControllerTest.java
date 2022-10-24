package BlogAPI.Controller;

import BlogAPI.BlogApiApplication;
import BlogAPI.Entity.SysUser;
import BlogAPI.Service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
@WebAppConfiguration
public class UserControllerTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                 .webAppContextSetup(webApplicationContext)
                 .build();
    }

    @Test
    public void addUserTest() throws Exception {
        var user = new SysUser();
        user.setUserName("wwr");
        user.setPwdHash("password");
        authService.login(user);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\": \"testUser\"," +
                        "\"pwdHash\": \"password\"}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        authService.logout();
    }
    @Test
    public void removeUserTest() throws Exception {
        var user = new SysUser();
        user.setUserName("wwr");
        user.setPwdHash("password");
        authService.login(user);
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\": \"testUser\"}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        authService.logout();
    }
}
