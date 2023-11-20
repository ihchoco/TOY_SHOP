package site.devdaramg.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.devdaramg.shop.dto.CartItemRequestDto;
import site.devdaramg.shop.dto.CartItemResponseDto;
import site.devdaramg.shop.entity.Cart;
import site.devdaramg.shop.entity.CartItem;
import site.devdaramg.shop.entity.Member;
import site.devdaramg.shop.repository.CartItemRepository;
import site.devdaramg.shop.repository.CartRepository;
import site.devdaramg.shop.repository.MemberRepository;
import site.devdaramg.shop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public void addCartItem(CartItemRequestDto cartItemRequestDto){
        Member member = memberRepository.findById(cartItemRequestDto.getMemberId()).get();
        Cart cart = cartRepository.findByOrderer(member).get();
        CartItem cartItem = CartItem.createCartItem(cartItemRequestDto);

        cart.addCartItem(cartItem);
    }

    public List<CartItemResponseDto> getCartItemsByMember(Long memberId){
        Member member = memberRepository.findById(memberId).get();
        Cart cart = cartRepository.findByOrderer(member).get();
        List<CartItem> items = cart.getItems();
        List<CartItemResponseDto> response = items.stream().map(x -> CartItemResponseDto.builder()
                        .id(x.getId())
                        .product(productRepository.findById(x.getProductId()).get())
                        .price(x.getPrice())
                        .build())
                .collect(Collectors.toList());
        return response;
    }

    public List<CartItemResponseDto> getCartItems(){
        return cartItemRepository.findAll().stream().map(x -> CartItemResponseDto.builder()
                        .id(x.getId())
                        .product(productRepository.findById(x.getProductId()).get())
                        .price(x.getPrice())
                        .quantity(x.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    public void cancelCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> {
            throw new IllegalArgumentException("찾는 카트 아이템이 없습니다");
        });
        System.out.println("장바구니 취소 카트 아이템");
        System.out.println(cartItem);
        cartItemRepository.delete(cartItem);
    }


}
