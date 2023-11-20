package site.devdaramg.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.devdaramg.shop.dto.OrderRequestDto;
import site.devdaramg.shop.dto.OrderResponseDto;
import site.devdaramg.shop.entity.Order;
import site.devdaramg.shop.service.OrderService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public void createOrder(@RequestBody OrderRequestDto orderRequestDto){
        log.info("request : {}",orderRequestDto);
        orderService.makeOrder(orderRequestDto.getMemberId(), orderRequestDto.getOrderItemRequestDtos(), orderRequestDto.getShippingInfo());
    }

    @PostMapping("/orders/{id}/cancel")
    public void cancelOrder(@PathVariable("id") Long orderId){
        log.info("request : {}", orderId);
        orderService.cancel(orderId);
    }

    @GetMapping("/orders/member/{memberId}")
    public List<Order> getOrders(@RequestParam Long memberId){
        log.info("request: {}", memberId);
        return orderService.findOrderListByMemberId(memberId);
    }

    @GetMapping("/orders")
    public List<OrderResponseDto> getAllOrders(){
        List<OrderResponseDto> allOrders = orderService.findAllOrders();

        return allOrders;
    }

}
