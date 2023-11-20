package site.devdaramg.shop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.devdaramg.shop.dto.ProductRequestDto;
import site.devdaramg.shop.entity.Product;
import site.devdaramg.shop.repository.MemberRepository;
import site.devdaramg.shop.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void clean() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 등록 테스트")
    void ProductServiceTest(){
        //given
        ProductRequestDto request = ProductRequestDto.builder()
                .name("개발다람쥐 책")
                .price(10000)
                .stockQuantity(100)
                .build();

        //when
        productService.register(request);

        //then
        Assertions.assertThat(productRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("상품 조회 테스트")
    void ProductServiceTest2(){
        //given
        Product request = Product.builder()
                .name("개발다람쥐 책")
                .price(10000)
                .stockQuantity(100)
                .build();

        //when
        productRepository.save(request);
        Product findProduct = productService.findProductById(request.getId());

        //then
        Assertions.assertThat(findProduct.getId()).isEqualTo(request.getId());
        Assertions.assertThat(findProduct.getName()).isEqualTo(request.getName());

    }

}