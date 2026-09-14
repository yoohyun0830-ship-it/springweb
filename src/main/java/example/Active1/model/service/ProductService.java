package example.Active1.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.Active1.model.dto.ProductDto;
import example.Active1.model.dto.ReviewDto;
import example.Active1.model.entity.ProductEntity;
import example.Active1.model.repository.ProductRepository;

@Service 
public class ProductService {
    public boolean 제품등록(ProductDto productDto){
        @Autowired 
        private ProductRepository productRepository;

        ProductEntity productEntity = productDto.toEntity();
        ProductEntity savedEntity = productRepository.save(productEntity);
        if (savedEntity.getBno()>=1) {
            return true;
        }
        return false;
    }

    public List<ProductDto> 제품전체조회(){
        List<ProductEntity> productEntities =productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.forEach((productEntity)->{
            ProductDto productDto = ProductDto.from(productEntity);
            productEntity.getReviewEntities().forEach((ReviewEntity)->{
                ReviewDto reviewDto = ReviewDto.from(reviewEntity);
                ReviewDto.getReviews().add(reviewDto);
            });
            productDtos.add(productDto);
        });
        return productDtos;
    }
}
