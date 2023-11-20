package site.devdaramg.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import site.devdaramg.shop.dto.OrderItemRequestDto;
import site.devdaramg.shop.dto.OrderRequestDto;
import site.devdaramg.shop.entity.*;
import site.devdaramg.shop.repository.MemberRepository;
import site.devdaramg.shop.repository.ProductRepository;
import site.devdaramg.shop.service.MemberService;
import site.devdaramg.shop.service.OrderService;
import site.devdaramg.shop.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void clean(){
        memberRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("주문 생성 테스트")
    void OrderControllerTest() throws Exception {
        Product product = Product.builder()
                .name("도메인주도개발_BOOK")
                .price(20000)
                .stockQuantity(10)
                .build();

        productRepository.save(product);

        //2. 회원가입(멤버등록)
        Member member = Member.builder()
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        memberRepository.save(member);

        //3. 주문상품선택
        List<OrderItemRequestDto> shopBasket = new ArrayList<>();
        OrderItemRequestDto orderItem1 = OrderItemRequestDto.builder()
                .productId(product.getId())
                .count(2)
                .price(20000)
                .build();

        OrderItemRequestDto orderItem2 = OrderItemRequestDto.builder()
                .productId(product.getId())
                .count(1)
                .price(20000)
                .build();

        shopBasket.add(orderItem1);
        shopBasket.add(orderItem2);

        //4. 배송지 정보 입력
        ShippingInfo shippingInfo = new ShippingInfo(new Receiver("박기연", "010-1234-1234"), new Address("경기도", "산본로 299", "10213"), "-");

        OrderRequestDto request = OrderRequestDto.builder()
                .memberId(member.getId())
                .orderItemRequestDtos(shopBasket)
                .shippingInfo(shippingInfo)
                .build();
        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("주문 취소 테스트")
    void OrderControllerTest2() throws Exception{
        Product product = Product.builder()
                .name("도메인주도개발_BOOK")
                .price(20000)
                .stockQuantity(10)
                .build();

        productRepository.save(product);

        //2. 회원가입(멤버등록)
        Member member = Member.builder()
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        memberRepository.save(member);

        //3. 주문상품선택
        List<OrderItemRequestDto> shopBasket = new ArrayList<>();
        OrderItemRequestDto orderItem1 = OrderItemRequestDto.builder()
                .productId(product.getId())
                .count(2)
                .price(20000)
                .build();

        OrderItemRequestDto orderItem2 = OrderItemRequestDto.builder()
                .productId(product.getId())
                .count(1)
                .price(20000)
                .build();

        shopBasket.add(orderItem1);
        shopBasket.add(orderItem2);

        //4. 배송지 정보 입력
        ShippingInfo shippingInfo = new ShippingInfo(new Receiver("박기연", "010-1234-1234"), new Address("경기도", "산본로 299", "10213"), "-");

        OrderRequestDto request = OrderRequestDto.builder()
                .memberId(member.getId())
                .orderItemRequestDtos(shopBasket)
                .shippingInfo(shippingInfo)
                .build();

        //5. 주문 저장
        Long orderId = orderService.makeOrder(member.getId(), shopBasket, shippingInfo);

        String url = "/orders/" + orderId + "/cancel";

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        int stockCnt = product.getStockQuantity();
        int stockQuantity = productRepository.findAll().get(0).getStockQuantity();
        System.out.println("취소 후 stock cnt : "+stockQuantity);
    }

    @Test
    @DisplayName("주문 전체 조회 겨과 가져오기 테스트")
    void OrderControllerTest3() throws Exception {
        Product product = Product.builder()
                .name("도메인주도개발_BOOK")
                .price(20000)
                .stockQuantity(10)
                .build();

        productRepository.save(product);

        //2. 회원가입(멤버등록)
        Member member = Member.builder()
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        memberRepository.save(member);

        //3. 주문상품선택
        List<OrderItemRequestDto> shopBasket = new ArrayList<>();
        OrderItemRequestDto orderItem1 = OrderItemRequestDto.builder()
                .productId(product.getId())
                .count(2)
                .price(20000)
                .build();


        shopBasket.add(orderItem1);

        //4. 배송지 정보 입력
        ShippingInfo shippingInfo = new ShippingInfo(new Receiver("박기연", "010-1234-1234"), new Address("경기도", "산본로 299", "10213"), "-");

        //5. 주문 저장
        Long orderId = orderService.makeOrder(member.getId(), shopBasket, shippingInfo);

        OrderRequestDto request = OrderRequestDto.builder()
                .memberId(member.getId())
                .orderItemRequestDtos(shopBasket)
                .shippingInfo(shippingInfo)
                .build();
        String json = objectMapper.writeValueAsString(request);



        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}