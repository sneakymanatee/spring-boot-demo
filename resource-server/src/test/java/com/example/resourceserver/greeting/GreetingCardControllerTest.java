package com.example.resourceserver.greeting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class GreetingCardControllerTest {
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
    @DisplayName("greeting should return welcome and username")
    void greeting_should_return_welcome_and_username() throws Exception {
        this.mockMvc.perform(get("/greeting").with(user("User1")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome, User1"));
    }

    @Test
    @DisplayName("greeting when unauthenticated user should return 401")
    void greeting_should_when_unauthenticated_user_return_401() throws Exception {
        this.mockMvc.perform(get("/greeting"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @DisplayName("overview should return 200")
    void overview_should_return_200() throws Exception {
        this.mockMvc.perform(get("/overview"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is an overview."));
    }

    @Test
    @DisplayName("getNearestStore should return users nearest store")
    void getNearestStore_should_return_users_nearest_store() throws Exception {
        var user = new GreetingCardUser();
        user.setUsername("User1");
        user.setNearestStore("Ratingen, Germany");

        this.mockMvc.perform(get("/nearest-store").with(user(new GreetingCardUserService.GreetingCardUserDetails(user))))
                .andExpect(status().isOk())
                .andExpect(content().string("Ratingen, Germany"));
    }

    @Test
    @DisplayName("getNearestStore when unauthenticated user should return 401 ")
    void getNearestStore_when_unauthenticated_user_should_return_401() throws Exception {
        this.mockMvc.perform(get("/nearest-store"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @DisplayName("postPurchase when user isAbleToPurchase should book purchase")
    void postPurchase_when_user_isAbleToPurchase_should_book_purchase() throws Exception {
        var user = new GreetingCardUser();
        user.setUsername("User1");
        user.setAbleToPurchase(true);

        this.mockMvc.perform(post("/purchase")
                        .content("This is a card")
                        .with(user(new GreetingCardUserService.GreetingCardUserDetails(user)))
                        .with(csrf())   // Add CSRF token
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("postPurchase when unauthenticated user should return 401 ")
    void postPurchase_when_unauthenticated_user_should_return_401() throws Exception {
        this.mockMvc.perform(get("/nearest-store"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }
}