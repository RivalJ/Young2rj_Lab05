package com.elkabani.mydb;

import com.elkabani.mydb.entities.*;
import com.elkabani.mydb.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;

@SpringBootApplication
public class MyDbApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(MyDbApplication.class, args);
        CategoryRepository categoryRepo = context.getBean(CategoryRepository.class);
        ProductRepository productRepo = context.getBean(ProductRepository.class);

        // 5A
        Category electronics = new Category();
        electronics.setName("Electronics");
        categoryRepo.save(electronics);

        Product camera = new Product();
        camera.setName("Camera");
        camera.setPrice(new BigDecimal("499.99"));
        camera.setCategory(electronics);
        productRepo.save(camera);

        // 5B
        Category existingCategory = categoryRepo.findById(electronics.getId()).orElseThrow();
        Product headphones = new Product();
        headphones.setName("Headphones");
        headphones.setPrice(new BigDecimal("149.99"));
        headphones.setCategory(existingCategory);
        Product savedHeadphones = productRepo.save(headphones);

        // 5C
        productRepo.deleteById(savedHeadphones.getId());
    }

}
