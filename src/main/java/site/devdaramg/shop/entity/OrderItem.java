package site.devdaramg.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
    private int orderPrice;
    private int count;

    public Map<String, String> getProductInfo(){
        Map<String, String> map = new HashMap<>();
        map.put("ID", product.getId().toString());
        map.put("Name", product.getName());
        return map;
    }

    public static OrderItem createOrderItem(Product product, int count, int price){
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setPrice(price);
        orderItem.setCount(count);

        product.removeStock(count); //수량 감소

        return orderItem;
    }

    private void setProduct(Product product){
        this.product = product;
    }

    private void setPrice(int price){
        this.orderPrice= price;
    }

    private void setCount(int count){
        this.count = count;
    }

    public int getTotalPrice(){
        return this.orderPrice * count;
    }

    public void cancel(){
        product.addStock(count);
    }

    public Product getProduct() {
        return product;
    }



}
