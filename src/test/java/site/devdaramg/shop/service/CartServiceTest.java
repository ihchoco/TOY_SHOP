package site.devdaramg.shop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.devdaramg.shop.dto.CartItemRequestDto;
import site.devdaramg.shop.dto.CartItemResponseDto;
import site.devdaramg.shop.dto.MemberRequestDto;
import site.devdaramg.shop.entity.Address;
import site.devdaramg.shop.entity.CartItem;
import site.devdaramg.shop.entity.Member;
import site.devdaramg.shop.entity.Product;
import site.devdaramg.shop.repository.CartItemRepository;
import site.devdaramg.shop.repository.CartRepository;
import site.devdaramg.shop.repository.MemberRepository;
import site.devdaramg.shop.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private MemberService memberService;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void clean(){
        cartRepository.deleteAll();;
        cartItemRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("장바구니_아이템 추가")
    void CartServiceTest(){
        //given
        MemberRequestDto requestMember = MemberRequestDto.builder()
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        Long memberId = memberService.join(requestMember);

        Product request = Product.builder()
                .name("개발다람쥐 책")
                .price(10000)
                .stockQuantity(100)
                .build();

        Long productId = productRepository.save(request).getId();

        CartItemRequestDto cartItem = CartItemRequestDto.builder()
                .memberId(memberId)
                .productId(productId)
                .quantity(10)
                .build();

        //when
        cartService.addCartItem(cartItem);

        //then
        System.out.println(cartRepository.findAll().get(0));
        assertThat(cartItemRepository.findAll().size()).isEqualTo(1);

        CartItem findCartItem = cartItemRepository.findAll().get(0);
        System.out.println(findCartItem);
    }

    @Test
    @DisplayName("장바구니_아이템 조회")
    void CartServiceTest2(){
        //given
        MemberRequestDto requestMember = MemberRequestDto.builder()
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        Long memberId = memberService.join(requestMember);

        Product request = Product.builder()
                .name("개발다람쥐 책")
                .price(10000)
                .stockQuantity(100)
                .build();

        Long productId = productRepository.save(request).getId();

        CartItemRequestDto cartItem = CartItemRequestDto.builder()
                .memberId(memberId)
                .productId(productId)
                .quantity(10)
                .build();

        //when
        cartService.addCartItem(cartItem);
        List<CartItemResponseDto> findCartItems = cartService.getCartItemsByMember(memberId);

        //then
        Assertions.assertThat(findCartItems.get(0).getProduct().getName()).isEqualTo(request.getName());
    }

    @Test
    @DisplayName("장바구니_아이템 삭제")
    void CartServiceTest3(){
        //given
        MemberRequestDto requestMember = MemberRequestDto.builder()
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        Long memberId = memberService.join(requestMember);

        Product request = Product.builder()
                .name("개발다람쥐 책")
                .price(10000)
                .stockQuantity(100)
                .build();

        Long productId = productRepository.save(request).getId();

        CartItemRequestDto cartItem = CartItemRequestDto.builder()
                .memberId(memberId)
                .productId(productId)
                .quantity(10)
                .build();

        //when
        cartService.addCartItem(cartItem);
        List<CartItemResponseDto> findCartItems = cartService.getCartItemsByMember(memberId);

        //then
        Assertions.assertThat(findCartItems.get(0).getProduct().getName()).isEqualTo(request.getName());

        System.out.println("카트 아이템(삭제전) : "+findCartItems.get(0).getId());
        cartService.cancelCartItem(findCartItems.get(0).getId());

        List<CartItemResponseDto> afterCancelItems = cartService.getCartItemsByMember(memberId);

        System.out.println("카트 아이템(삭제후) : "+afterCancelItems.get(0).getId());
        Assertions.assertThat(afterCancelItems.size()).isEqualTo(0);
    }

}