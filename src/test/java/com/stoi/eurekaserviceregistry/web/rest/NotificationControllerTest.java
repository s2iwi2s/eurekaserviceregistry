package com.stoi.eurekaserviceregistry.web.rest;

import com.stoi.eurekaserviceregistry.web.NotificationRequest;
import com.stoi.eurekaserviceregistry.web.config.IntegrationTest;
import com.stoi.eurekaserviceregistry.web.config.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@IntegrationTest
class NotificationControllerTest {
    @Autowired
    private MockMvc restNotificationMockMvc;

    @Test
    void handleNotification_testNonAuthenticatedUser() throws Exception {
        NotificationRequest request = new NotificationRequest();
        restNotificationMockMvc
                .perform(post("/api/notification/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(request)))

                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN", "USER"})
    void handleNotification() throws Exception {
        NotificationRequest request = new NotificationRequest();
        restNotificationMockMvc
                .perform(post("/api/notification/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(request)))

                .andExpect(status().isOk());
    }
}