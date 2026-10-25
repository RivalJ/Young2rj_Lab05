package com.elkabani.mydb.services;

import com.elkabani.mydb.dtos.ProductDto;
import com.elkabani.mydb.entities.Category;
import com.elkabani.mydb.entities.Product;
import com.elkabani.mydb.entities.CategoryRepository;
import com.elkabani.mydb.entities.ProductRepository;
import com.elkabani.mydb.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductMapper productMapper;

    public List<ProductDto> findAll(){
        return productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
    }
    public Optional<ProductDto> findById(Long id){
        return productRepository.findById(id).map(productMapper::toDto);
    }
    public List<ProductDto> findByCategory(Long categoryId){
        return productRepository.findByCategoryId(categoryId).stream().map(productMapper::toDto).collect(Collectors.toList());
    }
    public Optional<ProductDto> create(ProductDto productDto){
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(optionalCategory.isEmpty()){
            return Optional.empty();
        }
        Product product = productMapper.toEntity(productDto);
        product.setCategory(optionalCategory.get());
        return Optional.of(productMapper.toDto(productRepository.save(product)));
    }
    public boolean delete(Long id){
        if(!productRepository.existsById(id)){
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }

    public Optional<ProductDto> update(Long id, ProductDto productDto){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            return Optional.empty();
        }
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(optionalCategory.isEmpty()){
            throw new IllegalArgumentException("Category not found");
        }

        Product existingProduct = optionalProduct.get();
        productMapper.update(productDto, existingProduct);
        existingProduct.setCategory(optionalCategory.get());

        return Optional.of(productMapper.toDto(productRepository.save(existingProduct)));
    }
}
