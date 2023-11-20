package site.devdaramg.shop.entity;

public enum OrderStatus {
    PAYMENT_WAITING, //결제전
    PREPARING, //상품준비중
    SHIPPED, //상품준비중
    DELIVERING, //배송중
    DELIVERY_COMPLETED, //배송완료
    CANCEL, //상품취소
    ORDER_CONFIRMED; //구매확정완료
}
