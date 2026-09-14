package example.Active1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.Active1.model.dto.ProductDto;
import example.Active1.model.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    // 1. 제품 등록
    @PostMapping("")
    public boolean 제품등록(@RequestBody ProductDto productDto) {
        return productService.제품등록(productDto);
    }


    // 2. 제품 전체조회
    @GetMapping("")
    public List<ProductDto> 제품전체조회() {
        return productService.제품전체조회();
    }
}