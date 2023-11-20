package site.devdaramg.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.devdaramg.shop.dto.CartItemRequestDto;
import site.devdaramg.shop.dto.CartItemResponseDto;
import site.devdaramg.shop.entity.CartItem;
import site.devdaramg.shop.service.CartService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public void addCartItem(@RequestBody CartItemRequestDto cartItemRequestDto){
        cartService.addCartItem(cartItemRequestDto);
    }

    @GetMapping("/cart/{memberId}")
    public List<CartItemResponseDto> getCartItemsByMember(@PathVariable Long memberId){
        return cartService.getCartItemsByMember(memberId);
    }

    @GetMapping("/cart")
    public List<CartItemResponseDto> getCartItems(){
        return cartService.getCartItems();
    }

    @PostMapping("/cart/{cartItemId}/cancel")
    public void cancelCartItem(@PathVariable Long cartItemId){
        log.info("request : {}", cartItemId);
        cartService.cancelCartItem(cartItemId);
    }


}
