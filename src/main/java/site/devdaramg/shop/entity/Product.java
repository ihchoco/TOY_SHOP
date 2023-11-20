package site.devdaramg.shop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ToString
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;
    private String name; //상품이름
    private int price; //상품가격
    private int stockQuantity; //재고수량

    public Product() {
    }

    @Builder
    public Product(String name, int price, int stockQuantity){
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) throw new IllegalArgumentException("재고가 없습니다");
        this.stockQuantity = restStock; //주문 시 1개 재고 감소
    }
}
