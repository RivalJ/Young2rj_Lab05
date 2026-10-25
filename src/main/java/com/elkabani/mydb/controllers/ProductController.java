package com.elkabani.mydb.controllers;

import com.elkabani.mydb.dtos.ProductDto;
import com.elkabani.mydb.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll(@RequestParam(name = "categoryId", required = false)Long categoryId){
        if(categoryId == null){
            return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productService.findByCategory(categoryId), HttpStatus.OK);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id){
        return productService.findById(id)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }//sometimes, I look at stuff like this, and it just looks like pixels on a screen.
    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDto){
        return productService.create(productDto)
                .map(response -> new ResponseEntity<>(response, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto productDto){
        try{
            return productService.update(productDto.getId(), productDto)
                    .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean result = productService.delete(id);
        if (!result){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
