package site.devdaramg.shop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.devdaramg.shop.dto.CartItemRequestDto;

import javax.persistence.*;

@ToString
@Getter
@Entity
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cart cart;

    private Long productId;

    private int quantity;

    private int price;

    @Builder
    public CartItem(Long id, Cart cart, Long productId, int quantity, int price) {
        this.id = id;
        this.cart = cart;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public static CartItem createCartItem(CartItemRequestDto cartItemRequestDto){
        CartItem cartItem = CartItem.builder()
                .productId(cartItemRequestDto.getProductId())
                .quantity(cartItemRequestDto.getQuantity())
                .price(cartItemRequestDto.getPrice())
                .build();

        return cartItem;
    }
}
