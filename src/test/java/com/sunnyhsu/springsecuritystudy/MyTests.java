package com.sunnyhsu.springsecuritystudy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MyTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHello() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/hello")
                .header("Authorization","Basic dGVzdDFAZ21haWwuY29tOjExMQ==");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }

    @Test
    public void testHello2() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/hello")
                .with(httpBasic("test1@gmail.com","111"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }

    @Test
    public void testWelcome() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/welcome")
                .with(httpBasic("test1@gmail.com","111"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }

    @Test
    public void testWelcome2() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/welcome")
                .with(httpBasic("test2@gmail.com","222"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(403));
    }

    @WithMockUser(username = "mock", roles = { "NORMAL_MEMBER" })
    @Test
    public void testWelcome3() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/welcome");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(403));
    }

    @Test
    public void testCors() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .options("/hello")
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "http://www.example.com");

        mockMvc.perform(requestBuilder)
                .andExpect(header().exists("Access-Control-Allow-Origin"))
                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                .andExpect(header().exists("Access-Control-Allow-Methods"))
                .andExpect(header().string("Access-Control-Allow-Methods", "GET"))
                .andExpect(status().is(200));
    }

    @Test
    public void testWelcome_noCsrfToken() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/welcome")
                .with(httpBasic("test1", "111"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(403));
    }

    @Test
    public void testWelcome_withCsrfToken() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/welcome")
                .with(httpBasic("test1@gmail.com", "111"))
                .with(csrf());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }

    @Test
    public void testRegister_noCsrfToken() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/register");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }
}

