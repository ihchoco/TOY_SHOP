package site.devdaramg.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.devdaramg.shop.entity.Member;
import site.devdaramg.shop.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderer(Member orderder);

}
