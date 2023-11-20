package site.devdaramg.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.devdaramg.shop.entity.Member;
import site.devdaramg.shop.entity.OrderItem;
import site.devdaramg.shop.entity.OrderStatus;
import site.devdaramg.shop.entity.ShippingInfo;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderResponseDto {
    private Long id;
    private Member orderer;
    private OrderStatus orderStatus;
    private Date orderDate;
    private ShippingInfo shippingInfo;
    private List<OrderItem> orderItems;
}
