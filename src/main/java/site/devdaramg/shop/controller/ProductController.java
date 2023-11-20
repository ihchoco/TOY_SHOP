package site.devdaramg.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.devdaramg.shop.dto.ProductRequestDto;
import site.devdaramg.shop.entity.Product;
import site.devdaramg.shop.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public Long register(@RequestBody ProductRequestDto productRequestDto){
        return productService.register(productRequestDto);
    }

    @GetMapping("/products/{id}")
    public Product findProduct(@PathVariable Long id){
        return productService.findProductById(id);
    }

    @GetMapping("/products")
    public List<Product> findAllProducts(){
        return productService.findProductList();
    }
}
