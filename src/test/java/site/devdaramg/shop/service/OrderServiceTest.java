package site.devdaramg.shop.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.devdaramg.shop.dto.MemberRequestDto;
import site.devdaramg.shop.dto.OrderItemRequestDto;
import site.devdaramg.shop.entity.*;
import site.devdaramg.shop.repository.MemberRepository;
import site.devdaramg.shop.repository.OrderRepository;
import site.devdaramg.shop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void clean(){
        productRepository.deleteAll();
        memberRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("주문 생성 테스트")
    void OrderServiceTest(){
        //given
        //1. 상품등록
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

        //when
        orderService.makeOrder(member.getId(), shopBasket, shippingInfo);

        //then
        Assertions.assertThat(orderRepository.count()).isEqualTo(1);


        List<Order> findOrders = orderService.findOrderListByMemberId(member.getId());
        findOrders.forEach(System.out::println);

        int stockQuantity = productRepository.findAll().get(0).getStockQuantity();
        System.out.println("주문 후 stock cnt : "+productRepository.findAll().get(0).getStockQuantity());
    }

    @Test
    @Transactional
    @DisplayName("주문 취소 테스트")
    void OrderServiceTest2(){
        //given
        //1. 상품등록
        Product product = Product.builder()
                .name("도메인주도개발_BOOK")
                .price(20000)
                .stockQuantity(10)
                .build();

        productRepository.save(product);

        //2. 회원가입(멤버등록)
        Member member = Member.builder()
                .name("홍길동")
                .address(new Address("경기도", "다람쥐거리", "10213"))
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

        //when
        Long orderId = orderService.makeOrder(member.getId(), shopBasket, shippingInfo);

        System.out.println("stock cnt : "+product.getStockQuantity());
        System.out.println("stock cnt[2] : "+productRepository.findAll().get(0).getStockQuantity());
        //then
        orderService.cancel(orderId);

        Assertions.assertThat(orderRepository.count()).isEqualTo(0);

        int stockQuantity = productRepository.findAll().get(0).getStockQuantity();
        System.out.println("stock cnt[3] : "+productRepository.findAll().get(0).getStockQuantity());
        Assertions.assertThat(stockQuantity).isEqualTo(10);


    }


}