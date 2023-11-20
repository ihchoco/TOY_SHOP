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
import site.devdaramg.shop.dto.ProductRequestDto;
import site.devdaramg.shop.entity.Address;
import site.devdaramg.shop.entity.Member;
import site.devdaramg.shop.entity.Product;
import site.devdaramg.shop.repository.MemberRepository;
import site.devdaramg.shop.repository.ProductRepository;
import site.devdaramg.shop.service.MemberService;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void clean(){
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("상품등록 테스트")
    void MemberControllerTest1() throws Exception {
        //given
        ProductRequestDto request = ProductRequestDto.builder()
                .name("개발다람쥐 책")
                .price(10000)
                .stockQuantity(100)
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("상품조회 테스트")
    void MemberControllerTest2() throws Exception {
        //given
        Product request = Product.builder()
                .name("개발다람쥐 책")
                .price(10000)
                .stockQuantity(100)
                .build();

        productRepository.save(request);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", request.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                .andDo(MockMvcResultHandlers.print());
    }

}