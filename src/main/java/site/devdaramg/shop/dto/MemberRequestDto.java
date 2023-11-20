package site.devdaramg.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.devdaramg.shop.entity.Address;

@ToString
@Getter
@Builder
public class MemberRequestDto {
    private Long id;
    private String name;
    private Address address;
}
