package com.elkabani.mydb;

import com.elkabani.mydb.entities.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.math.BigDecimal;

@SpringBootApplication
public class MyDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyDbApplication.class, args);
    }

    @Bean
    public CommandLineRunner databaseSeeder(ProductRepository productRepo, CategoryRepository categoryRepo) {
        return args -> {
            productRepo.deleteAll();
            categoryRepo.deleteAll();

            Category electronics = new Category();
            electronics.setName("Electronics");
            categoryRepo.save(electronics);

            Product camera = new Product();
            camera.setName("Camera");
            camera.setDescription("High-definition DSLR camera package");
            camera.setPrice(new BigDecimal("499.99"));
            camera.setCategory(electronics);
            productRepo.save(camera);

            Product headphones = new Product();
            headphones.setName("Headphones");
            headphones.setDescription("Noise-canceling studio over-ear headphones");
            headphones.setPrice(new BigDecimal("149.99"));
            headphones.setCategory(electronics);
            productRepo.save(headphones);

            Category homeGoods = new Category();
            homeGoods.setName("Home Goods");
            categoryRepo.save(homeGoods);

            Product lamp = new Product();
            lamp.setName("Desk Lamp");
            lamp.setDescription("LED adjustable desk lamp");
            lamp.setPrice(new BigDecimal("29.99"));
            lamp.setCategory(homeGoods);
            productRepo.save(lamp);
        };
    }
}
