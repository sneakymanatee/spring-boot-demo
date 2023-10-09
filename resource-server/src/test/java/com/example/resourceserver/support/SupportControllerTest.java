package com.example.resourceserver.support;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SupportControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())    // Required to add authentication in post processors
                .build();
    }

    @Test
    void support_when_userAuthority_is_support_then_return_advice() throws Exception {
        Jwt supportJwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "customer-support")
                .claim("scope", "support")
                .build();
        this.mockMvc.perform(get("/support/card").with(jwt().jwt(supportJwt)))
                .andExpect(status().isOk())
                .andExpect(content().string("You do not get support."));
    }

    @Test
    void support_when_userAuthority_is_not_support_then_return_403() throws Exception {
        Jwt noSupportJwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "customer-support")
                .build();
        this.mockMvc.perform(get("/support/card").with(jwt().jwt(noSupportJwt)))
                .andExpect(status().isForbidden());
    }
}
