package BlogAPI.Controller;

import BlogAPI.BlogApiApplication;
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

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApiApplication.class)
@WebAppConfiguration
public class AuthControllerTest {
    @Autowired
    private AuthService authService;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                 .standaloneSetup(new AuthController(authService))
                 .build();
    }

    @Test
    public void loginTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                    .put("/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"userName\": \"wwr\"," +
                             "\"pwdHash\": \"password\"}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                          .content()
                          .json("{\"obj\": null, " +
                                            "\"message\": null, " +
                                            "\"status\":  \"success\"}"))
                .andDo(MockMvcResultHandlers.print());
        // .param("name", "Tom"))
    }
}
