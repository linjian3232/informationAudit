package com.informationaudit.controller;

import com.informationaudit.service.AdminService;
import com.informationaudit.util.Consts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void returnsReviewerRoleAndStartsSessionForValidCredentials() throws Exception {
        when(adminService.ifExist("reviewer", "valid-password")).thenReturn(1);
        when(adminService.verifyPasswords("reviewer", "valid-password")).thenReturn(2);

        mvc.perform(post("/admin/login/status")
                        .param("name", "reviewer")
                        .param("password", "valid-password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(request().sessionAttribute(Consts.NAME, "reviewer"));
    }

    @Test
    void rejectsInvalidCredentialsWithoutEchoingThePassword() throws Exception {
        mvc.perform(post("/admin/login/status")
                        .param("name", "reviewer")
                        .param("password", "invalid-password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").value("账号或密码错误"));
    }
}
