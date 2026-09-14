package example.Active1.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController 

public class ProductController {
    @PostMapping ("")
    public boolean 제품등록(@RequestBody ProductDto productDto){
        return productService.제품등록(productDto);
    }

    @GetMapping ("")
    public List<productDto> 제품전체조회(){
        return productService.제품전체조회();
    }
}
