package site.devdaramg.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.devdaramg.shop.entity.Cart;
import site.devdaramg.shop.entity.Member;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByOrderer(Member orderer);
}
