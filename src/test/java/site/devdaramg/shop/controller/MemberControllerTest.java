package site.devdaramg.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import site.devdaramg.shop.dto.MemberRequestDto;
import site.devdaramg.shop.entity.Address;
import site.devdaramg.shop.entity.Member;
import site.devdaramg.shop.repository.MemberRepository;
import site.devdaramg.shop.service.MemberService;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void clean(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void MemberControllerTest1() throws Exception {
        //given
        MemberRequestDto requestMember = MemberRequestDto.builder()
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        String json = objectMapper.writeValueAsString(requestMember);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원조회 테스트")
    void MemberControllerTest2() throws Exception {
        //given
        Member request = Member.builder()
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        memberRepository.save(request);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/members/{id}", request.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                .andDo(MockMvcResultHandlers.print());
    }

}