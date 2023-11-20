package site.devdaramg.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.devdaramg.shop.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
