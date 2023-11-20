package site.devdaramg.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.devdaramg.shop.entity.Product;

@Getter
@Setter
@Builder
public class OrderItemRequestDto {
    private Long productId;
    private int count;
    private int price;
}
