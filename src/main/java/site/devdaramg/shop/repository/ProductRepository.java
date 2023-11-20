package site.devdaramg.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.devdaramg.shop.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
