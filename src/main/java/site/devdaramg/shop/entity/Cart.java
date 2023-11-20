package site.devdaramg.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @JsonIgnore
    @OneToOne
    private Member orderer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

    public Cart() {
    }

    @Builder
    public Cart(Long id, Member member) {
        this.id = id;
        this.orderer = member;
    }

    public void addCartItem(CartItem cartItem){
        items.add(cartItem);
    }

}
