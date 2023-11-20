package site.devdaramg.shop.dto;

import lombok.*;
import site.devdaramg.shop.entity.ShippingInfo;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CartItemRequestDto {
    private Long id;
    private Long memberId;

    private Long productId;

    private int quantity;

    private int price;

    @Builder
    public CartItemRequestDto(Long id, Long memberId, Long productId, int quantity, int price) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
