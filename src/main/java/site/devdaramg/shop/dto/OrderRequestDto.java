package site.devdaramg.shop.dto;

import lombok.*;
import site.devdaramg.shop.entity.ShippingInfo;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {
    private Long id;
    private Long memberId;
    private List<OrderItemRequestDto> orderItemRequestDtos;
    private ShippingInfo shippingInfo;

    @Builder
    public OrderRequestDto(Long id, Long memberId, List<OrderItemRequestDto> orderItemRequestDtos, ShippingInfo shippingInfo) {
        this.id = id;
        this.memberId = memberId;
        this.orderItemRequestDtos = orderItemRequestDtos;
        this.shippingInfo = shippingInfo;
    }
}
