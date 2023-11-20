package site.devdaramg.shop.dto;

import lombok.*;
import site.devdaramg.shop.entity.Product;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CartItemResponseDto {
    private Long id;

    private Product product;

    private int quantity;

    private int price;

    @Builder
    public CartItemResponseDto(Long id, Product product, int quantity, int price) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
