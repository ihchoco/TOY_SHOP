package site.devdaramg.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.devdaramg.shop.dto.MemberRequestDto;
import site.devdaramg.shop.dto.ProductRequestDto;
import site.devdaramg.shop.entity.Member;
import site.devdaramg.shop.entity.Product;
import site.devdaramg.shop.repository.MemberRepository;
import site.devdaramg.shop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {
    private final ProductRepository productRepository;

    //상품 등록
    public Long register(ProductRequestDto productRequestDto){
        Product product = Product.builder()
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .stockQuantity(productRequestDto.getStockQuantity())
                .build();
        return productRepository.save(product).getId();
    }

    //상품 목록 리스트 조회
    public List<Product> findProductList(){
        return productRepository.findAll();
    }

    //상품 상세 조회 By Id
    public Product findProductById(Long id){
        Product findProduct = productRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("찾는 회원이 없습니다");
        });
        return findProduct;
    }



}
