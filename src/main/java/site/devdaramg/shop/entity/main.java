package site.devdaramg.shop.entity;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        //1. 상품등록
        Product product = Product.builder()
                .name("도메인주도개발_BOOK")
                .price(20000)
                .stockQuantity(10)
                .build();

        //2. 회원가입(멤버등록)
        Member member = Member.builder()
                .id(1L)
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        //3. 주문상품선택
        List<OrderItem> shopBasket = new ArrayList<>();
        OrderItem orderItem = new OrderItem().createOrderItem(product, 5, 20000);
        OrderItem orderItem2 = new OrderItem().createOrderItem(product, 3, 20000);
        shopBasket.add(orderItem);
        shopBasket.add(orderItem2);

        //4. 배송지 정보 입력
        ShippingInfo shippingInfo = new ShippingInfo(new Receiver("박기연", "010-1234-1234"), new Address("경기도", "산본로 299", "10213"), "-");

        //5. 주문
        Order order = new Order().createOrder(member, shopBasket, shippingInfo);


        //6. 결과 확인
        //6-1. 상품 갯수가 줄어들었을까?
        System.out.println(product);

        //6-2. 주문 상태 확인
        System.out.println(order);

        //6-3. 주문 토탈 금액 확인
        System.out.println(order.getTotalPrice());






    }
}
