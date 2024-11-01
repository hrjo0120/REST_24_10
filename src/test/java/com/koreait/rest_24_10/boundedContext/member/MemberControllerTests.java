package com.koreait.rest_24_10.boundedContext.member;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class MemberControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("POST /member/login 은 로그인 처리 URL 이다.")
    void t1() throws Exception {
        //When
        ResultActions resultActions = mvc.perform(
                        post("/api/v1/member/login")
                                .content("""
                                        {
                                        "username" : "user1",
                                        "password" : "1234"
                                        }
                                        """.stripIndent())
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andDo(print());

        //Then
        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.resultCode").value("S-1"))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data.accessToken").exists());

//        헤더에 액세스 토큰 정보가 없으면 어떻게 판별할 것인지는 위 코드에 있음

//        MvcResult mvcResult = resultActions.andReturn();
//
//        MockHttpServletResponse response = mvcResult.getResponse();

//        membercontroller에 resp.addHeader("Authentication", accessToken);가 없으면 이 부분도 필요하지 않게 된다.
//        String authentication = response.getHeader("Authentication");
//        assertThat(authentication).isNotEmpty();
    }

    @Test
    @WithUserDetails("user1")
    @DisplayName("GET /member/me 는 myPage")
    void t2() throws Exception {
        //When
        ResultActions resultActions = mvc.perform(
                        get("/api/v1/member/me")
                )
                .andDo(print());

        //Then
        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.resultCode").value("S-1"))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data.member.id").exists())
                .andExpect(jsonPath("$.data.member.userName").value("user1"));
    }
}
