package site.devdaramg.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@ToString
@Getter
@NoArgsConstructor
public class ShippingInfo {
    @Embedded
    private Receiver receiver; //배송 받는 사람
    @Embedded
    private Address address; //배송지 주소
    private String request; //배송 요청사항

    public ShippingInfo(Receiver receiver, Address address, String request) {
        this.receiver = receiver;
        this.address = address;
        this.request = request;
    }
}
