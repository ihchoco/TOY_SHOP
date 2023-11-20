package site.devdaramg.shop.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@Getter
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member orderer; //주문자
    private Date orderDate; //주문날짜
    private OrderStatus status; //주문상태
    @Embedded
    private ShippingInfo shippingInfo; //배송정보

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>(); //주문 상품 리스트

    public static Order createOrder(Member orderer, List<OrderItem> orderItems, ShippingInfo shippingInfo){
        Order order = new Order();
        order.setOrderer(orderer);
        order.setOrderItems(orderItems);
        order.setShippingInfo(shippingInfo);
        order.orderDate = new Date();
        order.status = OrderStatus.PAYMENT_WAITING;
        return order;
    }

    private void setOrderer(Member orderer){
        if(orderer == null) throw new IllegalArgumentException("주문자가 없습니다");
        this.orderer = orderer;
    }

    private void setOrderItems(List<OrderItem> orderItems){
        if(orderItems == null) throw new IllegalArgumentException("주문 상품이 없습니다");
        for (OrderItem orderItem : orderItems) {
            this.addOrderItems(orderItem); //분리한 이유 : 추후에 장바구니에서도 사용
        }

    }

    private void addOrderItems(OrderItem orderItem){
        this.orderItems.add(orderItem);
    }

    private void setShippingInfo(ShippingInfo shippingInfo){
        if(shippingInfo == null) throw new IllegalArgumentException("배송 정보가 없습니다");
        this.shippingInfo = shippingInfo;
    }

    /**
     * 요구사항
     * 1. 배송지 변경 및 상품 취소는 결제 전, 상품 준비중인 경우에만 가능하다
     */
    public void changeShippingInfo(ShippingInfo shippingInfo){
        verifyNotYetShipped();
        this.shippingInfo = shippingInfo;
    }

    public void cancel(){
        verifyNotYetShipped();
        this.status = OrderStatus.CANCEL;
        this.orderItems.forEach(x -> x.cancel());
    }

    private void verifyNotYetShipped(){
        if(this.status != OrderStatus.PAYMENT_WAITING && this.status != OrderStatus.PREPARING){
            throw new IllegalArgumentException("변경 및 취소는 상품 결제 전, 상품 준비중 상태에서만 가능합니다");
        }
    }

    //주문 총 금액 반환
    public int getTotalPrice(){
        return this.orderItems.stream().mapToInt(x -> x.getTotalPrice()).sum();
    }
}
