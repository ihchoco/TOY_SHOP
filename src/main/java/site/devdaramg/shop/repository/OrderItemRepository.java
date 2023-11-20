package site.devdaramg.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.devdaramg.shop.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
