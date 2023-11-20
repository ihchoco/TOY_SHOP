package site.devdaramg.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.devdaramg.shop.dto.MemberRequestDto;
import site.devdaramg.shop.dto.OrderItemRequestDto;
import site.devdaramg.shop.dto.OrderResponseDto;
import site.devdaramg.shop.entity.*;
import site.devdaramg.shop.repository.MemberRepository;
import site.devdaramg.shop.repository.OrderRepository;
import site.devdaramg.shop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    //주문 생성
    public Long makeOrder(Long memberId, List<OrderItemRequestDto> orderRequestItems, ShippingInfo shippingInfo){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("주문자 정보가 등록되어 있지 않습니다. 가입 필요");
        });
        /*
        수량 관련 주의사항
        1. static 메서드를 사용하여 create 진행(order, orderItem 등)
          - Order order = new Order(), new OrderItem() 수행
          - order.setOrderItem(), order.setDate() 등 수행
          - return order, orderItem을 통해서 반환
        2. Request를 보낼때 item, product, member 정보의 경우 각 객체가 아닌 ID만 보낸다
          - requestDTO에 Long productId, memberId 등을 전달
          - 서비스계층에서 repository.findbyId를 이용해서 조회
          - 가져온 데이터를 사용
        */
        List<OrderItem> orderItems = orderRequestItems.stream().map(x -> {
            Product product = productRepository.findById(orderRequestItems.get(0).getProductId()).get();
            return new OrderItem().createOrderItem(product, x.getCount(), x.getPrice());
        }).collect(Collectors.toList());

        Order order = Order.createOrder(member, orderItems, shippingInfo);

        int stockQuantity = productRepository.findAll().get(0).getStockQuantity();

        return orderRepository.save(order).getId();
    }

    //주문 조회
    public Order findOrder(Long id){
        return orderRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("찾는 주문이 없습니다");
        });
    }

    public List<Order> findOrderListByMemberId(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("주문자 정보가 등록되어 있지 않습니다. 가입 필요");
        });
        return orderRepository.findByOrderer(member);
    }

    public void cancel(Long id){
        Order findOrder = orderRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("찾는 주문이 없습니다");
        });
        findOrder.cancel();
        orderRepository.delete(findOrder);
    }

    public List<OrderResponseDto> findAllOrders(){
        return orderRepository.findAll().stream().map(x -> {
            return OrderResponseDto.builder()
                    .id(x.getId())
                    .orderer(x.getOrderer())
                    .orderDate(x.getOrderDate())
                    .orderItems(x.getOrderItems())
                    .orderStatus(x.getStatus())
                    .shippingInfo(x.getShippingInfo())
                    .build();
        }).collect(Collectors.toList());
    }

}
