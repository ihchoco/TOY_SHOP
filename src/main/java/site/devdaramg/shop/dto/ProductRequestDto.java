package site.devdaramg.shop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRequestDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
}
